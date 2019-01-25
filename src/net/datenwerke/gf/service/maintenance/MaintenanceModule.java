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
 
 
package net.datenwerke.gf.service.maintenance;

import net.datenwerke.gf.service.maintenance.hooks.MaintenanceTask;

import com.google.inject.AbstractModule;
import com.google.inject.Scope;
import com.google.inject.Singleton;

/**
 * A module providing a maintenance thread which periodically runs
 * a set of maintenance tasks ({@link MaintenanceTask}).
 * 
 * The configuration is performed in file "main/main.cf".
 * 
 * @see MaintenanceTask
 *
 */
public class MaintenanceModule extends AbstractModule {

	public static final String CONFIG_FILE = "main/main.cf";

	@Override
	protected void configure() {
		bind(MaintenanceService.class).to(MaintenanceServiceImpl.class).in(Singleton.class);
		bind(MaintenanceStartup.class).asEagerSingleton();
	}
	
}
