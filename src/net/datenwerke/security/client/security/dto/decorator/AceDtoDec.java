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
 
 
package net.datenwerke.security.client.security.dto.decorator;

import java.util.HashSet;
import java.util.Set;

import net.datenwerke.security.client.security.dto.AceAccessMapDto;
import net.datenwerke.security.client.security.dto.AceDto;
import net.datenwerke.security.service.security.entities.Ace;

/**
 * Dto for {@link Ace}
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
public class AceDtoDec extends AceDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3230134883245155212L;

	public AceDtoDec() {
		super();
	}

	public void addAccessMap(AceAccessMapDto accessMap){
		if(null == getAccessMaps()) 
			setAccessMaps(new HashSet<AceAccessMapDto>()); 
		
		Set<AceAccessMapDto> maps = getAccessMaps();
		
		/* try to find old map for that secury */
		String securee = accessMap.getSecuree();
		if(null == securee)
			throw new IllegalArgumentException("AccessMap needs to have a securee.");
		AceAccessMapDto oldMap = getAccessMap(securee);
		if(null != oldMap)
			maps.remove(oldMap);
		
		maps.add(accessMap);
		
		/* set property */
		setAccessMaps(maps);
	}
	
	public AceAccessMapDto getAccessMap(String secureeId){
		if(null == secureeId)
			return null;
		
		for(AceAccessMapDto map : getAccessMaps())
			if(secureeId.equals(map.getSecuree()))
				return map;
		
		return null;
	}
	
}
