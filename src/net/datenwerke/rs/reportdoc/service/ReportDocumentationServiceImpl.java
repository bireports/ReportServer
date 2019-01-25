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
 
 
package net.datenwerke.rs.reportdoc.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportserver.ServerInfoContainer;
import net.datenwerke.rs.reportdoc.service.helper.ReportDocGroovyHelper;
import net.datenwerke.rs.reportdoc.service.helper.ReportDocPdfRenderer;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.usermanager.entities.User;

/**
 * 
 *
 */
@Singleton
public class ReportDocumentationServiceImpl implements ReportDocumentationService {



	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private static final String GROOVY = "groovy";

	private final static String BASE_DOCUMENTATION_LOCATION = "reports/report_documentation.groovy";
	private final static String REVISION_DOCUMENTATION_LOCATION = "reports/report_documentation_rev.groovy";
	private final static String REVISIONS_LOCATION = "reports/revisions.groovy";

	
	private final Provider<ReportDocGroovyHelper> groovyHelperProvider;
	private final Provider<ReportDocPdfRenderer> pdfRendererProvider;
	private final Provider<ServerInfoContainer> serverInfoProvider; 
	private final Provider<AuthenticatorService> authenticatorServiceProvider; 
	
	private CompiledScript baseDocumentation;
	private CompiledScript revisions;
	private CompiledScript revDocumentation;
	
	@Inject
	public ReportDocumentationServiceImpl(
			Provider<ReportDocGroovyHelper> groovyHelperProvider,
			Provider<ReportDocPdfRenderer> pdfRendererProvider,
			Provider<ServerInfoContainer> serverInfoProvider,
			Provider<AuthenticatorService> authenticatorServiceProvider
		){
			this.groovyHelperProvider = groovyHelperProvider;
			this.pdfRendererProvider = pdfRendererProvider;
			this.serverInfoProvider = serverInfoProvider;
			this.authenticatorServiceProvider = authenticatorServiceProvider;
		
	}

	@Override
	public CompiledReport executeDocumentation(Report report, Long tsId, String format) throws ScriptException {
		CompiledScript compiled = getCompiledDocumentation();
		
		Map<String, Object> binding = new HashMap<String,Object>();
		binding.put("outputFormat", null != format ? format.toLowerCase() : "html");
		
		/* simulate parameter map */
		Map<String, Object> paramMap = new HashMap<String, Object>();
		binding.put("parameterMap", paramMap);
		
		paramMap.put("reportId", report.getId());
		paramMap.put("tsObjectId", tsId);
		
		Object result = execute(compiled, binding);
		
		return (CompiledReport) result;
	}
	
	@Override
	public CompiledReport executeDocumentationRev(Report report, Long revision, String format) throws ScriptException {
		CompiledScript compiled = getCompiledDocumentationRev();
		
		Map<String, Object> binding = new HashMap<String,Object>();
		binding.put("outputFormat", null != format ? format.toLowerCase() : "html");
		
		/* simulate parameter map */
		Map<String, Object> paramMap = new HashMap<String, Object>();
		binding.put("parameterMap", paramMap);
		
		paramMap.put("reportId", report.getId());
		paramMap.put("revId", revision);
		
		Object result = execute(compiled, binding);
		
		return (CompiledReport) result;
	}
	
	@Override
	public CompiledReport executeRevisions(Report report, String format) throws ScriptException {
		CompiledScript compiled = getCompiledRevisions();
		
		Map<String, Object> binding = new HashMap<String,Object>();
		binding.put("outputFormat", null != format ? format.toLowerCase() : "html");
		
		/* simulate parameter map */
		Map<String, Object> paramMap = new HashMap<String, Object>();
		binding.put("parameterMap", paramMap);
		
		paramMap.put("reportId", report.getId());

		Object result = execute(compiled, binding);
		
		return (CompiledReport) result;
	}

	protected Object execute(CompiledScript script, Map<String, Object> bindingMap) throws ScriptException {
		SimpleBindings binding = new SimpleBindings(bindingMap);
		binding.put("GLOBALS", groovyHelperProvider.get());
		
		Map<String,Object> rendererMap = new HashMap<String,Object>();
		rendererMap.put("pdf", pdfRendererProvider.get());
		binding.put("renderer", rendererMap);
		
		/* base url */
		String baseUrl = "";
		try {
			baseUrl = serverInfoProvider.get().getBaseURL() + "/";
		} catch(Exception e){}
		binding.put("baseurl", baseUrl);
		
		/* user */
		try{
			User currentUser = authenticatorServiceProvider.get().getCurrentUser();
			binding.put("user", currentUser);
		}catch(Exception e){}
		
		return script.eval(binding);
	}
	
	protected CompiledScript getCompiledRevisions() {
		if(null != revisions)
			return revisions;
		
		try {
			revisions = compile(REVISIONS_LOCATION);
		} catch (Exception e) {
			logger.warn("Could not load report revisions", e);
			throw new IllegalStateException(e);
		}
			
		return revisions;
	}

	protected synchronized CompiledScript getCompiledDocumentation() {
		if(null != baseDocumentation)
			return baseDocumentation;
		
		try {
			baseDocumentation = compile(BASE_DOCUMENTATION_LOCATION);
		} catch (Exception e) {
			logger.warn("Could not load report documentation", e);
			throw new IllegalStateException(e);
		}
			
		return baseDocumentation;
	}
	
	protected synchronized CompiledScript getCompiledDocumentationRev() {
		if(null != revDocumentation)
			return revDocumentation;
		
		try {
			revDocumentation = compile(REVISION_DOCUMENTATION_LOCATION);
		} catch (Exception e) {
			logger.warn("Could not load report documentation", e);
			throw new IllegalStateException(e);
		}
			
		return revDocumentation;
	}

	protected CompiledScript compile(String location) throws IOException, ScriptException {
		String report = IOUtils.toString(getClass().getResourceAsStream(location));
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine groovy = manager.getEngineByName(GROOVY);
		Compilable compGroovy = (Compilable) groovy;
		return compGroovy.compile(report);
	}
	
	

}
