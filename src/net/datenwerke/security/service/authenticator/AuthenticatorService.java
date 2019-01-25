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
 
 
package net.datenwerke.security.service.authenticator;

import java.util.Map;
import java.util.Set;

import net.datenwerke.security.client.login.AuthToken;
import net.datenwerke.security.service.usermanager.entities.User;


/**
 * 
 *
 */
public interface AuthenticatorService {


	public AuthenticationResult authenticate(AuthToken[] tokens);
	
	/**
	 * Returns if a user was previously successfully authenticated
	 * using the authenticate method()
	 * 
	 * @return
	 */
	public boolean isAuthenticated();
	
	
	/**
	 * Returns the authenticated user
	 * 
	 * @throws RuntimeException if no user was authenticated
	 * @return
	 */
	public User getCurrentUser();
	
	
	public Set<String> getRequiredClientModules();

	public void logoff();

	void su(User user);

	Map<Long, Long> getLastRequests();

	void setAuthenticated(Long userId);

	void setAuthenticatedInThread(Long userId);

	void logoffUserInThread();

}
