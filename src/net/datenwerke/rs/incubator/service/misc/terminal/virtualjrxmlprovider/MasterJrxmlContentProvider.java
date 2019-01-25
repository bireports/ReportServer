/*
 *  ReportServer
 *  Copyright (c) 2018 InfoFabrik GmbH
 *  http://reportserver.net/
 *
 *
 * This file is part of ReportServer.
 *
 * ReportServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package net.datenwerke.rs.incubator.service.misc.terminal.virtualjrxmlprovider;

import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReportJRXMLFile;
import net.datenwerke.rs.base.service.reportengines.jasper.util.JasperUtilsService;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.interfaces.ReportVariant;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocationInfo;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSObjectInfo;
import net.datenwerke.rs.terminal.service.terminal.vfs.exceptions.LocationDoesNotExistException;
import net.datenwerke.rs.terminal.service.terminal.vfs.exceptions.VFSException;
import net.datenwerke.rs.terminal.service.terminal.vfs.hooks.VirtualContentProviderImpl;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.rights.Write;

import com.google.inject.Inject;

public class MasterJrxmlContentProvider extends
		VirtualContentProviderImpl {

	public static final String VIRTUAL_NAME = "master";
	private static final String VIRTUAL_FILE_NAME = "master.jrxml";
	
	private final JasperUtilsService jasperReportsService;
	private final ReportService reportService;

	
	@Inject
	public MasterJrxmlContentProvider(
		SecurityService securityService,
		JasperUtilsService jasperReportsService,
		ReportService reportService
		) {
		super(securityService);
		this.jasperReportsService = jasperReportsService;
		this.reportService = reportService;
	}

	@Override
	public String getName() {
		return VIRTUAL_NAME;
	}

	@Override
	protected void addVirtualChildInfos(VFSLocationInfo info) {
		info.addChildInfo(new VFSObjectInfo(getClass(), VIRTUAL_FILE_NAME, VIRTUAL_FILE_NAME, false));
	}

	@Override
	protected boolean doHasContent(VFSLocation vfsLocation) {
		return true;
	}

	@Override
	protected byte[] doGetContent(VFSLocation vfsLocation) throws VFSException {
		VFSLocation parent = vfsLocation.getVirtualParentLocation();
		
		JasperReport node = (JasperReport) parent.getObject();

		return null != node.getMasterFile() ? node.getMasterFile().getContent().getBytes() : null;
	}

	@Override
	protected void doSetContent(VFSLocation vfsLocation, byte[] content) {
	}

	@Override
	protected String doGetContentType(VFSLocation vfsLocation) {
		return "application/xml";
	}

	@Override
	public boolean enhanceNonVirtual(VFSLocation location) throws VFSException {
		Object obj = location.getObject();
		return null != obj && obj instanceof JasperReport && ! (obj instanceof ReportVariant);
	}

	@Override
	protected boolean doesExist(VFSLocation vfsLocation) {
		return VIRTUAL_FILE_NAME.equals(vfsLocation.getPathHelper().getLastPathway());
	}
	
	@Override
	protected boolean doCanWrite(VFSLocation location) {
		return VIRTUAL_FILE_NAME.equals(location.getPathHelper().getLastPathway());
	}
	
	@Override
	protected boolean doIsLocationDeletable(VFSLocation location) {
		return VIRTUAL_FILE_NAME.equals(location.getPathHelper().getLastPathway());
	}
	
	@Override
	public void delete(VFSLocation location) throws VFSException {
		JasperReport report = (JasperReport) location.getVirtualBaseLocation().getObject();
		
		JasperReportJRXMLFile master = report.getMasterFile();
		if(null != master)
			jasperReportsService.removeJRXMLFile(report.getMasterFile());
	}
	
	@Override
	public void writeIntoLocation(VFSLocation vfsLocation, byte[] uploadData)
			throws VFSException {
		if(! doesExist(vfsLocation))
			throw new LocationDoesNotExistException(vfsLocation.getAbsolutePath());
		
		final JasperReport report = (JasperReport) vfsLocation.getVirtualParentLocation().getObject();

		if(! securityService.checkRights(report, Write.class))
			throw new VFSException("Insufficient rights");
		
		if(null == report.getMasterFile()){
			JasperReportJRXMLFile master = new JasperReportJRXMLFile();
			report.setMasterFile(master);
			jasperReportsService.persist(master);
		}
		
		String jrxml = new String(uploadData);
		
		report.getMasterFile().setContent(jrxml);

		reportService.merge(report);
	}
	
	@Override
	public Object getObjectFor(VFSLocation vfsLocation) throws VFSException {
		if(! doesExist(vfsLocation))
			throw new LocationDoesNotExistException(vfsLocation.getAbsolutePath());
		
		JasperReport report = (JasperReport) vfsLocation.getVirtualBaseLocation().getObject();
		
		return report.getMasterFile();
	}

	
	
}
