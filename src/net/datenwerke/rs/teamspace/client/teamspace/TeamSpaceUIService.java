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
 
 
package net.datenwerke.rs.teamspace.client.teamspace;

import net.datenwerke.gxtdto.client.stores.LoadableListStore;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceRoleDto;

import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoader;


/**
 * 
 *
 */
public interface TeamSpaceUIService {

	public interface TeamSpaceOperationSuccessHandler{
		public void onSuccess(TeamSpaceDto teamSpace);
	}
	
	/**
	 * Tells if the current user is an admin
	 * @return
	 */
	public boolean isGlobalTsAdmin();

	public boolean hasTeamSpaceCreateRight();

	boolean isAdmin(TeamSpaceDto teamSpace);

	boolean isManager(TeamSpaceDto teamSpace);

	boolean isUser(TeamSpaceDto teamSpace);

	boolean isGuest(TeamSpaceDto teamSpace);

	TeamSpaceRoleDto getRole(TeamSpaceDto teamSpace);

	LoadableListStore<ListLoadConfig, TeamSpaceDto, ListLoadResult<TeamSpaceDto>> getAllTeamSpacesStore();

	LoadableListStore<ListLoadConfig, TeamSpaceDto, ListLoadResult<TeamSpaceDto>> getTeamSpacesStore();

	ListLoader<ListLoadConfig, ListLoadResult<TeamSpaceDto>> getTeamSpacesLoader();

	ListLoader<ListLoadConfig, ListLoadResult<TeamSpaceDto>> getAllTeamSpacesLoader();

	boolean hasTeamSpaceRemoveRight();
	
	public void gotoTeamSpace(TeamSpaceDto selectedItem);

	void displayAddSpaceDialog(TeamSpaceOperationSuccessHandler successHandler);

	public void notifyOfDeletion(TeamSpaceDto deleted);

	public void notifyOfAddition(TeamSpaceDto added);

	public void notifyOfUpdate(TeamSpaceDto updated);
}
