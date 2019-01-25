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
 
 
package net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.generator;

import java.io.OutputStream;
import java.io.StringWriter;
import java.sql.Connection;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;
import net.datenwerke.rs.core.service.reportmanager.engine.basereports.CompiledHtmlReportImpl;
import net.datenwerke.rs.core.service.reportmanager.engine.config.ReportExecutionConfig;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.utils.XlsxToHtmlConverter;

import org.apache.poi.hssf.converter.ExcelToHtmlConverter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;

public class JxlsHTMLOutputGenerator extends JxlsOutputGeneratorImpl {

	@Override
	public String[] getFormats() {
		return new String[]{ReportExecutorService.OUTPUT_FORMAT_HTML};
	}
	
	@Override
	public CompiledReport getFormatInfo() {
		return new CompiledHtmlReportImpl(null);
	}
	
	@Override
	public CompiledReport exportReport(OutputStream os, byte[] template,
			Connection connection, ParameterSet parameters,
			String outputFormat, ReportExecutionConfig... configs)
			throws ReportExecutorException {
		Workbook workbook = processWorkbook(parameters, template, connection);
		
		try{
			Document xmlDocument = DocumentBuilderFactory.newInstance()
		            .newDocumentBuilder().newDocument();
			
			Transformer transformer = TransformerFactory.newInstance()
		            .newTransformer();
		    transformer.setOutputProperty( OutputKeys.INDENT, "no" );
		    transformer.setOutputProperty( OutputKeys.ENCODING, "utf-8" );
		    transformer.setOutputProperty( OutputKeys.METHOD, "html" );

		    String html = null;
			
			if(workbook instanceof HSSFWorkbook){
				ExcelToHtmlConverter xlsToHtmlConverter = new ExcelToHtmlConverter(xmlDocument);
				xlsToHtmlConverter.processWorkbook(  (HSSFWorkbook) workbook );
				
				if(null == os){
				    StringWriter stringWriter = new StringWriter();
				    transformer.transform(
				            new DOMSource( xlsToHtmlConverter.getDocument() ),
				            new StreamResult( stringWriter ) );
			
				    html = stringWriter.toString();
			    } else {
			    	 transformer.transform(
					            new DOMSource( xlsToHtmlConverter.getDocument() ),
					            new StreamResult(os) );
			    }
			} else if(workbook instanceof XSSFWorkbook) {
				XlsxToHtmlConverter xlsToHtmlConverter = new XlsxToHtmlConverter(xmlDocument);
				xlsToHtmlConverter.processWorkbook(  (XSSFWorkbook) workbook );
				
				if(null == os){
				    StringWriter stringWriter = new StringWriter();
				    transformer.transform(
				            new DOMSource( xlsToHtmlConverter.getDocument() ),
				            new StreamResult( stringWriter ) );
			
				    html = stringWriter.toString();
			    } else {
			    	 transformer.transform(
					            new DOMSource( xlsToHtmlConverter.getDocument() ),
					            new StreamResult(os) );
			    }
			} else {
				html = "Could not convert file";
			}
		    
			return new CompiledHtmlReportImpl(html);
		} catch(Exception e){
			throw new ReportExecutorException(e);
		}
	}
}
