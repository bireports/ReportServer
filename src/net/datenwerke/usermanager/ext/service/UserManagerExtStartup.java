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
 
 
package net.datenwerke.usermanager.ext.service;

import net.datenwerke.eximport.hooks.ExporterProviderHook;
import net.datenwerke.eximport.hooks.ImporterProviderHook;
import net.datenwerke.gf.service.history.hooks.HistoryUrlBuilderHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.eximport.service.eximport.hooks.ExportAllHook;
import net.datenwerke.rs.eximport.service.eximport.hooks.ImportAllHook;
import net.datenwerke.rs.eximport.service.eximport.im.http.hooks.HttpImportConfigurationProviderHook;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;
import net.datenwerke.rs.utils.localization.hooks.LocaleChangedNotificationHook;
import net.datenwerke.usermanager.ext.service.eximport.HttpUserManagerImportConfigurationHooker;
import net.datenwerke.usermanager.ext.service.eximport.UserManagerExporter;
import net.datenwerke.usermanager.ext.service.eximport.UserManagerImporter;
import net.datenwerke.usermanager.ext.service.eximport.hooker.ExportAllUsersHooker;
import net.datenwerke.usermanager.ext.service.eximport.hooker.ImportAllUsersHooker;
import net.datenwerke.usermanager.ext.service.history.UserManagerHistoryUrlBuilderHooker;
import net.datenwerke.usermanager.ext.service.hookers.UpdateUserLocalHooker;
import net.datenwerke.usermanager.ext.service.hooks.UserModSubCommandHook;
import net.datenwerke.usermanager.ext.service.terminal.commands.SetUserPropertySubCommand;
import net.datenwerke.usermanager.ext.service.terminal.commands.UserModCommand;

import com.google.inject.Inject;
import com.google.inject.Provider;


public class UserManagerExtStartup {

	@Inject
	public UserManagerExtStartup(
		HookHandlerService hookHandler,
		Provider<UserManagerExporter> exporterProvider,
		Provider<UserManagerImporter> importerProvider,
		Provider<HttpUserManagerImportConfigurationHooker> httpImportConfigHookerProvider,
		Provider<ExportAllUsersHooker> exportAllUsers,
		Provider<ImportAllUsersHooker> importAllUsers,
		
		Provider<UpdateUserLocalHooker> updateUserLocaleHooker,
		
		Provider<UserManagerHistoryUrlBuilderHooker> userManagerUrlBuilder,
		
		Provider<UserModCommand> userModCommandProvider,
		Provider<SetUserPropertySubCommand> setUserPropertyCommandProvider
		){
		
		hookHandler.attachHooker(ExporterProviderHook.class, new ExporterProviderHook(exporterProvider));
		hookHandler.attachHooker(ImporterProviderHook.class, new ImporterProviderHook(importerProvider));
		hookHandler.attachHooker(HttpImportConfigurationProviderHook.class, httpImportConfigHookerProvider);
		hookHandler.attachHooker(ExportAllHook.class, exportAllUsers);
		hookHandler.attachHooker(ImportAllHook.class, importAllUsers);
		
		hookHandler.attachHooker(LocaleChangedNotificationHook.class, updateUserLocaleHooker);
		
		hookHandler.attachHooker(TerminalCommandHook.class, userModCommandProvider);
		hookHandler.attachHooker(UserModSubCommandHook.class, setUserPropertyCommandProvider);
		
		hookHandler.attachHooker(HistoryUrlBuilderHook.class, userManagerUrlBuilder);
	}
}
