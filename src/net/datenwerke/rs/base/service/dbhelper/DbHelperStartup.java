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
 
 
package net.datenwerke.rs.base.service.dbhelper;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.dbhelper.hookers.FilterExecutorHooker;
import net.datenwerke.rs.base.service.dbhelper.hookers.InnerQueryColumnReplacementHooker;
import net.datenwerke.rs.base.service.dbhelper.hookers.ProvideBaseDatabaseHelpersHookers;
import net.datenwerke.rs.base.service.dbhelper.hooks.DatabaseHelperProviderHook;
import net.datenwerke.rs.base.service.dbhelper.hooks.DbFilterExecutorHook;
import net.datenwerke.rs.base.service.dbhelper.hooks.InnerQueryModificationHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DbHelperStartup {

	@Inject
	public DbHelperStartup(
		HookHandlerService hookHandler,
			
		Provider<FilterExecutorHooker> filterExecutor,
		Provider<InnerQueryColumnReplacementHooker> innerQueryColumnReplacementHooker,
		
		ProvideBaseDatabaseHelpersHookers provideBaseDbHelpers
		){
		
		hookHandler.attachHooker(DbFilterExecutorHook.class, filterExecutor);
		
		hookHandler.attachHooker(DatabaseHelperProviderHook.class, provideBaseDbHelpers);
		hookHandler.attachHooker(InnerQueryModificationHook.class, innerQueryColumnReplacementHooker);
	}
}
