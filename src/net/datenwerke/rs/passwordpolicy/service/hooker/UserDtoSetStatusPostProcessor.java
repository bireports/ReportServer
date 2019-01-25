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
 
 
package net.datenwerke.rs.passwordpolicy.service.hooker;

import java.util.Date;

import net.datenwerke.rs.passwordpolicy.service.BsiPasswordPolicy;
import net.datenwerke.rs.passwordpolicy.service.BsiPasswordPolicyService;
import net.datenwerke.rs.passwordpolicy.service.BsiPasswordPolicyUserMetadata;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.security.service.usermanager.hooks.UserDtoPostProcessorHook;

import com.google.inject.Inject;

public class UserDtoSetStatusPostProcessor implements UserDtoPostProcessorHook {

	private final BsiPasswordPolicyService bsiPasswordPolicyService;
	
	@Inject
	public UserDtoSetStatusPostProcessor(BsiPasswordPolicyService bsiPasswordPolicy) {
		this.bsiPasswordPolicyService = bsiPasswordPolicy;
	}

	@Override
	public void adaptDto(User user, UserDto userDto) {
		if(! bsiPasswordPolicyService.isActive() || ! userDto.isActive())
			return;

		BsiPasswordPolicyUserMetadata data = bsiPasswordPolicyService.getUserMetadata(user);
		
		if(null != data.getAccountInhibited() && data.getAccountInhibited()) 
			userDto.setActive(false);
		else  if(null != data.getAccountExpirationDate() && data.getAccountExpirationDate().before(new Date()))
			userDto.setActive(false);
		else {
			BsiPasswordPolicy policy = bsiPasswordPolicyService.getPolicy();
			
			if(data.getFailedLoginCount() > policy.getAccountLockoutThreshold())
				userDto.setActive(false);
		}
	}

}
