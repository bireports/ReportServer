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
 
 
package net.datenwerke.treedb.client.treedb.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.datenwerke.gxtdto.client.dtomanager.DtoContainer;
import net.datenwerke.gxtdto.client.dtomanager.DtoRegistrar;

public class EntireTreeDTO implements DtoContainer, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2937390159776898192L;
	
	private List<AbstractNodeDto> roots = new ArrayList<AbstractNodeDto>();
	private Map<AbstractNodeDto, List<AbstractNodeDto>> childrenMap = new HashMap<AbstractNodeDto, List<AbstractNodeDto>>();
	
	public List<AbstractNodeDto> getRoots() {
		return roots;
	}
	public void setRoots(List<AbstractNodeDto> roots) {
		this.roots = roots;
	}
	public void addRoot(AbstractNodeDto root){
		roots.add(root);
	}
	

	public void setChildrenMap(Map<AbstractNodeDto, List<AbstractNodeDto>> childrenMap) {
		this.childrenMap = childrenMap;
	}
	
	public Map<AbstractNodeDto, List<AbstractNodeDto>> getChildrenMap() {
		return childrenMap;
	}
	
	public void addChild(AbstractNodeDto parent, AbstractNodeDto child){
		if(! (childrenMap.get(parent) instanceof List))
			childrenMap.put(parent, new ArrayList<AbstractNodeDto>());
		childrenMap.get(parent).add(child);
	}

	public List<AbstractNodeDto> getChildrenFor(AbstractNodeDto parent){
		if(! (childrenMap.get(parent) instanceof List))
			return new ArrayList<AbstractNodeDto>();
		return childrenMap.get(parent);
	}
	
	@Override
	public void registerDtos(DtoRegistrar registrar) {
		roots = registrar.registerDtos(roots);
		
		Map<AbstractNodeDto, List<AbstractNodeDto>> newChildrenMap = new HashMap<AbstractNodeDto, List<AbstractNodeDto>>();
		for(Entry<AbstractNodeDto,  List<AbstractNodeDto>> entry : childrenMap.entrySet()){
			AbstractNodeDto parent = registrar.registerDto(entry.getKey());
			List<AbstractNodeDto> children = registrar.registerDtos(entry.getValue());
			
			newChildrenMap.put(parent, children);
		}
		
		/* exchange maps */
		childrenMap = newChildrenMap;
	}
	
	
}
