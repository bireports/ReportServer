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
 
 
package net.datenwerke.security.ext.client.usermanager.utils;

import net.datenwerke.gf.client.treedb.icon.IconMapping;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.usermanager.dto.SexDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.resources.client.ImageResource;

/**
 * 
 * <p>Needs static injection</p>
 *
 */
public class UserIconMapping extends IconMapping {

	
	
	public UserIconMapping() {
		super(UserDto.class);
	}

	@Override
	public ImageResource getIcon(AbstractNodeDto node) {
		if(! (node instanceof UserDto))
			throw new IllegalArgumentException("Expected UserDTO"); //$NON-NLS-1$
		UserDto user = (UserDto) node;
		if(! user.isActive())
			return BaseIcon.USER_BLOCKED.toImageResource();
		
		return BaseIcon.USER.toImageResource();
	}
}
