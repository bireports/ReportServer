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
 
 
package net.datenwerke.security.client.usermanager.dto.decorator;

import java.util.Set;

import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.client.usermanager.dto.UserPropertyDto;

/**
 * Dto Decorator for {@link UserDto}
 *
 */
public class UserDtoDec extends UserDto {


	private static final long serialVersionUID = 1L;

	public UserDtoDec() {
	}

	public boolean hasUserProperty(String key){
		if(null == key)
			return false;
		for(UserPropertyDto prop : getProperties())
			if(key.equals(prop.getKey()))
				return true;
		return false;
	}
	
	public String getUserPropertyValue(String key){
		if(null == key)
			return null;
		for(UserPropertyDto prop : getProperties())
			if(key.equals(prop.getKey()))
				return prop.getValue();
		return null;
	}
	
	public void setUserPropertyValue(String key, String value){
		if(null == key)
			return;
		for(UserPropertyDto prop : getProperties())
			if(key.equals(prop.getKey())){
				prop.setValue(value);
				return;
			}
		
		UserPropertyDto prop = new UserPropertyDto();
		prop.setKey(key);
		prop.setValue(value);
		
		Set<UserPropertyDto> properties = getProperties();
		properties.add(prop);
		setProperties(properties);
	}
	
}
