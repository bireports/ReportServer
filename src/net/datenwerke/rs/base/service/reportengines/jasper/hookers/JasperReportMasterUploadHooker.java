/*
 *  ReportServer
 *  Copyright (c) 2016 datenwerke Jan Albrecht
 *  http://reportserver.datenwerke.net
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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.hookers;

import java.util.Map;

import net.datenwerke.gf.service.upload.UploadedFile;
import net.datenwerke.gf.service.upload.hooks.FileUploadHandlerHook;
import net.datenwerke.rs.base.client.reportengines.jasper.JasperUiModule;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReportJRXMLFile;
import net.datenwerke.rs.base.service.reportengines.jasper.util.JasperUtilsService;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.treedb.actions.UpdateAction;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class JasperReportMasterUploadHooker implements FileUploadHandlerHook {
	
	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final Provider<SecurityService> securityServiceProvider;
	private final Provider<ReportService> reportServiceProvider;
	private final Provider<JasperUtilsService> jasperReportManagerProvider;

	@Inject
	public JasperReportMasterUploadHooker(
			Provider<AuthenticatorService> authenticatorServiceProvider, 
			Provider<SecurityService> securityServiceProvider, 
			Provider<ReportService> reportServiceProvider,
			Provider<JasperUtilsService> jasperReportManagerProvider
	) {
		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.securityServiceProvider = securityServiceProvider;
		this.reportServiceProvider = reportServiceProvider;
		this.jasperReportManagerProvider  =jasperReportManagerProvider;
	}
	
	@Override
	public boolean consumes(String handler) {
		return JasperUiModule.MASTER_UPLOAD_HANDLER_ID.equals(handler);
	}

	@Override
	public String uploadOccured(UploadedFile uploadedFile) {
		Map<String, String> metadataMap = uploadedFile.getMetadata();
		
		long reportId = Long.valueOf(metadataMap.get(JasperUiModule.META_REPORT_ID));
		String reportName = uploadedFile.getFileName();
		String reportContents = new String(uploadedFile.getFileBytes());


		if(null == reportName || null == reportContents || reportContents.isEmpty() || reportName.isEmpty())
			return null;

		if(!reportName.endsWith(".jrxml"))
			throw new RuntimeException("Expectet .jrxml file");

		if(null == authenticatorServiceProvider.get().getCurrentUser())
			throw new ViolatedSecurityException();

		SecurityService securityService = securityServiceProvider.get();

		ReportService reportService = reportServiceProvider.get();
		AbstractReportManagerNode rmn = reportService.getNodeById(reportId);

		if(! securityService.checkActions(rmn, UpdateAction.class))
			throw new ViolatedSecurityException();

		if(rmn instanceof JasperReport){
			JasperReport jasperReport = (JasperReport) rmn;

			JasperReportJRXMLFile oldFile = jasperReport.getMasterFile();

			JasperReportJRXMLFile reportFile = new JasperReportJRXMLFile();
			reportFile.setName(reportName);
			reportFile.setContent(reportContents);

			jasperReport.setMasterFile(reportFile);

			/* delete old file */
			if(null != oldFile){
				JasperUtilsService service = jasperReportManagerProvider.get();
				service.removeJRXMLFile(oldFile);
			}

			/* merge new file */
			reportService.merge(jasperReport);
		}
		
		return null;
	}


}
