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
 
 
package net.datenwerke.rs.authenticator.client.login;

import java.util.Set;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.authenticator.client.login.rpc.LoginHandlerAsync;
import net.datenwerke.security.client.login.AuthToken;
import net.datenwerke.security.client.login.AuthenticateResultDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class LoginDao extends Dao {

	private final LoginHandlerAsync rpcService;

	@Inject
	public LoginDao(LoginHandlerAsync rpcService) {
		super();
		this.rpcService = rpcService;
	}
	
	public void isAuthenticated(AsyncCallback<UserDto> callback){
		rpcService.isAuthenticated(transformDtoCallback(callback));
	}

	public void logoff(AsyncCallback<String> callback){
		rpcService.logoff(transformAndKeepCallback(callback));
	}

	public void authenticate(AuthToken[] tokens, AsyncCallback<AuthenticateResultDto> callback){
		rpcService.authenticate(tokens, transformDtoCallback(callback));
	}

	public void getRequiredClientModules(AsyncCallback<Set<String>> callback){
		rpcService.getRequiredClientModules(transformAndKeepCallback(callback));
	}
	
}
