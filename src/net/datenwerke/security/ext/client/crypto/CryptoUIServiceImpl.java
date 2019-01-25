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
 
 
package net.datenwerke.security.ext.client.crypto;

import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class CryptoUIServiceImpl implements CryptoUIService {

	private final CryptoDao cryptoDao;
	
	private String hmacPassphrase = null;
	private String salt = null;
	private int keylength;
	
	private boolean init = false;
	
	
	@Inject
	public CryptoUIServiceImpl(
		CryptoDao cryptoDao	
		){
		
		/* store objects */
		this.cryptoDao = cryptoDao;
	}
	
	private void init(final RsAsyncCallback<Void> callback){
		if(init){
			callback.onSuccess(null);
			return;
		}
		
		cryptoDao.getHmacPassphrase(new RsAsyncCallback<String>(){
			public void onSuccess(String result) {
				hmacPassphrase = result;
				
				cryptoDao.getSalt(new RsAsyncCallback<String>(){
					@Override
					public void onSuccess(String result) {
						salt = result;
						
						cryptoDao.getKeyLength(new RsAsyncCallback<Integer>(){
							@Override
							public void onSuccess(Integer result) {
								keylength = result;
								
								init = true;
								callback.onSuccess(null);
							}
						});
					}
				});
			}
		});
	}
	
	@Override
	public void getHmacPassphrase(final AsyncCallback<String> callback) {
		init(new RsAsyncCallback<Void>(){
			@Override
			public void onSuccess(Void result) {
				callback.onSuccess(hmacPassphrase);
			}
		});
	}

	@Override
	public void getUserPassphrase(final String password, final AsyncCallback<String> callback) {
		init(new RsAsyncCallback<Void>(){
			@Override
			public void onSuccess(Void result) {
				
				cryptoDao.getUserSalt(new RsAsyncCallback<String>() {
					@Override
					public void onSuccess(String salt) {
						String hmac = salt + CryptoJsWrapper.hmac(password, hmacPassphrase + salt);
						callback.onSuccess(hmac);
					}
				});
				
			}
		});
	}
	
	
	private void getUserPassphrase(final String username, final String password, final AsyncCallback<String> callback) {
		init(new RsAsyncCallback<Void>(){
			@Override
			public void onSuccess(Void result) {
				
				cryptoDao.getUserSalt(username, new RsAsyncCallback<String>() {
					@Override
					public void onSuccess(String salt) {
						String hmac = salt + CryptoJsWrapper.hmac(password, hmacPassphrase + salt);
						callback.onSuccess(hmac);
					}
				});
				
			}
		});
	}


	@Override
	public void encrypt(final String data, String password,	final AsyncCallback<String> callback) {
		getUserPassphrase(password, new RsAsyncCallback<String>(){
			@Override
			public void onSuccess(String passphrase) {
				String ciphertext = CryptoJsWrapper.encryptAES(data, passphrase, salt, keylength, CryptoUIModule.NR_OF_ITERATIONS);
				callback.onSuccess(ciphertext);
			}
		});
	}
	
	@Override
	public void encrypt(String username, final String data, String password, final AsyncCallback<String> callback) {
		getUserPassphrase(username, password, new RsAsyncCallback<String>(){
			@Override
			public void onSuccess(String passphrase) {
				String ciphertext = CryptoJsWrapper.encryptAES(data, passphrase, salt, keylength, CryptoUIModule.NR_OF_ITERATIONS);
				callback.onSuccess(ciphertext);
			}
		});
	}
	
	

}
