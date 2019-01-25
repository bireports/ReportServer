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
 
 
package net.datenwerke.usermanager.ext.client.properties;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.client.usermanager.dto.UserPropertyDto;
import net.datenwerke.usermanager.ext.client.properties.rpc.UserPropertiesRpcServiceAsync;

public class UserPropertiesDao extends Dao{

	private final UserPropertiesRpcServiceAsync rpcService;
	
	@Inject
	public UserPropertiesDao(
			UserPropertiesRpcServiceAsync rpcService	
		){
	
		/* store objects */
		this.rpcService = rpcService;
	}
	
	public void getPropertyKeys(AsyncCallback<List<String>> callback){
		rpcService.getPropertyKeys(transformAndKeepCallback(callback));
	}

	public void updateProperties(UserDto user, List<UserPropertyDto> addedProperties,
			List<UserPropertyDto> modifiedProperties, List<UserPropertyDto> removedProperties,
			AsyncCallback<UserDto> callback) {
		rpcService.updateProperties(user, addedProperties, modifiedProperties, removedProperties, transformDtoCallback(callback));
	}
}
