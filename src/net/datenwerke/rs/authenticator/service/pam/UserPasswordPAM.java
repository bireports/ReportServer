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
 
 
package net.datenwerke.rs.authenticator.service.pam;

import javax.persistence.NoResultException;

import net.datenwerke.rs.authenticator.client.login.dto.UserPasswordAuthToken;
import net.datenwerke.rs.authenticator.client.login.pam.UserPasswordClientPAM;
import net.datenwerke.rs.utils.crypto.PasswordHasher;
import net.datenwerke.security.client.login.AuthToken;
import net.datenwerke.security.service.authenticator.AuthenticationResult;
import net.datenwerke.security.service.authenticator.ReportServerPAM;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;

public class UserPasswordPAM implements ReportServerPAM{
	
	private static final String CLIENT_MODULE_NAME = UserPasswordClientPAM.class.getName();
	private final UserManagerService userManagerService;
	private final PasswordHasher passwordHasher;
	
	@Inject
	public UserPasswordPAM(
						UserManagerService userManagerService,
						PasswordHasher passwordHasher
						) {
		this.userManagerService = userManagerService;
		this.passwordHasher = passwordHasher;
	}
	
	
	public AuthenticationResult authenticate(AuthToken[] tokens) {
		for(Object token : tokens){
			if(token instanceof UserPasswordAuthToken){
				UserPasswordAuthToken credentials = (UserPasswordAuthToken) token;

				User u = authenticate(credentials.getUsername(), credentials.getPassword());
				if(null != u){
					return new AuthenticationResult(true, u);
				}else{
					User usr = getUserOrNull(credentials.getUsername());
					return new AuthenticationResult(false, usr);
				}
			}
		}

		return new AuthenticationResult(!isAuthoritative(), null);
	}
	
	
	protected User getUserOrNull(String username){
		try{
			return userManagerService.getUserByName(username);
		}catch(NoResultException ex){
			return null;
		}	
	}
	
	
	public User authenticate(String username, String cleartextPassword){
		User user = getUserOrNull(username);
		
		if(null == user)
			return null;
		
		if(null != user.getPassword() && passwordHasher.validatePassword(user.getPassword(), cleartextPassword)){
			return user;
		}else
			return null;
	}
	
	public String getClientModuleName() {
		return CLIENT_MODULE_NAME;
	}
	
	protected boolean isAuthoritative() {
		return false;
	}

}
