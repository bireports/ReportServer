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
 
 
package net.datenwerke.rs.passwordpolicy.client.activateuser.rpc;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.security.client.usermanager.dto.UserDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class ActivateUserServiceDao extends Dao{
	
	private ActivateUserRpcServiceAsync rpcService;
	
	@Inject
	public ActivateUserServiceDao(ActivateUserRpcServiceAsync rpcService) {
		this.rpcService = rpcService;
	}
	
	public void activateAccount(UserDto user, boolean force, AsyncCallback<Void> callback){
		rpcService.activateAccount(user, force, transformAndKeepCallback(callback));
	}


}
