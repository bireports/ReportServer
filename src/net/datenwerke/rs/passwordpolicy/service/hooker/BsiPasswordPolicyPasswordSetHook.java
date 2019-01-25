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
 
 
package net.datenwerke.rs.passwordpolicy.service.hooker;

import net.datenwerke.rs.passwordpolicy.service.BsiPasswordPolicyService;
import net.datenwerke.rs.passwordpolicy.service.BsiPasswordPolicyUserMetadata;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.security.service.usermanager.hooks.PasswordSetHook;

import com.google.inject.Inject;

public class BsiPasswordPolicyPasswordSetHook implements PasswordSetHook{
	
	private final UserManagerService userManagerService;
	private final BsiPasswordPolicyService bsiPasswordPolicyService;

	@Inject
	public BsiPasswordPolicyPasswordSetHook(
			UserManagerService userManagerService, 
			BsiPasswordPolicyService bsiPasswordPolicyService) {
		this.userManagerService = userManagerService;
		this.bsiPasswordPolicyService = bsiPasswordPolicyService;
	}

	@Override
	public void passwordWasSet(User user) {
		if(!bsiPasswordPolicyService.isActive())
			return;
		
		BsiPasswordPolicyUserMetadata userMetadata = bsiPasswordPolicyService.getUserMetadata(user);
		
		userMetadata.setFailedLoginCount(0);
		userMetadata.setLastChangedPassword(null);
		userMetadata.enforcePasswordChangeOnNextLogin();

		bsiPasswordPolicyService.updateUserMetadata(user, userMetadata);
		
		userManagerService.merge(user);
	}

}
