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
 
 
package net.datenwerke.rs.passwordpolicy.client.accountinhibition;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.passwordpolicy.client.accountinhibition.dto.InhibitionState;
import net.datenwerke.rs.passwordpolicy.client.accountinhibition.rpc.AccountInhibitionRpcServiceAsync;
import net.datenwerke.security.client.usermanager.dto.UserDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class AccountInhibitionServiceDao extends Dao {
	
	private final AccountInhibitionRpcServiceAsync rpcService;

	@Inject
	public AccountInhibitionServiceDao(
			AccountInhibitionRpcServiceAsync accountInhibitionRpcServiceAsync
			) {
		super();
		rpcService = accountInhibitionRpcServiceAsync;
	}
	
	public void getInhibitionState(UserDto user, AsyncCallback<InhibitionState> callback){
		rpcService.getInhibitionState(user, transformAndKeepCallback(callback));
	}

	public void applyAccountInhibitionConfiguration(AccountInhibitionConfiguration accountInhibitionConfiguration, AsyncCallback<Void> callback) {
		rpcService.applyAccountInhibitionConfiguration(accountInhibitionConfiguration, transformAndKeepCallback(callback));
	}

	public void getAccountInhibitionConfiguration(UserDto user, AsyncCallback<AccountInhibitionConfiguration> callback){
		rpcService.getAccountInhibitionConfiguration(user, transformAndKeepCallback(callback));
	}
}
