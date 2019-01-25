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
 
 
package net.datenwerke.rs.birt.service.reportengine.hookers;

import java.util.Map;

import net.datenwerke.gf.service.upload.UploadedFile;
import net.datenwerke.gf.service.upload.hooks.FileUploadHandlerHook;
import net.datenwerke.rs.birt.client.reportengines.BirtUiModule;
import net.datenwerke.rs.birt.service.reportengine.BirtReportService;
import net.datenwerke.rs.birt.service.reportengine.entities.BirtReport;
import net.datenwerke.rs.birt.service.reportengine.entities.BirtReportFile;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.treedb.actions.UpdateAction;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BirtReportUploadHooker implements FileUploadHandlerHook {
	
	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final Provider<SecurityService> securityServiceProvider;
	private final Provider<ReportService> reportServiceProvider;
	private final Provider<BirtReportService> birtReportServiceProvider;

	@Inject
	public BirtReportUploadHooker(
			Provider<AuthenticatorService> authenticatorServiceProvider, 
			Provider<SecurityService> securityServiceProvider, 
			Provider<ReportService> reportServiceProvider,
			Provider<BirtReportService> birtReportServiceProvider
	) {
		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.securityServiceProvider = securityServiceProvider;
		this.reportServiceProvider = reportServiceProvider;
		this.birtReportServiceProvider = birtReportServiceProvider;
	}
	
	@Override
	public boolean consumes(String handler) {
		return BirtUiModule.UPLOAD_HANDLER_ID.equals(handler);
	}

	@Override
	public String uploadOccured(UploadedFile uploadedFile) {
		Map<String, String> metadataMap = uploadedFile.getMetadata();
		
		long reportId = Long.valueOf(metadataMap.get(BirtUiModule.UPLOAD_REPORT_ID_FIELD));
		String reportName = uploadedFile.getFileName();
		String reportContents = new String(uploadedFile.getFileBytes());


		if(null == reportName || null == reportContents || reportContents.isEmpty() || reportName.isEmpty())
			return null;

		if(!reportName.endsWith(".rptdesign"))
			throw new RuntimeException("Expectet .rptdesign file");

		if(null == authenticatorServiceProvider.get().getCurrentUser())
			throw new ViolatedSecurityException();

		SecurityService securityService = securityServiceProvider.get();

		ReportService reportService = reportServiceProvider.get();
		AbstractReportManagerNode rmn = reportService.getNodeById(reportId);

		if(! securityService.checkActions(rmn, UpdateAction.class))
			throw new ViolatedSecurityException();

		if(rmn instanceof BirtReport){
			BirtReport birtReport = (BirtReport) rmn;

			BirtReportFile oldFile = birtReport.getReportFile();

			BirtReportFile reportFile = new BirtReportFile();
			reportFile.setName(reportName);
			reportFile.setContent(reportContents);

			birtReport.setReportFile(reportFile);

			/* delete old file */
			if(null != oldFile){
				BirtReportService service = birtReportServiceProvider.get();
				service.remove(oldFile);
			}

			/* merge new file */
			reportService.merge(birtReport);
		}
		
		return null;
	}


}
