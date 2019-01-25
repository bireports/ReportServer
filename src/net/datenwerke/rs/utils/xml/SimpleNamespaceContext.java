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
 
 
package net.datenwerke.rs.utils.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;

public class SimpleNamespaceContext implements NamespaceContext {

	private Map<String, String> namespaces;

	public SimpleNamespaceContext() {
		this.namespaces = new HashMap<String, String>();
	}
	
	public SimpleNamespaceContext(String prefix, String uri){
		this.namespaces = new HashMap<String, String>();
		this.namespaces.put(prefix, uri);
	}

	public SimpleNamespaceContext(Map<String, String> namespaces) {
		this.namespaces = new HashMap<String, String>(namespaces);
	}

	@Override
	public String getNamespaceURI(String prefix) {
		return namespaces.get(prefix);
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