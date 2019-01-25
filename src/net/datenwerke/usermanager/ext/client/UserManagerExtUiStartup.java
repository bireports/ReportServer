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
 
 
package net.datenwerke.usermanager.ext.client;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.eximport.client.eximport.im.hooks.ImporterConfiguratorHook;
import net.datenwerke.rs.userprofile.client.userprofile.hooks.UserProfileCardProviderHook;
import net.datenwerke.usermanager.ext.client.eximport.im.hookers.UserManagerUIImporterHooker;
import net.datenwerke.usermanager.ext.client.hookers.UserProfileUserDataHooker;

import com.google.inject.Inject;


public class UserManagerExtUiStartup {

	@Inject
	public UserManagerExtUiStartup(
		final HookHandlerService hookHandler,
		final UserManagerUIImporterHooker importerHooker,
		final UserProfileUserDataHooker userProfileDataHooker
		){
		
		/* attach ex/importer */
		hookHandler.attachHooker(ImporterConfiguratorHook.class, importerHooker);
		
		/* attach user profile cards */
		hookHandler.attachHooker(UserProfileCardProviderHook.class, userProfileDataHooker, HookHandlerService.PRIORITY_HIGH);
	}
}
