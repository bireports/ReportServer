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
 
 
package net.datenwerke.security.ext.client.password;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.security.ext.client.crypto.CryptoUIService;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.GXT;

public class PasswordServiceDao extends Dao {
	
	private final CryptoUIService cryptoService;
	private final PasswordRpcServiceAsync rpcService;

	@Inject
	public PasswordServiceDao(
		CryptoUIService cryptoService,
		PasswordRpcServiceAsync passwordService
		) {
		
		/* store objects */
		this.cryptoService = cryptoService;
		this.rpcService = passwordService;
	}
	
	public void changePassword(final String oldPassword, final String newPassword, final AsyncCallback<Void> callback){
		if(GXT.isIE()){
			//do not encrypt passwords
			rpcService.changePassword(oldPassword, newPassword, false, transformAndKeepCallback(callback));
		}else{
			cryptoService.encrypt(newPassword, oldPassword, new RsAsyncCallback<String>(){
				@Override
				public void onSuccess(final String newPasswordCipher) {
					cryptoService.encrypt(oldPassword, oldPassword, new RsAsyncCallback<String>(){
						@Override
						public void onSuccess(String oldPasswordCipher) {
							rpcService.changePassword(oldPasswordCipher, newPasswordCipher, true, transformAndKeepCallback(callback));
						}
					});
				}
			});
		}
	}

	public void changePassword(final String username, final String oldPassword, final String newPassword, final AsyncCallback<Void> callback){
		if(GXT.isIE()){
			//do not encrypt passwords
			rpcService.changePassword(username, oldPassword, newPassword, false, transformAndKeepCallback(callback));
		}else{
			cryptoService.encrypt(username, newPassword, oldPassword, new RsAsyncCallback<String>(){
				@Override
				public void onSuccess(final String newPasswordCipher) {
					cryptoService.encrypt(username, oldPassword, oldPassword, new RsAsyncCallback<String>(){
						@Override
						public void onSuccess(String oldPasswordCipher) {
							rpcService.changePassword(username, oldPasswordCipher, newPasswordCipher, true, transformAndKeepCallback(callback));
						}
					});
				}
			});
		}
	}
}
