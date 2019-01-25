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
 
 
package net.datenwerke.eximport.xml;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

import net.datenwerke.eximport.ExImportHelperService;

public class ExImportNamespaceContext implements NamespaceContext {

	@Override
	public String getNamespaceURI(String prefix) {
		if (prefix == null) 
			throw new NullPointerException("Null prefix");
        else if (ExImportHelperService.EXIMPORT_XML_NAMESPACE_PREFIX.equals(prefix)) 
        	return ExImportHelperService.EXIMPORT_XML_NAMESPACE;
        else if ("xml".equals(prefix)) 
        	return XMLConstants.XML_NS_URI;
        else if("null".equals(prefix))
        	return XMLConstants.NULL_NS_URI;
		
		return ExImportHelperService.EXIMPORT_XML_NAMESPACE;
	}

	@Override
	public String getPrefix(String namespaceURI) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterator getPrefixes(String namespaceURI) {
		throw new UnsupportedOperationException();
	}

}
