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
 
 
package net.datenwerke.rs.base.service.reportengines.table.output.generator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.datenwerke.rs.base.service.reportengines.locale.ReportEnginesMessages;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column.ColumnFormatCellFormatter;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormatNumber;
import net.datenwerke.rs.base.service.reportengines.table.output.object.CompiledPDFTableReport;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorRuntimeException;
import net.datenwerke.rs.utils.misc.PdfUtils;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.google.inject.Inject;
import com.lowagie.text.DocumentException;

public class PdfTableOutputGenerator extends HTMLOutputGenerator {

	protected static final String CONFIG_FILE = "dynamiclists/pdfexport.cf";
	
	@Inject
	protected PdfUtils pdfUtils;

	private ITextRenderer renderer;
	
	@Override
	protected String getConfigFileLocation() {
		return CONFIG_FILE;
	}
	
	@Override
	protected String getBodyClass() {
		return super.getBodyClass() + " rs-reportexport-dl-pdf";
	}
	
	@Override
	protected void initWriter(OutputStream os)
			throws IOException {
		writer = new StringBuffer();
	}
	
	@Override
	protected void doAddCss() throws IOException {
		writer.append(themeServiceProvider.get().getTheme());
		
		if(null != cellFormatters){
			for(int i = 0; i < cellFormatters.length; i++){
				if(cellFormatters[i] instanceof ColumnFormatCellFormatter && ((ColumnFormatCellFormatter)cellFormatters[i]).getColumnFormat() instanceof ColumnFormatNumber){
					writer.append("tr td:nth-child(");
					writer.append("" + (i+1));
					writer.append(") { text-align: right; }");
				}
			}
		}
		
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
			writer.append("* { font-family: DejaVu Sans, Sans-Serif; }");
		}
	}
	
	
	@Override
	public CompiledReport getTableObject() {
		/* init rendered */
		renderer = new ITextRenderer();
		try {
			pdfUtils.configureFontResolver(renderer.getFontResolver());
			String html = writer.toString();
			renderer.setDocumentFromString(html);
			
			if(null == os)
				os = new ByteArrayOutputStream();
			renderer.layout();
			renderer.createPDF(os);

			byte[] cReport = os instanceof ByteArrayOutputStream ? ((ByteArrayOutputStream)os).toByteArray() : null;

			return new CompiledPDFTableReport(cReport);
		} catch (DocumentException e) {
			throw new ReportExecutorRuntimeException(e);
		} catch (IOException e) {
			throw new ReportExecutorRuntimeException(e);
		}
	}
	
	@Override
	public CompiledReport getFormatInfo() {
		return new CompiledPDFTableReport();
	}
	
	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_PDF};
	}
	
	@Override
	public boolean supportsStreaming() {
		return false;
	}
}
