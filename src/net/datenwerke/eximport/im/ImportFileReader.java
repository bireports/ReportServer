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
 
 
package net.datenwerke.eximport.im;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import net.datenwerke.eximport.exceptions.InvalidImportDocumentException;

import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

@Deprecated
public class ImportFileReader {

	public static final String EXIMPORT_SCHEMA_LOCATION = "resources/schemas/eximport.xsd";
	private boolean ignoreWarnings = true;
	private boolean ignoreErrors = false;
	
	private boolean foundFatalError = false;
	private boolean foundWarning = false;
	private boolean foundError = false;
	
	public ImportFileReader(){
		
	}

	public void setIgnoreWarnings(boolean ignoreWarnings) {
		this.ignoreWarnings = ignoreWarnings;
	}

	public boolean isIgnoreWarnings() {
		return ignoreWarnings;
	}

	public void setIgnoreErrors(boolean ignoreErrors) {
		this.ignoreErrors = ignoreErrors;
	}

	public boolean isIgnoreErrors() {
		return ignoreErrors;
	}
	
	public Document loadData(String data) throws InvalidImportDocumentException, SAXException, ParserConfigurationException, IOException{
		return loadData(new ByteArrayInputStream(data.getBytes()));
	}
	
	public Document loadData(InputStream is) throws InvalidImportDocumentException, SAXException, ParserConfigurationException, IOException{
		URL schemaUrl = getClass().getClassLoader().getResource(EXIMPORT_SCHEMA_LOCATION);
		Schema schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(schemaUrl);
		
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		
        docBuilderFactory.setNamespaceAware(true);
        docBuilderFactory.setSchema(schema);

        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		final StringBuilder errorBuilder = new StringBuilder();
        docBuilder.setErrorHandler(new ErrorHandler() {
			
			@Override
			public void warning(SAXParseException e) throws SAXException {
				foundWarning = true;
				errorBuilder.append("warning: " + e.getMessage() + "\n");
			}
			
			@Override
			public void fatalError(SAXParseException e) throws SAXException {
				foundFatalError = true;
				errorBuilder.append("fatalError: " + e.getMessage() + "\n");
			}
			
			@Override
			public void error(SAXParseException e) throws SAXException {
				foundError = true;
				errorBuilder.append("error: " + e.getMessage() + "\n");		
			}
		});

		Document doc = docBuilder.parse(is);
		
		if(foundFatalError)
			throw new InvalidImportDocumentException("Problems with import document: \n" + errorBuilder);
		if(foundError && ! ignoreErrors)
			throw new InvalidImportDocumentException("Problems with import document: \n" + errorBuilder);
		if(foundWarning && ! ignoreWarnings)
			throw new InvalidImportDocumentException("Problems with import document: \n" + errorBuilder);
		
		return doc;
	}
	
}
