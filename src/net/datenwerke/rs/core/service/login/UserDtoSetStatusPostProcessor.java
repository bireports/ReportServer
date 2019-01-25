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
 
 
package net.datenwerke.rs.core.service.login;

import net.datenwerke.rs.core.service.reportserver.ReportServerModule;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.security.service.usermanager.entities.UserProperty;
import net.datenwerke.security.service.usermanager.hooks.UserDtoPostProcessorHook;

public class UserDtoSetStatusPostProcessor implements UserDtoPostProcessorHook {


	@Override
	public void adaptDto(User user, UserDto userDto) {
		if(! userDto.isActive())
			return;

		UserProperty property = user.getProperty(ReportServerModule.USER_PROPERTY_ACCOUNT_INHIBITED);
		if(null != property && property.getValueAsBoolean())
			userDto.setActive(false);
	}

}
