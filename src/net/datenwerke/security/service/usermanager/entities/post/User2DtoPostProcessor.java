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
 
 
package net.datenwerke.security.service.usermanager.entities.post;

import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoPostProcessor;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.security.service.usermanager.hooks.UserDtoPostProcessorHook;

import com.google.inject.Inject;

public class User2DtoPostProcessor implements Poso2DtoPostProcessor<User, UserDto> {

	private final HookHandlerService hookHandler;
	
	@Inject
	public User2DtoPostProcessor(HookHandlerService hookHandler) {
		this.hookHandler = hookHandler;
	}

	@Override
	public void dtoCreated(User user, UserDto userDto) {
		if(null != user.getPassword() && ! "".equals(user.getPassword().trim()))
			userDto.setHasPassword(true);
		
		for(UserDtoPostProcessorHook hooker : hookHandler.getHookers(UserDtoPostProcessorHook.class))
			hooker.adaptDto(user, userDto);
	}

	@Override
	public void dtoInstantiated(User arg0, UserDto arg1) {
		
	}

}
