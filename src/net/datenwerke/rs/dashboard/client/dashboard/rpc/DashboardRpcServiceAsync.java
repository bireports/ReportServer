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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.rpc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardContainerDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardNodeDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardReferenceDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.FavoriteListDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.ReportDadgetDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;


public interface DashboardRpcServiceAsync {

	void getDashboardForUser(AsyncCallback<DashboardContainerDto> callback);

	void createDashboardForUser(AsyncCallback<DashboardDto> callback);

	void removeDashboard(DashboardDto dashboard,
			AsyncCallback<Void> callback);

	void reloadDashboard(DashboardDto dashboard, AsyncCallback<DashboardDto> callbar);
	
	void editDashboard(DashboardDto dashboard,
			AsyncCallback<DashboardDto> callback);

	void addDadgetToDashboard(DashboardDto dashboard, DadgetDto dadget,
			AsyncCallback<DashboardDto> callback);

	void removeDadgetFromDashboard(DashboardDto dashboard, DadgetDto dadget,
			AsyncCallback<DashboardDto> callback);

	void editDadgetToDashboard(DashboardDto dashboardDto, DadgetDto dadget,
			AsyncCallback<DashboardDto> callback);

	void addToFavorites(AbstractTsDiskNodeDto node, AsyncCallback<Void> callback);
	
	void removeFromFavorites(AbstractTsDiskNodeDto node, AsyncCallback<Void> callback);

	void loadFavorites(AsyncCallback<FavoriteListDto> callback);

	void importReferencedDashboardForUser(DashboardNodeDto dashboard,
			AsyncCallback<DashboardDto> callback);

	void editDashboards(ArrayList<DashboardDto> dashboards,
			AsyncCallback<Void> callback);

	void getDashboardParameterInstances(DashboardDto dashboardDto, AsyncCallback<Map<String,ParameterInstanceDto>> callback);

	void setDashboardParameterInstances(DashboardDto dashboardDto, Set<ParameterInstanceDto> parameterInstances, AsyncCallback<List<DadgetDto>> callback);

	void getDadgetParameterInstances(ReportDadgetDto dadgetDto, AsyncCallback<Map<String, ParameterInstanceDto>> callback);

	void setDadgetParameterInstances(ReportDadgetDto dadgetDto, Set<ParameterInstanceDto> parameterInstances,
			AsyncCallback<DadgetDto> callback);

	void resetReferencedDashboard(DashboardReferenceDto dashboardDto, AsyncCallback<DashboardDto> callback);

	void loadDashboardForDisplay(DashboardDto dashboard, AsyncCallback<DashboardDto> transformAndKeepCallback);


}
