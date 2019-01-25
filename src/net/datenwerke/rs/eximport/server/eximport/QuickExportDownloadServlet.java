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
 
 
package net.datenwerke.rs.eximport.server.eximport;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.datenwerke.rs.eximport.service.eximport.ex.http.HttpExportService;
import net.datenwerke.rs.eximport.service.genrights.ExportSecurityTarget;
import net.datenwerke.rs.utils.filename.FileNameService;
import net.datenwerke.rs.utils.zip.ZipUtilsService;
import net.datenwerke.security.server.SecuredHttpServlet;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Execute;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

/**
 * 
 *
 */
@Singleton
public class QuickExportDownloadServlet extends SecuredHttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2531374971329277458L;
	
	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final Provider<SecurityService> securityServiceProvider;
	private final Provider<HttpExportService> httpExportServiceProvider;
	private final Provider<ZipUtilsService> zipServiceProvider;
	private final Provider<FileNameService> fileNameServiceProvider;
	
	@Inject
	public QuickExportDownloadServlet(
		Provider<AuthenticatorService> authenticatorServiceProvider,
		Provider<SecurityService> securityServiceProvider,
		Provider<HttpExportService> httpExportServiceProvider,
		Provider<ZipUtilsService> zipServiceProvider,
		Provider<FileNameService> fileNameServiceProvider
		){
	
		/* store objects */
		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.securityServiceProvider = securityServiceProvider;
		this.httpExportServiceProvider = httpExportServiceProvider;
		this.zipServiceProvider = zipServiceProvider;
		this.fileNameServiceProvider = fileNameServiceProvider;
	}
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		validateRequest();
		
		String export = httpExportServiceProvider.get().getAndRemoveStoredExport();
		String name = httpExportServiceProvider.get().getExportName();
		name = null == name ? "" : name;
		if(null == export)
			throw new IllegalStateException("Could not find exported data.");

		response.setContentType("application/octet-stream"); //$NON-NLS-1$
		response.setHeader("Cache-Control", "no-cache"); //$NON-NLS-1$ //$NON-NLS-2$
		String exName = new SimpleDateFormat("yyyyMMdd-hhmm").format(Calendar.getInstance().getTime()) + "-export-" + name + ".zip";
		exName = fileNameServiceProvider.get().sanitizeFileName(exName);
		response.setHeader("Content-Disposition", "attachment; filename=\"" + exName + "\""); //$NON-NLS-1$ 
		
		/* zip */
		ZipUtilsService zipUtils = zipServiceProvider.get();
		zipUtils.createZip(Collections.singletonMap("export.xml", export), response.getOutputStream());
	}
	
	private void validateRequest() {
		if(null == authenticatorServiceProvider.get().getCurrentUser())
			throw new ViolatedSecurityException();

		SecurityService securityService = securityServiceProvider.get();
		
		if(! securityService.checkRights(ExportSecurityTarget.class, Execute.class))
			throw new ViolatedSecurityException();
	}
}
