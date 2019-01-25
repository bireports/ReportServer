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
 
 
package net.datenwerke.rs.reportdoc.server;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Singleton;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportserver.ReportServerService;
import net.datenwerke.rs.reportdoc.service.ReportDocumentationService;
import net.datenwerke.rs.utils.filename.FileNameService;
import net.datenwerke.rs.utils.misc.HttpUtils;
import net.datenwerke.security.server.SecuredHttpServlet;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Execute;

@Singleton
public class ReportDocumentationServlet extends SecuredHttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String PROPERTY_ID = "id";
	public static final String PROPERTY_TEAMSPACE_ID = "tsid";
	public static final String PROPERTY_FORMAT = "format";
	public static final String PROPERTY_DOWNLOAD = "download";
	
	public static final String PROPERTY_REPORT = "report";
	public static final String PROPERTY_REVID = "revid";
	
	private final Provider<ReportDocumentationService> documentationServiceProvider;
	private final Provider<SecurityService> securityServiceProvider;
	private final Provider<ReportService> reportServiceProvider;
	private final Provider<ReportServerService> reportServerServiceProvider;
	private final Provider<HttpUtils> httpUtilsProvider;
	
	@Inject
	public ReportDocumentationServlet(
		Provider<ReportDocumentationService> documentationServiceProvider,
		Provider<SecurityService> securityServiceProvider,
		Provider<ReportService> reportServiceProvider,
		Provider<ReportServerService> reportServerServiceProvider,
		Provider<HttpUtils> httpUtilsProvider
		) {
		super();
		this.documentationServiceProvider = documentationServiceProvider;
		this.securityServiceProvider = securityServiceProvider;
		this.reportServiceProvider = reportServiceProvider;
		this.reportServerServiceProvider = reportServerServiceProvider;
		this.httpUtilsProvider = httpUtilsProvider;
	}





	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		if("revisions".equals(request.getParameter(PROPERTY_REPORT))){
			getRevisions(request, response);
		} else if("revdocumentation".equals(request.getParameter(PROPERTY_REPORT))){
			getRevisionDocumentation(request, response);
		} else {
			getDocumentation(request, response);
		}
		
	}


	protected void getRevisions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String strId = request.getParameter(PROPERTY_ID);
		
		if(null == strId || "".equals(strId))
			throw new IllegalArgumentException("expected report id as parameter");
		
		/* try get report id */
		Long reportId = Long.parseLong(strId);

		/* load report */
		Report report = reportServiceProvider.get().getReportById(reportId);
		if(null == report)
			throw new IllegalArgumentException("Could not load report with id " + reportId);
		
		/* check security */
		if(! securityServiceProvider.get().checkRights(report, Execute.class))
			throw new ViolatedSecurityException();
		
		/* get format */
		String format = null != request.getParameter(PROPERTY_FORMAT) ? request.getParameter(PROPERTY_FORMAT) : "HTML";
		
		try {
			CompiledReport compiledReport = documentationServiceProvider.get().executeRevisions(report, format);
			
			output(request, response, compiledReport, "rev-" + report.getId() + "-" + report.getName());
		} catch (ScriptException e) {
			throw new IllegalStateException(e);
		}
		
	}
	
	protected void getRevisionDocumentation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String strId = request.getParameter(PROPERTY_ID);
		String strRevision = request.getParameter(PROPERTY_REVID);
		
		if(null == strId || "".equals(strId))
			throw new IllegalArgumentException("expected report id as parameter");
		if(null == strRevision || "".equals(strRevision))
			throw new IllegalArgumentException("expected rev id as parameter");
		
		/* try get report id */
		Long reportId = Long.parseLong(strId);
		Long revId = Long.parseLong(strRevision);
		
		/* load report */
		Report report = reportServiceProvider.get().getReportById(reportId);
		if(null == report)
			throw new IllegalArgumentException("Could not load report with id " + reportId);
		
		/* check security */
		if(! securityServiceProvider.get().checkRights(report, Execute.class))
			throw new ViolatedSecurityException();
		
		/* get format */
		String format = null != request.getParameter(PROPERTY_FORMAT) ? request.getParameter(PROPERTY_FORMAT) : "HTML";
		
		try {
			CompiledReport compiledReport = documentationServiceProvider.get().executeDocumentationRev(report, revId, format);
			
			output(request, response, compiledReport, "doc-" + report.getId() + "-" + report.getName());
		} catch (ScriptException e) {
			throw new IllegalStateException(e);
		}
		
	}

	protected void getDocumentation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String strId = request.getParameter(PROPERTY_ID);
		String strTsId = request.getParameter(PROPERTY_TEAMSPACE_ID);
		
		if(null == strId || "".equals(strId))
			throw new IllegalArgumentException("expected report id as parameter");
		
		/* try get report id */
		Long reportId = Long.parseLong(strId);
		Long tsId = null;
		if(null != strTsId && ! "".equals(strTsId.trim()) )
			tsId = Long.parseLong(strTsId);
		
		/* load report */
		Report report = reportServiceProvider.get().getReportById(reportId);
		if(null == report)
			throw new IllegalArgumentException("Could not load report with id " + reportId);
		
		/* check security */
		if(! securityServiceProvider.get().checkRights(report, Execute.class))
			throw new ViolatedSecurityException();
		
		/* get format */
		String format = null != request.getParameter(PROPERTY_FORMAT) ? request.getParameter(PROPERTY_FORMAT) : "HTML";
		
		try {
			CompiledReport compiledReport = documentationServiceProvider.get().executeDocumentation(report, tsId, format);
			
			output(request, response, compiledReport, "doc-" + report.getId() + "-" + report.getName());
		} catch (ScriptException e) {
			throw new IllegalStateException(e);
		}
		
	}


	protected void output(HttpServletRequest request, HttpServletResponse response,  CompiledReport compiledReport, String name) throws IOException {
		/* output file name */
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String fileName = dateFormat.format(Calendar.getInstance().getTime()) + "_" + name;

		/* set mime type */
		response.setContentType(compiledReport.getMimeType());

		/* cache */
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.
		
		/* set header and encoding */
		fileName += "." + compiledReport.getFileExtension();
		
		boolean download = "true".equals(request.getParameter(PROPERTY_DOWNLOAD));
		
		response.setHeader(HttpUtils.CONTENT_DISPOSITION, httpUtilsProvider.get().makeContentDispositionHeader(download, fileName));

		if(compiledReport.isStringReport()){
			/* get charset */
			String charset = reportServerServiceProvider.get().getCharset();
			response.setCharacterEncoding(charset); 
			
			try(PrintWriter writer = response.getWriter();){
				writer.write((String)compiledReport.getReport());
			}
		} else {
			try(OutputStream os = response.getOutputStream()){
				os.write((byte[]) compiledReport.getReport());
			}
		}
	}
}
