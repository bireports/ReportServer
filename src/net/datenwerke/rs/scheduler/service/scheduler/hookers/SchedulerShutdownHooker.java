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
 
 
package net.datenwerke.rs.scheduler.service.scheduler.hookers;

import javax.servlet.ServletContextEvent;

import net.datenwerke.gf.service.lifecycle.hooks.ContextHook;
import net.datenwerke.scheduler.service.scheduler.SchedulerService;

import com.google.inject.Inject;

public class SchedulerShutdownHooker implements ContextHook {

	private final SchedulerService schedulerService;
	
	@Inject
	public SchedulerShutdownHooker(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		schedulerService.shutdown();
		schedulerService.shutdownWatchdog();
	}

}
