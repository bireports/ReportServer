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
 
 
package net.datenwerke.rs.passwordpolicy.service;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.configservice.service.configservice.hooks.ReloadConfigNotificationHook;
import net.datenwerke.rs.passwordpolicy.service.hooker.UserDtoSetStatusPostProcessor;
import net.datenwerke.security.service.authenticator.hooks.PostAuthenticateHook;
import net.datenwerke.security.service.usermanager.hooks.ChangePasswordHook;
import net.datenwerke.security.service.usermanager.hooks.PasswordSetHook;
import net.datenwerke.security.service.usermanager.hooks.UserDtoPostProcessorHook;

import com.google.inject.Inject;

public class PasswordPolicyStartup {
	
	@Inject
	public PasswordPolicyStartup(
		HookHandlerService hookHandlerService, 
		PasswordPolicy passwordPolicy, 
		UserDtoSetStatusPostProcessor setStatusPostProcessor,
		BsiPasswordPolicyService passwordPolicyService
		) {
		
		hookHandlerService.attachHooker(PostAuthenticateHook.class, passwordPolicy.getPostAuthenticateHooker());
		hookHandlerService.attachHooker(ChangePasswordHook.class, passwordPolicy.getChangePasswordHooker());
		hookHandlerService.attachHooker(PasswordSetHook.class, passwordPolicy.getPasswordSetHooker());
		
		hookHandlerService.attachHooker(UserDtoPostProcessorHook.class, setStatusPostProcessor);
		
		if(passwordPolicyService instanceof ReloadConfigNotificationHook)
			hookHandlerService.attachHooker(ReloadConfigNotificationHook.class, (ReloadConfigNotificationHook)passwordPolicyService);
	}

}
