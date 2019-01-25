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
 
 
package net.datenwerke.rs.crystal.client.crystal.hookers;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import net.datenwerke.gf.service.upload.UploadedFile;
import net.datenwerke.gf.service.upload.hooks.FileUploadHandlerHook;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.rs.crystal.client.crystal.CrystalUiModule;
import net.datenwerke.rs.crystal.service.crystal.entities.CrystalReport;
import net.datenwerke.rs.crystal.service.crystal.entities.CrystalReportFile;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.treedb.actions.UpdateAction;

public class CrystalReportUploadHooker implements FileUploadHandlerHook {

	private Provider<AuthenticatorService> authenticatorServiceProvider;
	private Provider<SecurityService> securityServiceProvider;
	private Provider<ReportService> reportServiceProvider;

	@Inject
	public CrystalReportUploadHooker(
			Provider<AuthenticatorService> authenticatorServiceProvider, 
			Provider<SecurityService> securityServiceProvider, 
			Provider<ReportService> reportServiceProvider) {

		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.securityServiceProvider = securityServiceProvider;
		this.reportServiceProvider = reportServiceProvider;
	}

	@Override
	public boolean consumes(String handler) {
		return CrystalUiModule.UPLOAD_HANDLER_ID.equals(handler);
	}


	@Override
	public String uploadOccured(UploadedFile uploadedFile) {
		Map<String, String> metadataMap = uploadedFile.getMetadata();

		long reportId = Long.valueOf(metadataMap.get(CrystalUiModule.UPLOAD_REPORT_ID_FIELD));
		String reportName = uploadedFile.getFileName();
		byte[] reportContents = uploadedFile.getFileBytes();


		if(null == reportName || null == reportContents || reportContents.length == 0 || reportName.isEmpty())
			return null;

		if(!reportName.endsWith(".rpt"))
			throw new RuntimeException("Expected .rpt file");

		if(null == authenticatorServiceProvider.get().getCurrentUser())
			throw new ViolatedSecurityException();

		SecurityService securityService = securityServiceProvider.get();

		ReportService reportService = reportServiceProvider.get();
		AbstractReportManagerNode rmn = reportService.getNodeById(reportId);

		if(! securityService.checkActions(rmn, UpdateAction.class))
			throw new ViolatedSecurityException();

		if(rmn instanceof CrystalReport){
			CrystalReport crystalReport = (CrystalReport) rmn;

			CrystalReportFile reportFile = new CrystalReportFile();
			reportFile.setName(reportName);
			reportFile.setContent(reportContents);

			crystalReport.setReportFile(reportFile);

			reportService.merge(crystalReport);
		}
		
		return null;
	}


}
