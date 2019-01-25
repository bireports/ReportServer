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
 
 
package net.datenwerke.eximport.obj;

import java.util.Collection;
import java.util.HashSet;

import net.datenwerke.eximport.im.objectimporters.ImportableElement;
import nu.xom.Element;


/**
 * 
 *
 */
public class ExportedItem implements ImportableElement {

	private final String id;
	private final Class<?> type;
	private final Collection<ItemProperty> properties;
	private final Class<?> exporterType;
	private Element element;

	public ExportedItem(String id, Class<?> type, Collection<ItemProperty> properties, Class<?> exporterType, Element el) {
		super();
		this.id = id;
		this.type = type;
		this.properties = properties;
		this.exporterType = exporterType;
		this.element = el;
	}

	public String getId() {
		return id;
	}

	public Class<?> getType() {
		return type;
	}

	public Collection<ItemProperty> getProperties() {
		return properties;
	}

	public Class<?> getExporterType() {
		return exporterType;
	}

	public Collection<String> getReferencedIDs() {
		Collection<String> referencedIds = new HashSet<String>();
		
		for(ItemProperty property : getProperties())
			referencedIds.addAll(property.getReferencedIds());
		
		return referencedIds;
	}

	public Collection<String> getEnclosedObjectIds() {
		Collection<String> ids = new HashSet<String>();
		
		for(ItemProperty property : getProperties())
			ids.addAll(property.getEnclosedObjectIds());
		
		return ids;
	}
	
	public ItemProperty getPropertyByName(String name){
		for(ItemProperty property : properties)
			if(name.equals(property.getName()))
				return property;
		return null;
	}
	
	public Element getElement() {
		return element;
	}
	
}
