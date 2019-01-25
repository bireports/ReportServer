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
 
 
package net.datenwerke.rs.birt.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.datenwerke.rs.birt.service.reportengine.entities.BirtReport;
import net.datenwerke.rs.birt.service.reportengine.entities.BirtReportFile;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.security.server.SecuredHttpServlet;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.treedb.actions.ReadAction;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class BirtReportFileDownloadServlet extends SecuredHttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7092450658564936400L;
	private final Provider<ReportService> reportManagerProvider;
	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final Provider<SecurityService> securityServiceProvider;


	@Inject
	public BirtReportFileDownloadServlet(
			Provider<ReportService> reportManagerProvider,
			Provider<AuthenticatorService> authenticatorServiceProvider,
			Provider<SecurityService> securityServiceProvider) {
		
				this.reportManagerProvider = reportManagerProvider;
				this.authenticatorServiceProvider = authenticatorServiceProvider;
				this.securityServiceProvider = securityServiceProvider;
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
		
		if(rmn instanceof BirtReport){
			BirtReport report = (BirtReport)rmn;
			BirtReportFile reportFile = report.getReportFile();
			
			/* set mime type */
			response.setContentType("application/xml"); //$NON-NLS-1$
			
			/* set header and encoding */
			response.setHeader("Content-Disposition", "attachment;filename=\"" + reportFile.getName() + "\""); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			response.setCharacterEncoding("UTF-8"); //$NON-NLS-1$
			
			response.getOutputStream().write(reportFile.getContent().getBytes());
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
