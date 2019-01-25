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
 
 
package net.datenwerke.rs.dashboard.service.dashboard.eventhandler;

import java.util.List;

import net.datenwerke.rs.dashboard.service.dashboard.DadgetService;
import net.datenwerke.rs.dashboard.service.dashboard.DashboardService;
import net.datenwerke.rs.dashboard.service.dashboard.dagets.LibraryDadget;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DadgetNode;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.security.service.eventlogger.jpa.ForceRemoveEntityEvent;

import com.google.inject.Inject;

public class HandleDadgetNodeForceRemoveEventHandler implements
		EventHandler<ForceRemoveEntityEvent> {

	private final DadgetService dadgetService;
	private final DashboardService dashboardService;

	@Inject
	public HandleDadgetNodeForceRemoveEventHandler(DadgetService dadgetService, DashboardService dashboardService) {
		this.dadgetService = dadgetService;
		this.dashboardService = dashboardService;
	}

	@Override
	public void handle(ForceRemoveEntityEvent event) {
		DadgetNode node = (DadgetNode) event.getObject();
		
		List<LibraryDadget> libraryDadgets = dadgetService.getLibraryDadgetsWith(node);
		
		if(null != libraryDadgets && ! libraryDadgets.isEmpty()){
			for(LibraryDadget dadget : libraryDadgets){
				dadget.setDadgetNode(null);
				dashboardService.merge(dadget);
			}
		}
	}

}
