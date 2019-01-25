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
 
 
package net.datenwerke.rs.search.service.search;

import net.datenwerke.gf.service.lateinit.LateInitHook;
import net.datenwerke.gf.service.maintenance.hooks.MaintenanceTask;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.search.service.search.hooks.SearchProvider;
import net.datenwerke.rs.search.service.search.provider.EntitySearchProvider;
import net.datenwerke.rs.search.service.search.terminal.commands.LocateCommand;
import net.datenwerke.rs.search.service.search.terminal.commands.LocateUpdateDBCommand;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SearchStartup {

	
	
	@Inject
	public SearchStartup(
			HookHandlerService hookHandlerService,
			LocateCommand locateCommand,
			LocateUpdateDBCommand locateUpdateDBCommand,
			final SearchService searchService,
			Provider<EntitySearchProvider> entitySearchProvider
	) {
		
		hookHandlerService.attachHooker(TerminalCommandHook.class, locateCommand);
		hookHandlerService.attachHooker(TerminalCommandHook.class, locateUpdateDBCommand);
		hookHandlerService.attachHooker(SearchProvider.class, entitySearchProvider);
		
		hookHandlerService.attachHooker(LateInitHook.class, new LateInitHook() {
			@Override
			public void initialize() {
				searchService.rebuildIndex();
			}
		});
	}
}
