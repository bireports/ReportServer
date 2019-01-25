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
 
 
package net.datenwerke.security.ext.server.utils;


import javax.inject.Provider;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.security.ext.client.password.PasswordRpcService;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.crypto.pbe.PbeService;
import net.datenwerke.security.service.crypto.pbe.encrypt.EncryptionService;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.User;

import org.apache.commons.codec.binary.Base64;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

/**
 * 
 *
 */
@Singleton
public class PasswordRpcServiceImpl extends SecuredRemoteServiceServlet implements PasswordRpcService {

	private static final long serialVersionUID = 6864534096215565334L;

	private final UserManagerService userManager;
	private final PbeService pbeService;

	private final Provider<AuthenticatorService> authServiceProvider;
	
	@Inject
	public PasswordRpcServiceImpl(
			Provider<AuthenticatorService> authServiceProvider,
			UserManagerService userManager,
			PbeService pbeService
			) {
		
		/* store objcets */
		this.authServiceProvider = authServiceProvider;
		this.userManager = userManager;
		this.pbeService = pbeService;
	}

	@Override
	@Transactional(rollbackOn={Exception.class})
	public void changePassword(String oldPasswordB64, String newPasswordB64, boolean encrypted) throws ServerCallFailedException {
		String oldPassword;
		String newPassword;
		
		if(encrypted){
			/* decrypt */
			oldPassword = decrypt(oldPasswordB64);
			newPassword = decrypt(newPasswordB64);
		}else{
			oldPassword = oldPasswordB64;
			newPassword = newPasswordB64;
		}
		
		userManager.changePassword(authServiceProvider.get().getCurrentUser(), oldPassword, newPassword);
	}
	

	@Override
	@SecurityChecked(loginRequired=false)
	@Transactional(rollbackOn={Exception.class})
	public void changePassword(String username, String oldPasswordB64, String newPasswordB64, boolean encrypted) throws ExpectedException {
		User user = userManager.getUserByName(username);
		
		String oldPassword;
		String newPassword;
		
		if(encrypted){
			/* decrypt */
			oldPassword = decrypt(user, oldPasswordB64);
			newPassword = decrypt(user, newPasswordB64);
		}else{
			oldPassword = oldPasswordB64;
			newPassword = newPasswordB64;
		}

		userManager.changePassword(username, oldPassword, newPassword);
	}
	

	private String decrypt(String oldPasswordB64) {
		EncryptionService encService = pbeService.getClientEncryptionService();
		return new String(encService.decrypt(Base64.decodeBase64(oldPasswordB64.getBytes())));
	}
	
	private String decrypt(User user, String oldPasswordB64) {
		EncryptionService encService = pbeService.getClientEncryptionService(user);
		return new String(encService.decrypt(Base64.decodeBase64(oldPasswordB64.getBytes())));
	}

}
