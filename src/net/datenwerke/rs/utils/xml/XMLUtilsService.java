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
 
 
package net.datenwerke.rs.utils.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;



public interface XMLUtilsService {

	public Document readStringIntoJAXPDoc(String data) throws ParserConfigurationException, SAXException, IOException;
	
	public Document readStringIntoJAXPDoc(String data, String encoding) throws ParserConfigurationException, SAXException, IOException;
	
	/**
	 * Reads a file into a JAXP XML document.
	 * 
	 * @param file The input file.
	 * @return The JAXP XML document.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document readInputFileIntoJAXPDoc(File file)
			throws ParserConfigurationException, SAXException, IOException;

	/**
	 * Reads an InputStream into a JAXP XML document.
	 * 
	 * @param in The input stream.
	 * @return The JAXP XML document.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document readInputStreamIntoJAXPDoc(InputStream in)
			throws ParserConfigurationException, SAXException, IOException;

	/**
	 * Creates an empty JAXP XML document.
	 * 
	 * @return An empty JAXP XML document.
	 */
	public Document getNewEmptyJAXPDoc();

	/**
	 * Transforms an XML node (from a JAXP XML document) into a formatted string.
	 * 
	 * @param node The node to transform.
	 * @return A string representation of the node. 
	 */
	public String getXMLNodeAsString(Node node);
	
	public String getXMLNodeAsString(Node node, boolean omitXMLDeclaration);

	public XPathExpression getXPath(String string) throws XPathException;
	public XPathExpression getXPath(String string, NamespaceContext namespaceContext) throws XPathException;


}
