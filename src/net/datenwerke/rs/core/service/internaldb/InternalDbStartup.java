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
 
 
package net.datenwerke.rs.core.service.internaldb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import net.datenwerke.gf.service.lateinit.LateInitHook;
import net.datenwerke.gf.service.lifecycle.hooks.ContextHook;
import net.datenwerke.gf.service.maintenance.hooks.MaintenanceTask;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.internaldb.hookers.ClearInternalDbOnShutdown;
import net.datenwerke.rs.core.service.internaldb.hookers.TempTableMaintenanceTask;
import net.datenwerke.rs.utils.config.ConfigService;

public class InternalDbStartup {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private static final String CONFIG_FILE = "datasources/internaldb.cf";
	protected static final String DROP_TMPTABLES = "internaldb.droponstartup";

	
	@Inject
	public InternalDbStartup(
		HookHandlerService hookHandler,
		ClearInternalDbOnShutdown shutdownHooker,
		final TempTableService tempTableService,
		final ConfigService configService,
		TempTableMaintenanceTask tempTableMaintencanceTask

		) {

		
		hookHandler.attachHooker(ContextHook.class, shutdownHooker);
		
		hookHandler.attachHooker(MaintenanceTask.class, tempTableMaintencanceTask);
		
		hookHandler.attachHooker(LateInitHook.class, new LateInitHook() {
			@Override
			public void initialize() {
				try{
					boolean dropTmpTables = configService.getConfigFailsafe(CONFIG_FILE).getBoolean(DROP_TMPTABLES, true);
					if(dropTmpTables)
						tempTableService.dropRSTMPtables();
					
				} catch(Exception e){
					logger.error( "Error in Internal DB Startup", e);
				}
			}
		});
	}
}
