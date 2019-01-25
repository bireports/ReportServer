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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardContainerDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardNodeDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardReferenceDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.FavoriteListDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.ReportDadgetDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;

@RemoteServiceRelativePath("dashboard")
public interface DashboardRpcService extends RemoteService {

	public DashboardContainerDto getDashboardForUser() throws ServerCallFailedException;
	
	public DashboardDto createDashboardForUser() throws ServerCallFailedException;
	
	public DashboardDto importReferencedDashboardForUser(DashboardNodeDto dashboard) throws ServerCallFailedException;
	
	public void removeDashboard(DashboardDto dashboard)  throws ServerCallFailedException;
	
	public DashboardDto editDashboard(DashboardDto dashboard) throws ServerCallFailedException;
	
	public void editDashboards(ArrayList<DashboardDto> dashboards) throws ServerCallFailedException;
	
	public DashboardDto addDadgetToDashboard(DashboardDto dashboard, DadgetDto dadget) throws ServerCallFailedException;
	
	public DashboardDto editDadgetToDashboard(DashboardDto dashboardDto, DadgetDto dadget) throws ServerCallFailedException;
	
	public DashboardDto removeDadgetFromDashboard(DashboardDto dashboard, DadgetDto dadget) throws ServerCallFailedException;
	
	
	
	public void addToFavorites(AbstractTsDiskNodeDto node) throws ServerCallFailedException;
	
	public void removeFromFavorites(AbstractTsDiskNodeDto node) throws ServerCallFailedException;
	
	public FavoriteListDto loadFavorites() throws ServerCallFailedException;

	public DashboardDto reloadDashboard(DashboardDto dashboard);

	public Map<String, ParameterInstanceDto> getDashboardParameterInstances(DashboardDto dashboardDto) throws ServerCallFailedException;
	
	public List<DadgetDto> setDashboardParameterInstances(DashboardDto dashboardDto, Set<ParameterInstanceDto> parameterInstances) throws ServerCallFailedException;

	Map<String, ParameterInstanceDto> getDadgetParameterInstances(ReportDadgetDto dadgetDto) throws ServerCallFailedException;

	DadgetDto setDadgetParameterInstances(ReportDadgetDto dadgetDto, Set<ParameterInstanceDto> parameterInstances)
			throws ServerCallFailedException;

	DashboardDto resetReferencedDashboard(DashboardReferenceDto dashboardDto) throws ServerCallFailedException;

	DashboardDto loadDashboardForDisplay(DashboardDto dashboard);
}
