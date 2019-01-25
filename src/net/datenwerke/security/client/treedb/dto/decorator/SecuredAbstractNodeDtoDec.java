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
 
 
package net.datenwerke.security.client.treedb.dto.decorator;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.datenwerke.security.client.security.dto.RightDto;
import net.datenwerke.security.client.treedb.dto.SecuredAbstractNodeDto;

/**
 * Dto Decorator for {@link SecuredAbstractNodeDto}
 *
 */
abstract public class SecuredAbstractNodeDtoDec extends SecuredAbstractNodeDto {


	public static final String PROPERTY_ACCCESS_RIGHTS = "SECURED_ABSTRACT_NODE_ACCESS_RIGHTS"; //$NON-NLS-1$


	private static final long serialVersionUID = 1L;
	
	public SecuredAbstractNodeDtoDec() {
		super();
	}
	
	public void addAvailableRight(RightDto right) {
		Set<RightDto> availableRights = getAvailableAccessRights();
		if(null == availableRights)
			availableRights = new HashSet<RightDto>();
			
		availableRights.add(right);
		setAvailableAccessRights(availableRights);
	}
	
	public boolean isAccessRightsLoaded(){
		if(! isDtoProxy())
			return Boolean.TRUE.equals(this.availableAccessRightsSet);
		
		return dtoManager.unproxy(this).isAccessRightsLoaded();
	}
	
	public boolean hasAccessRight(Class<? extends RightDto> right){
		Collection<RightDto> availableRights = getAvailableAccessRights();
		if(null == availableRights)
			return false;
		
		for(RightDto r : availableRights)
			if(r.getClass().equals(right))
				return true;
		
		return false;
	}
	
	public boolean hasInheritedAccessRight(Class<? extends RightDto> right){
		Collection<RightDto> availableRights = getAvailableInheritedAccessRights();
		if(null == availableRights)
			return false;
		
		for(RightDto r : availableRights)
			if(r.getClass().equals(right))
				return true;
		
		return false;
	}


}
