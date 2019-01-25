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
 
 
package net.datenwerke.security.service.authenticator;

import java.util.Map;
import java.util.Set;

import net.datenwerke.security.client.login.AuthToken;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.AbstractModule;

public class AuthenticateEverythingModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(AuthenticatorService.class).toInstance(new AuthenticatorService() {
			
			@Override
			public void su(User user) {
				
			}
			
			@Override
			public void logoff() {
			}
			
			@Override
			public boolean isAuthenticated() {
				return true;
			}
			
			@Override
			public Set<String> getRequiredClientModules() {
				return null;
			}
			
			@Override
			public Map<Long, Long> getLastRequests() {
				return null;
			}
			
			@Override
			public User getCurrentUser() {
				return null;
			}
			
			@Override
			public AuthenticationResult authenticate(AuthToken[] tokens) {
				return null;
			}

			@Override
			public void setAuthenticated(Long userId) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void logoffUserInThread() {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void setAuthenticatedInThread(Long userId) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

}
