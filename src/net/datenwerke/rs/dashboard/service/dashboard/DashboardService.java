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
 
 
package net.datenwerke.rs.dashboard.service.dashboard;

import java.util.Collection;

import net.datenwerke.rs.dashboard.service.dashboard.entities.AbstractDashboardManagerNode;
import net.datenwerke.rs.dashboard.service.dashboard.entities.Dadget;
import net.datenwerke.rs.dashboard.service.dashboard.entities.Dashboard;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DashboardContainer;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DashboardNode;
import net.datenwerke.rs.dashboard.service.dashboard.entities.UserDashboard;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.treedb.service.treedb.TreeDBManager;

public interface DashboardService extends TreeDBManager<AbstractDashboardManagerNode> {
	
	public static final String USER_PROPERTY_PRIMARY_DASHBOARD = "dashboard:primaryDashboard";

	void removeDashboardFor(User user);
	
	Dashboard getExplicitPrimaryDashboard(User user);

	UserDashboard getUserDashboard(User user);

	DashboardContainer getDashboardFor(User user);

	DashboardContainer merge(DashboardContainer dashboardContainer);

	Dashboard merge(Dashboard dashboard);

	void remove(Dashboard dashboard);

	void persist(Dadget dadget);

	void persist(Dashboard dashboard);

	void remove(Dadget dadget);

	Dadget merge(Dadget dadget);

	DashboardContainer getDashboardContainerFor(Dashboard db);

	UserDashboard getUserDashboardFor(DashboardContainer container);

	Dashboard getDashboardById(Long id);

	Dashboard getDashboardFor(Dadget dadget);

	DashboardNode getNodeFor(Dashboard dashboard);
	
	boolean isOwner(User user, Dashboard dashboard);

	Collection<Dashboard> getAllDashboards();

	Collection<Dashboard> getDashboards();

	void setPrimaryDashboard(Dashboard dashboard);

}
