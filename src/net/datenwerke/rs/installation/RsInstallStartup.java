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
 
 
package net.datenwerke.rs.installation;

import javax.inject.Inject;

import net.datenwerke.gf.service.lateinit.LateInitHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;

import com.google.inject.Provider;

public class RsInstallStartup {

	@Inject
	public RsInstallStartup(
		HookHandlerService hookHandler,
		final Provider<ReportServerInstallationServiceImpl> installer, 
		Provider<PkgCommand> isntPkgCommandProvider
		) {

		hookHandler.attachHooker(TerminalCommandHook.class, isntPkgCommandProvider);
		
		hookHandler.attachHooker(LateInitHook.class, new LateInitHook() {
			@Override
			public void initialize() {
				installer.get().install();
			}
		}, HookHandlerService.PRIORITY_HIGH -10 /* HIGHER */);
	}
}
