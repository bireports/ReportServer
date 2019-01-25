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
 
 
package net.datenwerke.usermanager.ext.service.hookers;

import java.util.Locale;

import net.datenwerke.rs.utils.localization.hooks.LocaleChangedNotificationHook;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.usermanager.UserManagerModule;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.UserPropertiesService;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UpdateUserLocalHooker implements LocaleChangedNotificationHook {

	private final Provider<AuthenticatorService> authenticationServiceProvider;
	private final UserPropertiesService userPropertiesService;
	private final UserManagerService userManagerService;
	
	@Inject
	public UpdateUserLocalHooker(
			Provider<AuthenticatorService> authenticationServiceProvider,
			UserPropertiesService userPropertiesService,
			UserManagerService userManagerService) {
		super();
		this.authenticationServiceProvider = authenticationServiceProvider;
		this.userPropertiesService = userPropertiesService;
		this.userManagerService = userManagerService;
	}



	@Override
	public void localeChanged(Locale locale) {
		try{
			AuthenticatorService authService = authenticationServiceProvider.get();
			User user = authService.getCurrentUser();
			if(null != user){
				userPropertiesService.setPropertyValue(user, UserManagerModule.USER_PROPERTY_USER_LOCALE, null != locale ? locale.toLanguageTag() : "");
				userManagerService.merge(user);
			}
		} catch(Exception e){}
	}

}
