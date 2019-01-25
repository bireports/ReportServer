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
import java.util.HashSet;

import net.datenwerke.eximport.im.objectimporters.ImportableElement;
import nu.xom.Element;


/**
 * 
 *
 */
public class EnclosedItemProperty extends ItemProperty implements ImportableElement {

	protected final String id;
	protected final Class<?> exporterType;
	protected final Collection<ItemProperty> itemProperties;
	
	
	public EnclosedItemProperty(String name, Class<?> type, String id, Class<?> exporterType, Collection<ItemProperty> itemProperties, Element el) {
		super(name, type, el);

		/* store objects */
		this.id = id;
		this.exporterType = exporterType;
		this.itemProperties = itemProperties;
	}
	
	public String getId(){
		return id;
	}

	public Class<?> getExporterType() {
		return exporterType;
	}

	public Collection<ItemProperty> getProperties() {
		return itemProperties;
	}
	
	public Collection<String> getReferencedIds() {
		Collection<String> references =  new HashSet<String>();
		
		for(ItemProperty property : getProperties())
			references.addAll(property.getReferencedIds());
		
		return references;
	}
	
	public Collection<String> getEnclosedObjectIds() {
		Collection<String> ids =  new HashSet<String>();
		ids.add(getId());
		
		for(ItemProperty property : getProperties())
			ids.addAll(property.getEnclosedObjectIds());
		
		return ids;
	}

}
