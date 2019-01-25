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
 
 
package net.datenwerke.rs.base.server.jasper;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReportJRXMLFile;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.rs.utils.misc.HttpUtils;
import net.datenwerke.rs.utils.zip.ZipUtilsService;
import net.datenwerke.security.server.SecuredHttpServlet;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.treedb.actions.ReadAction;

/**
 * 
 *
 */
@Singleton
public class JRXMLDownloadServlet extends SecuredHttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5706090059568108211L;
	
	private final Provider<ReportService> reportManagerProvider;
	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final Provider<SecurityService> securityServiceProvider;
	private final Provider<ZipUtilsService> zipUtilsProvider;
	private final Provider<HttpUtils> httpUtilsProvider;
	
	@Inject
	public JRXMLDownloadServlet(
		Provider<ReportService> reportManagerProvider,
		Provider<AuthenticatorService> authenticatorServiceProvider,
		Provider<SecurityService> securityServiceProvider,
		Provider<ZipUtilsService> zipUtilsProvider, 
		Provider<HttpUtils> httpUtilsProvider
		){
		
		/* store objects */
		this.reportManagerProvider = reportManagerProvider;
		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.securityServiceProvider = securityServiceProvider;
		this.zipUtilsProvider = zipUtilsProvider;
		this.httpUtilsProvider = httpUtilsProvider;
	}
	
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		/* set utf-8 character encoding -> Ticket #691*/
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		Long reportId = Long.valueOf(request.getParameter("id")); //$NON-NLS-1$
		
		ReportService reportService = reportManagerProvider.get();
		AbstractReportManagerNode rmn = reportService.getNodeById(reportId);
		
		/* check rights */
		validateRequest(rmn);
		
		if(rmn instanceof JasperReport){
			JasperReport report = (JasperReport)rmn;
			
			Map<String, Object> contentMap = new LinkedHashMap<String, Object>(); 
			
			JasperReportJRXMLFile master = report.getMasterFile();
			addFileToContent(contentMap, master);
			
			for(JasperReportJRXMLFile file : report.getSubFiles())
				addFileToContent(contentMap, file);
			
			/* set mime type */
			response.setContentType("application/zip"); //$NON-NLS-1$
			
			/* set header and encoding */
			response.setHeader(HttpUtils.CONTENT_DISPOSITION, httpUtilsProvider.get().makeContentDispositionHeader(true, report.getName() + "-jrxmlfiles.zip"));
			response.setCharacterEncoding("UTF-8"); //$NON-NLS-1$
			
			zipUtilsProvider.get().createZip(contentMap, response.getOutputStream());
		}
	}
	

	private void addFileToContent(Map<String, Object> contentMap,
			JasperReportJRXMLFile file) throws UnsupportedEncodingException {
		if(null != file){
			byte[] content = file.getContent().getBytes("UTF-8");
			contentMap.put(file.getName() + (null != file.getName() && file.getName().endsWith(".jrxml") ? "" : ".jrxml"), null == content ? "" : content); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	private void validateRequest(AbstractReportManagerNode report) {
		if(null == authenticatorServiceProvider.get().getCurrentUser())
			throw new ViolatedSecurityException();

		SecurityService securityService = securityServiceProvider.get();
		
		if(! securityService.checkActions(report, ReadAction.class))
			throw new ViolatedSecurityException();
	}


	
}
