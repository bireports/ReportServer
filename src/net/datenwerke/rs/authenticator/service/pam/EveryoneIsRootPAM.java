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

import net.datenwerke.security.client.login.AuthToken;
import net.datenwerke.security.service.authenticator.AuthenticationResult;
import net.datenwerke.security.service.authenticator.ReportServerPAM;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.User;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class EveryoneIsRootPAM implements ReportServerPAM{

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private final UserManagerService userManagerService;

	@Inject
	public EveryoneIsRootPAM(
		UserManagerService userManagerService
		) {
		this.userManagerService = userManagerService;
	}
	
	@Override
	public AuthenticationResult authenticate(AuthToken[] tokens) {
		logger.warn("Login somebody as a super user without checking credentials!");
		
		User root = userManagerService.getUserByName("root");
		if(null != root && root.isSuperUser())
			return new AuthenticationResult(true, root);
		
		Collection<User> allUsers = userManagerService.getAllUsers();
		for(User u : userManagerService.getAllUsers()){
			if(u.isSuperUser())
				return new AuthenticationResult(true, u);
		}
		
		logger.warn("Could not find a super user");
		
		/* login with the first user if everything fails */
		if(! allUsers.isEmpty())
			return new AuthenticationResult(true, allUsers.iterator().next());
		
		logger.warn("Could not even find any user. Starting to panic.");		
		
		/* can't do anything */
		return new AuthenticationResult(true, null);
	}

	@Override
	public String getClientModuleName() {
		return null;
	}

}
