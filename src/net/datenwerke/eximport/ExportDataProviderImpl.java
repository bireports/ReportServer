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
 
 
package net.datenwerke.eximport;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;

import nu.xom.Attribute.Type;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.NodeFactory;
import nu.xom.Nodes;

public class ExportDataProviderImpl implements ExportDataProvider {

	private File input;
	private byte[] byteInput;
	
	private Map<String, String> elementMap = new HashMap<String, String>();
	private Map<String, String> exporterTypeMap = new HashMap<String, String>();
	private Map<String, String> enclosedMap = new HashMap<String, String>();

	public ExportDataProviderImpl(File input){
		this.input = input;
		init();
	}
	
	public ExportDataProviderImpl(byte[] data) {
		this.byteInput = data;
		init();
	}

	private void init() {
		Builder builder = new Builder(new NodeFactory(){
			private boolean isExportElement = false;
			private boolean isExporterType = false;

			private boolean inExportElement = false;
			
			private String id = null;
			private String exporter = null;
			private Nodes empty = new Nodes();

			@Override
			public Element startMakingElement(String name, String namespace) {
				isExporterType = ExImportHelperService.EXPORTER_BASE_ELEMENT.equals(name);
				isExportElement = ExImportHelperService.EXPORTED_ITEM_ELEMENT_NAME.equals(name);
				if(isExportElement)
					inExportElement = true;
				return super.startMakingElement(name, namespace);
			}
			
			@Override
			public Nodes makeAttribute(String name, String URI, String value,
					Type type) {
				if(isExportElement && "xml:id".equals(name))
					id = value;
				if(isExporterType && ExImportHelperService.EXPORTER_TYPE.equals(name))
					exporter = value;
				return super.makeAttribute(name, URI, value, type);
			}
			
			@Override
			public Nodes finishMakingElement(Element element) {
				if (element.getQualifiedName().equals(ExImportHelperService.EXPORTED_PROPERTY_ELEMENT_NAME) || element.getQualifiedName().equals(ExImportHelperService.COLLECTION_VALUE_ELEMENT)) {
					String innerExporter = element.getAttributeValue(ExImportHelperService.EXPORTER_TYPE);
					String innerId = element.getAttributeValue("id", XMLConstants.XML_NS_URI);
					if(null != innerExporter && null != innerId){
						exporterTypeMap.put( innerId, innerExporter);
						elementMap.put(innerId, element.toXML());
						enclosedMap.put(innerId, id);
					}
				} else if (element.getQualifiedName().equals(ExImportHelperService.EXPORTED_ITEM_ELEMENT_NAME)) {
					String xml = element.toXML();
					elementMap.put(id, xml);
					exporterTypeMap.put(id, exporter);
					
					isExportElement = false;
					inExportElement = false;
					id = null;
				}
				if(inExportElement || element.getParent() instanceof Document)
					return super.finishMakingElement(element);
		        return empty;  
			}
			
			@Override
		    public Nodes makeDocType(String rootElementName, 
		    	      String publicID, String systemID) {
    	        return empty;    
    	    }
			
			@Override
			public Nodes makeText(String data) {
				return super.makeText(data);
			}


		});

		try {
			builder.build(getXmlStream());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	@Override
	public InputStream getXmlStream() {
		if(null != input){
			try {
				return new FileInputStream(input);
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
		return new ByteArrayInputStream(byteInput);
	}

	@Override
	public Element getElementById(String id) {
		if(elementMap.containsKey(id)){
			try {
				String xml = elementMap.get(id);
				Element val = (Element) new Builder().build(new StringReader(xml)).getRootElement();
				return val;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else 
			throw new IllegalArgumentException("no idea who " + id + "is.");
	}

	@Override
	public String getExportertTypeById(String id) {
		if(! exporterTypeMap.containsKey(id))
			throw new IllegalArgumentException("No idea what exporter to use for " + id);
		return exporterTypeMap.get(id);
	}

	@Override
	public Element getExportedItemWithEnclosed(String id) {
		return getElementById(enclosedMap.get(id));
	}

}
