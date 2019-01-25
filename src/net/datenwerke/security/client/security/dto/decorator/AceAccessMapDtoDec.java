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
 
 
package net.datenwerke.security.client.security.dto.decorator;

import net.datenwerke.security.client.security.dto.AceAccessMapDto;
import net.datenwerke.security.client.security.dto.RightDto;
import net.datenwerke.security.service.security.entities.AceAccessMap;

/**
 * Dto for {@link AceAccessMap}
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
public class AceAccessMapDtoDec extends AceAccessMapDto {


	private static final long serialVersionUID = 1L;

	public AceAccessMapDtoDec() {
		super();
	}

	public void addAccessRight(long right){
		Long access = getAccess();
		if(null == access)
			access = 0L;
		setAccess(access | right); 
	}
	
	public boolean hasAccessRight(long right){
		Long access = getAccess();
		if(null == access)
			return false;
		return (access & right) == right;
	}
	
	public boolean hasAccessRight(RightDto right){
		return hasAccessRight(right.getBitField());
	}

	public void clearRights() {
		setAccess(0L);
	}

}
