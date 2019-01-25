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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper.output.generator;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.output.object.CompiledHTMLJasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.output.object.CompiledRSJasperReport;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.RECPaged;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.security.service.usermanager.entities.User;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class JasperHTMLOutputGenerator extends JasperOutputGeneratorImpl {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	@Inject
	public JasperHTMLOutputGenerator(
		HookHandlerService hookHandler	
		){
		super(hookHandler);
	}
	
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_HTML};
	}
	

	public CompiledRSJasperReport exportReport(JasperPrint jasperPrint, String outputFormat, JasperReport report, User user, ReportExecutionConfig... configs) {
		JRAbstractExporter exporter;
		
		exporter = new JRHtmlExporter();
		
		/* create buffer for output */
		StringBuffer out = new StringBuffer();
		
		/* configure exporter */
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STRING_BUFFER, out);
		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
		
		/* preview ? */
		if(hasConfig(RECPaged.class, configs)){
			exporter.setParameter(JRExporterParameter.START_PAGE_INDEX, getConfig(RECPaged.class, configs).getFirstPage());
			exporter.setParameter(JRExporterParameter.END_PAGE_INDEX, getConfig(RECPaged.class, configs).getLastPage());
		}
		
		callPreHooks(outputFormat, exporter, report, user);
		
		/* export */
		String htmlReport = ""; //$NON-NLS-1$
		try {
			exporter.exportReport();
			
			/* create html report */
			htmlReport = out.toString();
		} catch (JRException e) {
			logger.warn( e.getMessage(), e);
		}
		
		/* create return object */
		CompiledRSJasperReport cjrReport = new CompiledHTMLJasperReport();
		cjrReport.setData(jasperPrint);
		
		/* add report to object */
		cjrReport.setReport(htmlReport);
		
		callPostHooks(outputFormat, exporter, report, cjrReport, user);
		
		/* return compiled report */
		return cjrReport;
	}

	@Override
	public CompiledReport getFormatInfo() {
		return new CompiledHTMLJasperReport();
	}

}
