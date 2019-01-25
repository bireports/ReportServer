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
 
 
package net.datenwerke.rs.dashboard.server.dashboard;

import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.AbstractDatasourceManagerNode;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.dashboard.client.dashboard.rpc.DashboardTreeLoader;
import net.datenwerke.rs.dashboard.client.dashboard.rpc.DashboardTreeManager;
import net.datenwerke.rs.dashboard.service.dashboard.DashboardManagerService;
import net.datenwerke.rs.dashboard.service.dashboard.entities.AbstractDashboardManagerNode;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DadgetNode;
import net.datenwerke.rs.dashboard.service.dashboard.entities.Dashboard;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DashboardNode;
import net.datenwerke.rs.utils.entitycloner.EntityClonerService;
import net.datenwerke.security.server.TreeDBManagerTreeHandler;
import net.datenwerke.security.service.security.SecurityService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * 
 *
 */
@Singleton
public class DashboardTreeRpcServiceImpl extends TreeDBManagerTreeHandler<AbstractDashboardManagerNode> 
		implements 
			DashboardTreeLoader, 
			DashboardTreeManager
			 {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6246061438291322619L;

	@Inject
	public DashboardTreeRpcServiceImpl(
		DtoService dtoService,
		DashboardManagerService manager,
		SecurityService securityService,
		EntityClonerService entityClonerService
		) {
		super(manager, dtoService, securityService, entityClonerService);
	}

	@Override
	protected void doSetInitialProperties(AbstractDashboardManagerNode inserted) {
		if(inserted instanceof DashboardNode){
			DashboardNode dashboardNode = (DashboardNode) inserted;
			dashboardNode.setDashboard(new Dashboard());
		}
	}

	@Override
	protected boolean allowDuplicateNode(AbstractDashboardManagerNode realNode) {
		return true;
	}
	
	@Override
	protected void nodeCloned(AbstractDashboardManagerNode clonedNode) {
		if(! (clonedNode instanceof AbstractDashboardManagerNode))
			throw new IllegalArgumentException();
		
		if (clonedNode instanceof DadgetNode) {
			DadgetNode dadgetNode = (DadgetNode) clonedNode;
			dadgetNode.setName(dadgetNode.getName() == null ? "copy" : dadgetNode.getName() + " (copy)");
		} else if (clonedNode instanceof DashboardNode) {
			DashboardNode dashboardNode = (DashboardNode) clonedNode;
			dashboardNode.setName(dashboardNode.getName() == null ? "copy" : dashboardNode.getName() + " (copy)");
		}
		
	}
}
	