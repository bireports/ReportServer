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
import java.util.List;

import nu.xom.Element;


/**
 * 
 *
 */
public class ItemPropertyCollection extends ItemProperty {

	protected final Class<?> propertyType;
	protected final List<ItemProperty> collectionValues;
	
	public ItemPropertyCollection(String name, Class<?> type, Class<?> propertyType, List<ItemProperty> collectionValues, Element el) {
		super(name, type, el);
		
		this.propertyType = propertyType;
		this.collectionValues = collectionValues;
	}

	public Class<?> getPropertyType() {
		return propertyType;
	}

	public List<ItemProperty> getCollectionValues() {
		return collectionValues;
	}
	
	public Collection<String> getReferencedIds() {
		Collection<String> references =  new HashSet<String>();
		
		for(ItemProperty property : getCollectionValues())
			references.addAll(property.getReferencedIds());
		
		return references;
	}
	
	public Collection<String> getEnclosedObjectIds() {
		Collection<String> ids =  new HashSet<String>();
		
		for(ItemProperty property : getCollectionValues())
			ids.addAll(property.getEnclosedObjectIds());
		
		return ids;
	}


}
