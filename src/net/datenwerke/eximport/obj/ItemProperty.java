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
 
 
package net.datenwerke.eximport.obj;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import nu.xom.Element;

/**
 * 
 *
 */
public abstract class ItemProperty {

	protected final String name;
	protected final Class<?> type;
	protected final Element element;
	
	public ItemProperty(String name, Class<?> type, Element element) {
		super();
		this.name = name;
		this.type = type;
		this.element = element;
	}

	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}

	public Collection<String> getReferencedIds() {
		return new HashSet<String>();
	}

	public Collection<String> getEnclosedObjectIds() {
		return Collections.emptySet();
	}
	
	public Element getElement() {
		return element;
	}
	
}
