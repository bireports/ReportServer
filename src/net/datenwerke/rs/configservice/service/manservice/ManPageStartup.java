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
 
 
package net.datenwerke.rs.configservice.service.manservice;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.configservice.service.manservice.terminal.DocCommand;
import net.datenwerke.rs.configservice.service.manservice.terminal.DocReloadCommand;
import net.datenwerke.rs.configservice.service.manservice.terminal.DocSubCommandHook;
import net.datenwerke.rs.configservice.service.manservice.terminal.ManCommand;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ManPageStartup {

	@Inject
	public ManPageStartup(
		HookHandlerService hookHandlerService,
		
		Provider<ManCommand> manCommand,
		Provider<DocCommand> docCommand,
		Provider<DocReloadCommand> manReloadCommand
		) {
		
		hookHandlerService.attachHooker(TerminalCommandHook.class, manCommand);
		hookHandlerService.attachHooker(TerminalCommandHook.class, docCommand);
		hookHandlerService.attachHooker(DocSubCommandHook.class, manReloadCommand);
	}
}
