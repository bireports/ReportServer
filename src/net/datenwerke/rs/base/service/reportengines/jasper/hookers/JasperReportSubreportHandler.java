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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.hookers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.datenwerke.gf.client.fileselection.dto.SelectedFileWrapper;
import net.datenwerke.gf.service.fileselection.hooks.FileSelectionHandlerHook;
import net.datenwerke.gf.service.upload.FileUploadService;
import net.datenwerke.gf.service.upload.UploadedFile;
import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.client.reportengines.jasper.JasperUiModule;
import net.datenwerke.rs.base.client.reportengines.jasper.dto.JasperReportJRXMLFileDto;
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

public class JasperReportSubreportHandler implements FileSelectionHandlerHook {

	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final Provider<SecurityService> securityServiceProvider;
	private final Provider<ReportService> reportServiceProvider;
	private final Provider<DtoService> dtoServiceProvider;
	private final Provider<JasperUtilsService> jasperUtils;
	private final Provider<FileUploadService> fileUploadServiceProvider;
	
	@Inject
	public JasperReportSubreportHandler(
			Provider<AuthenticatorService> authenticatorServiceProvider,
			Provider<SecurityService> securityServiceProvider,
			Provider<ReportService> reportServiceProvider,
			Provider<DtoService> dtoServiceProvider,
			Provider<JasperUtilsService> jasperUtils,
			Provider<FileUploadService> fileUploadServiceProvider) {
		super();
		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.securityServiceProvider = securityServiceProvider;
		this.reportServiceProvider = reportServiceProvider;
		this.dtoServiceProvider = dtoServiceProvider;
		this.jasperUtils = jasperUtils;
		this.fileUploadServiceProvider = fileUploadServiceProvider;
	}

	@Override
	public boolean consumes(String handler) {
		return JasperUiModule.SUB_REPORT_HANDLER_ID.equals(handler);
	}

	@Override
	public void processData(ArrayList<SelectedFileWrapper> data, String handler, Map<String,String> metadata) {
		/* get report and check security */
		long reportId = Long.valueOf(metadata.get(JasperUiModule.META_REPORT_ID));
		JasperReport report = getReportAndCheckSecurty(reportId);
		
		/* get services */
		ReportService reportService = reportServiceProvider.get();
		JasperUtilsService jasperService = jasperUtils.get();
		
		/* get remaining JRXML files and potentially update */
		List<JasperReportJRXMLFile> jrxmlFiles = new ArrayList<JasperReportJRXMLFile>();
		for(SelectedFileWrapper file : data){
			if(null != file.getOriginalDto()){
				Dto dto = file.getOriginalDto();
				if(! (dto instanceof JasperReportJRXMLFileDto))
					throw new IllegalArgumentException("Only JRXML files allowed");
				
				JasperReportJRXMLFile jrxml = (JasperReportJRXMLFile) dtoServiceProvider.get().loadPoso(dto);
				
				/* check that file belongs to report */
				JasperReport correspondingReport = jasperService.getReportWithJRXMLFile(jrxml);
				if(null == correspondingReport || ! correspondingReport.equals(report))
					throw new IllegalArgumentException("JRXML of wrong report supplied");
				
				/* add to list */
				jrxmlFiles.add(jrxml);
				
				/* update name */
				jrxml.setName(file.getName());
				
				/* merge jrxml file */
				jasperService.merge(jrxml);
			}
		}
		
		/* remove superfluous files */
		List<JasperReportJRXMLFile> toDeleteList = new ArrayList<JasperReportJRXMLFile>(report.getSubFiles());
		toDeleteList.removeAll(jrxmlFiles);
		for (Iterator<JasperReportJRXMLFile> iterator = toDeleteList.iterator(); iterator.hasNext();) {
			JasperReportJRXMLFile toDelete = iterator.next();
			jasperService.removeJRXMLFile(toDelete);
		}
		
		/* get new files */
		FileUploadService fileUploadService = fileUploadServiceProvider.get();
		List<UploadedFile> uploadedFiles = fileUploadService.extractUploadedFilesFrom(data);
		for(UploadedFile file : uploadedFiles){
			JasperReportJRXMLFile subFile = new JasperReportJRXMLFile();
			subFile.setName(file.getFileName());
			subFile.setContent(new String(file.getFileBytes()));
			
			report.addSubfile(subFile);
			
			jasperService.persist(subFile);
		}
		
		/* merge */
		reportService.merge(report);
	}

	protected JasperReport getReportAndCheckSecurty(long reportId) {
		if(null == authenticatorServiceProvider.get().getCurrentUser())
			throw new ViolatedSecurityException();

		SecurityService securityService = securityServiceProvider.get();

		ReportService reportService = reportServiceProvider.get();
		AbstractReportManagerNode rmn = reportService.getNodeById(reportId);
		if(! (rmn instanceof JasperReport))
			throw new IllegalArgumentException("expected jasper report");
		
		if(! securityService.checkActions(rmn, UpdateAction.class))
			throw new ViolatedSecurityException();
		
		return (JasperReport) rmn;
	}

}
