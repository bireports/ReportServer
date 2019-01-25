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
 
 
package net.datenwerke.rs.dashboard.service.dashboard.eventhandler;

import net.datenwerke.rs.dashboard.service.dashboard.DashboardService;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;

public class HandleUserRemoveEventHandler implements
		EventHandler<RemoveEntityEvent> {

	private final DashboardService dashboardService;

	@Inject
	public HandleUserRemoveEventHandler(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@Override
	public void handle(RemoveEntityEvent event) {
		User user = (User) event.getObject();
		
		dashboardService.removeDashboardFor(user);
	}

}
