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
 
 
package net.datenwerke.security.ext.client.usermanager;

import java.util.Collection;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownGroup;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUser;
import net.datenwerke.security.ext.client.usermanager.rpc.UserManagerRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.loader.ListLoadResult;

public class UserManagerDao extends Dao {

	private final UserManagerRpcServiceAsync rpcService;

	@Inject
	public UserManagerDao(UserManagerRpcServiceAsync rpcService) {
		super();
		this.rpcService = rpcService;
	}
	
	public void getStrippedDownUsers(AsyncCallback<ListLoadResult<StrippedDownUser>> callback){
		rpcService.getStrippedDownUsers(transformAndKeepCallback(callback));
	}

	public void getStrippedDownGroups(AsyncCallback<ListLoadResult<StrippedDownGroup>> callback){
		rpcService.getStrippedDownGroups(transformAndKeepCallback(callback));
	}
	
	public void changeActiveUserData(UserDto userDto, AsyncCallback<UserDto> callback){
		rpcService.changeActiveUserData(userDto, transformDtoCallback(callback));
	}

	public void getStrippedDownUsers(Collection<Long> ids,
			AsyncCallback<List<StrippedDownUser>> callback){
		rpcService.getStrippedDownUsers(ids, transformAndKeepCallback(callback));
	}
	
	
	
}
