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

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gf.service.download.hooks.FileDownloadHandlerHook;
import net.datenwerke.rs.base.client.reportengines.jasper.JasperUiModule;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReportJRXMLFile;
import net.datenwerke.rs.base.service.reportengines.jasper.util.JasperUtilsService;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.utils.misc.HttpUtils;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.treedb.actions.ReadAction;

public class JasperReportJRXMLDownloadHooker implements FileDownloadHandlerHook {

	private final Provider<SecurityService> securityServiceProvider;
	private final Provider<JasperUtilsService> jasperUtilsProvider;
	private final Provider<ReportService> reportServiceProvider;
	private final Provider<HttpUtils> httpUtilsProvider;
	
	@Inject
	public JasperReportJRXMLDownloadHooker(
			Provider<JasperUtilsService> jasperUtilsProvider,
			Provider<ReportService> reportServiceProvider,
			Provider<SecurityService> securityServiceProvider, 
			Provider<HttpUtils> httpUtilsProvider
			) {
		this.jasperUtilsProvider = jasperUtilsProvider;
		this.reportServiceProvider = reportServiceProvider;
		this.securityServiceProvider = securityServiceProvider;
		this.httpUtilsProvider = httpUtilsProvider;
	}

	@Override
	public boolean consumes(String handler) {
		return JasperUiModule.JRXML_DOWNLOAD_HANDLER.equals(handler);
	}

	@Override
	public void processDownload(String id, String handler,
			Map<String, String> metadata, HttpServletResponse response) throws IOException {
		Long lid = Long.valueOf(id);
		
		JasperUtilsService jasperUtilsService = jasperUtilsProvider.get();
		JasperReportJRXMLFile file = jasperUtilsService.getJRXMLFileById(lid);
		JasperReport report = jasperUtilsService.getReportWithJRXMLFile(file);
		
		SecurityService securityService = securityServiceProvider.get();

		if(! securityService.checkActions(report, ReadAction.class))
			throw new ViolatedSecurityException();
		
		response.setHeader(HttpUtils.CONTENT_DISPOSITION, httpUtilsProvider.get().makeContentDispositionHeader(true, file.getName()));
		response.setCharacterEncoding("UTF-8"); 
		response.setContentType("application/xml");
		response.getOutputStream().write(file.getContent().getBytes());
	}

}
