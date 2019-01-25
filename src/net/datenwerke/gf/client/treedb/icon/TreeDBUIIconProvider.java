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
 
 
package net.datenwerke.gf.client.treedb.icon;

import java.util.HashMap;
import java.util.Map;

import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.data.shared.IconProvider;


/**
 * 
 *
 */
public class TreeDBUIIconProvider implements IconProvider<AbstractNodeDto>{

	private Map<Class<? extends AbstractNodeDto>, IconMapping> entries = new HashMap<Class<? extends AbstractNodeDto>, IconMapping>();
	
	public TreeDBUIIconProvider(IconMapping... icons){
		addMappings(icons);
	}
	
	public void addMappings(IconMapping... icons){
		for(IconMapping icon : icons)
			entries.put(icon.getType(), icon);
	}
	
	public void addMapping(IconMapping mapping){
		entries.put(mapping.getType(), mapping);
	}
	
	public ImageResource getIcon(AbstractNodeDto model) {
		Class<?> currentClass = model.getClass();
		while(null != currentClass){
			if(entries.containsKey(currentClass)){
				IconMapping mapping = entries.get(currentClass);
				return mapping.getIcon(model);
			}
			currentClass = currentClass.getSuperclass();
		}
		return null;
	}
	
}
