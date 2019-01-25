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
 
 
package net.datenwerke.rs.reportdoc.service.helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.basereports.CompiledPdfReport;
import net.datenwerke.rs.utils.misc.PdfUtils;

public class ReportDocPdfRenderer {
	
	private PdfUtils pdfUtils;

	@Inject
	public ReportDocPdfRenderer(PdfUtils pdfUtils) {
		this.pdfUtils = pdfUtils;
	}

	public CompiledReport render(Object input)  {
		ITextRenderer renderer = new ITextRenderer();
		try {
			pdfUtils.configureFontResolver(renderer.getFontResolver());
		} catch (DocumentException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		renderer.setDocumentFromString((String) input);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		renderer.layout();
		try {
			renderer.createPDF(os);
			os.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		byte[] cReport = os.toByteArray();

		return new CompiledPdfReport(cReport);
	}

}
