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
 
 
package net.datenwerke.rs.teamspace.client.teamspace.rpc;

import java.util.Collection;

import net.datenwerke.rs.teamspace.client.teamspace.dto.StrippedDownTeamSpaceMemberDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.ListLoadResult;

public interface TeamSpaceRpcServiceAsync {

	void getPrimarySpace(AsyncCallback<TeamSpaceDto> callback);

	void setPrimarySpace(TeamSpaceDto teamSpaceDto, AsyncCallback<Void> callback);
	
	void reloadTeamSpace(TeamSpaceDto teamSpace,
			AsyncCallback<TeamSpaceDto> callback);
	
	void createNewTeamSpace(TeamSpaceDto dummySpace,
			AsyncCallback<TeamSpaceDto> callback);

	void loadTeamSpaces(AsyncCallback<ListLoadResult<TeamSpaceDto>> callback);

	void removeTeamSpace(TeamSpaceDto teamSpace, AsyncCallback<Void> callback);

	void editTeamSpaceSettings(TeamSpaceDto space,
			AsyncCallback<TeamSpaceDto> callback);

	void setMembers(TeamSpaceDto teamSpace,
			Collection<StrippedDownTeamSpaceMemberDto> members,
			AsyncCallback<TeamSpaceDto> callback);

	void loadAllTeamSpaces(AsyncCallback<ListLoadResult<TeamSpaceDto>> callback);

	void reloadTeamSpaceForEdit(TeamSpaceDto teamSpaceDto, AsyncCallback<TeamSpaceDto> callback);

}
