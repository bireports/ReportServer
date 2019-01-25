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
 
 
package net.datenwerke.rs.core.service;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gf.service.lateinit.LateInitHook;
import net.datenwerke.gf.service.maintenance.hooks.MaintenanceTask;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.configservice.service.configservice.hooks.ReloadConfigNotificationHook;
import net.datenwerke.rs.core.service.login.BlockSuperUserOnLoginHooker;
import net.datenwerke.rs.core.service.login.CheckBlockedUserOnLoginHooker;
import net.datenwerke.rs.core.service.login.UserDtoSetStatusPostProcessor;
import net.datenwerke.rs.core.service.maintenance.BlockUserMaintenanceTask;
import net.datenwerke.rs.core.service.pdf.RegisterPdfFontsOnConfigReload;
import net.datenwerke.rs.core.service.pdf.RegisterPdfFontsOnStartup;
import net.datenwerke.security.service.authenticator.hooks.PostAuthenticateHook;
import net.datenwerke.security.service.usermanager.hooks.UserDtoPostProcessorHook;

public class RsCoreStartup {

	@Inject
	public RsCoreStartup(
		HookHandlerService hookHandler,
		
		Provider<UserDtoSetStatusPostProcessor> userSetStatusDtoPostProcessor,
		
		Provider<CheckBlockedUserOnLoginHooker> checkBlockUserOnLogin,
		Provider<BlockSuperUserOnLoginHooker> blockRootOnLogin,
		
		Provider<BlockUserMaintenanceTask> blockTask,
		
		RegisterPdfFontsOnConfigReload pdfFontOnReloadLoader,
		RegisterPdfFontsOnStartup pdfFontOnStartupLoader
		){
		
		
		hookHandler.attachHooker(UserDtoPostProcessorHook.class, userSetStatusDtoPostProcessor);
		
		hookHandler.attachHooker(PostAuthenticateHook.class, checkBlockUserOnLogin);
		
		hookHandler.attachHooker(PostAuthenticateHook.class, blockRootOnLogin);
		
		hookHandler.attachHooker(MaintenanceTask.class, blockTask);
		
		hookHandler.attachHooker(ReloadConfigNotificationHook.class, pdfFontOnReloadLoader);
		hookHandler.attachHooker(LateInitHook.class, pdfFontOnStartupLoader);
		
	}
}
