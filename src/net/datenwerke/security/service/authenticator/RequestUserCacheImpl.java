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

import java.util.HashMap;
import java.util.Map;

import net.datenwerke.security.service.authenticator.exceptions.AuthenticatorRuntimeException;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;

public class RequestUserCacheImpl implements RequestUserCache {

	private Map<Long, User> userMap = new HashMap<Long, User>();
	
	private final UserManagerService userManagerService;
	
	@Inject
	public RequestUserCacheImpl(
		UserManagerService userManagerService
		){
		
		this.userManagerService = userManagerService;
	}
	
	@Override
	public User getUser(Long id) {
		if(! userMap.containsKey(id)){
			AbstractUserManagerNode userNode = userManagerService.getNodeById(id);
			if(!(userNode instanceof User))
				throw new AuthenticatorRuntimeException("Something went terribly wrong. UserId (" + id + ") does not point at user."); //$NON-NLS-1$ //$NON-NLS-2$

			userMap.put(id, (User) userNode);
			return (User) userNode;	
		}
		return userMap.get(id);
	}

}
