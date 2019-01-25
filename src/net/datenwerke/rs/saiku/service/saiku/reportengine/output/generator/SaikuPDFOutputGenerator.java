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
 
 
package net.datenwerke.rs.saiku.service.saiku.reportengine.output.generator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.olap4j.CellSet;
import org.saiku.olap.dto.resultset.CellDataSet;
import org.saiku.olap.query2.ThinHierarchy;
import org.saiku.olap.util.formatter.ICellSetFormatter;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.google.inject.Inject;
import com.lowagie.text.DocumentException;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.reportengines.locale.ReportEnginesMessages;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorRuntimeException;
import net.datenwerke.rs.saiku.service.saiku.reportengine.output.object.CompiledPDFSaikuReport;
import net.datenwerke.rs.saiku.service.saiku.reportengine.output.object.CompiledRSSaikuReport;
import net.datenwerke.rs.utils.misc.PdfUtils;

public class SaikuPDFOutputGenerator extends SaikuHTMLOutputGenerator {
	
	@Inject
	protected PdfUtils pdfUtils;

	@Inject
	public SaikuPDFOutputGenerator(HookHandlerService hookHandler) {
		super(hookHandler);
	}


	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_PDF};
	}

	@Override
	public CompiledReport getFormatInfo() {
		return new CompiledPDFSaikuReport();
	}
	
	@Override
	protected String getBodyClass() {
		return super.getBodyClass() + " rs-reportexport-dl-pdf";
	}
	
	@Override
	protected String getConfigFileLocation() {
		return "reportengines/saiku.pdfexport.cf";
	}


	@Override
	public CompiledRSSaikuReport exportReport(CellDataSet cellDataSet,
			CellSet cellset, List<ThinHierarchy> filters, ICellSetFormatter formatter,
			String outputFormat, ReportExecutionConfig... configs)
			throws ReportExecutorException {
		CompiledRSSaikuReport htmlReport = super.exportReport(cellDataSet, cellset, filters, formatter, outputFormat, configs);
		
		ITextRenderer renderer = new ITextRenderer();
		try {
			pdfUtils.configureFontResolver(renderer.getFontResolver());
			String html = (String) htmlReport.getReport();
			renderer.setDocumentFromString(html);
			
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			renderer.layout();
			renderer.createPDF(os);

			byte[] cReport = os instanceof ByteArrayOutputStream ? ((ByteArrayOutputStream)os).toByteArray() : null;

			return new CompiledPDFSaikuReport(cReport);
		} catch (DocumentException e) {
			throw new ReportExecutorRuntimeException(e);
		} catch (IOException e) {
			throw new ReportExecutorRuntimeException(e);
		}
	}
	
	@Override
	protected void doAddCss(StringBuilder writer, Configuration configFile) throws IOException {
		writer.append(themeServiceProvider.get().getTheme());

		String style = configFile.getString(STYLE_PROPERTY, "");
		if(null != style && ! "".equals(style.trim())){
			String html = parseTemplate(style);
			writer.append(html);
		} else {
			writer.append("@page {size: A4 landscape;margin-top:1.5cm;");
			writer.append("@top-left { content: \"" + org.apache.commons.lang.StringEscapeUtils.unescapeHtml(report.getName()) + "\"; font-family: DejaVu Sans, Sans-Serif; font-size: 8pt; }");
			writer.append("@top-right {content: \"" + getNowString() + "\"; font-family: DejaVu Sans, Sans-Serif; font-size: 8pt; }");
			writer.append("@bottom-right { content: \"" + ReportEnginesMessages.INSTANCE.page()+ " \" counter(page) \" " + ReportEnginesMessages.INSTANCE.of() + " \" counter(pages); font-family: DejaVu Sans, Sans-Serif; font-size: 8pt; }");
			writer.append("}");
		}
	}
	

}
