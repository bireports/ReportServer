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
 
 
package net.datenwerke.security.ext.client.crypto;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.security.ext.client.crypto.rpc.CryptoRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class CryptoDao extends Dao {

	private final CryptoRpcServiceAsync rpcService;

	@Inject
	public CryptoDao(CryptoRpcServiceAsync rpcService) {
		super();
		this.rpcService = rpcService;
	}
	
	public void getHmacPassphrase(AsyncCallback<String> callback){
		rpcService.getHmacPassphrase(transformAndKeepCallback(callback));
	}
	
	public void getSalt(AsyncCallback<String> callback){
		rpcService.getSalt(transformAndKeepCallback(callback));
	}
	
	public void getKeyLength(AsyncCallback<Integer> callback){
		rpcService.getKeyLength(transformAndKeepCallback(callback));
	}
	
	public void getUserSalt(String username, AsyncCallback<String> callback){
		rpcService.getUserSalt(username, transformAndKeepCallback(callback));
	}
	
	public void getUserSalt(AsyncCallback<String> callback){
		rpcService.getUserSalt(transformAndKeepCallback(callback));
	}
}
