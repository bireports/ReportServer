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
 
 
package net.datenwerke.rs.jxlsreport.service.jxlsreport.hookers;

import java.io.ByteArrayInputStream;
import java.util.Map;

import net.datenwerke.gf.service.upload.UploadedFile;
import net.datenwerke.gf.service.upload.hooks.FileUploadHandlerHook;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.JxlsReportUiModule;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.JxlsReportService;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.entities.JxlsReport;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.entities.JxlsReportFile;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.treedb.actions.UpdateAction;

import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class JxlsReportUploadHooker implements FileUploadHandlerHook {

	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final Provider<SecurityService> securityServiceProvider;
	private final Provider<ReportService> reportServiceProvider;
	private final Provider<JxlsReportService> jxlsReportServiceProvider;

	@Inject
	public JxlsReportUploadHooker(
			Provider<AuthenticatorService> authenticatorServiceProvider, 
			Provider<SecurityService> securityServiceProvider, 
			Provider<ReportService> reportServiceProvider,
			Provider<JxlsReportService> jxlsReportServiceProvider
			) {
		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.securityServiceProvider = securityServiceProvider;
		this.reportServiceProvider = reportServiceProvider;
		this.jxlsReportServiceProvider = jxlsReportServiceProvider;
	}

	@Override
	public boolean consumes(String handler) {
		return JxlsReportUiModule.UPLOAD_HANDLER_ID.equals(handler);
	}


	@Override
	public String uploadOccured(UploadedFile uploadedFile) {
		Map<String, String> metadataMap = uploadedFile.getMetadata();

		long reportId = Long.valueOf(metadataMap.get(JxlsReportUiModule.UPLOAD_REPORT_ID_FIELD));
		String reportName = uploadedFile.getFileName();
		byte[] content = uploadedFile.getFileBytes();


		if(null == reportName || null == content || "".equals(reportName.trim()) || content.length == 0)
			return null;

		if(! (reportName.endsWith(".xls") || reportName.endsWith(".xlsx") || reportName.endsWith(".xlsm")))
			throw new RuntimeException("Could not create template. No xls file specified");

		try {
			/* make sure template is indeed xls */
			WorkbookFactory.create(new ByteArrayInputStream(content));
		} catch (Exception e) {
			throw new RuntimeException("Could not load excel file", e);	
		}

		if(null == authenticatorServiceProvider.get().getCurrentUser())
			throw new ViolatedSecurityException();

		SecurityService securityService = securityServiceProvider.get();

		ReportService reportService = reportServiceProvider.get();
		AbstractReportManagerNode rmn = reportService.getNodeById(reportId);

		if(! securityService.checkActions(rmn, UpdateAction.class))
			throw new ViolatedSecurityException();

		if(rmn instanceof JxlsReport){
			JxlsReport jxlsReport = (JxlsReport) rmn;

			JxlsReportFile oldFile = jxlsReport.getReportFile();

			JxlsReportFile reportFile = new JxlsReportFile();
			reportFile.setName(reportName);
			reportFile.setContent(content);

			jxlsReport.setReportFile(reportFile);

			if(null != oldFile){
				JxlsReportService jxlsReportService = jxlsReportServiceProvider.get();
				jxlsReportService.remove(oldFile);
			}

			reportService.merge(jxlsReport);
		}
		
		return null;
	}


}
