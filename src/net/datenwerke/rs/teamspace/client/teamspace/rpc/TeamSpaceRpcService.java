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
 
 
package net.datenwerke.rs.teamspace.client.teamspace.rpc;

import java.util.Collection;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.ListLoadResult;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.teamspace.client.teamspace.dto.StrippedDownTeamSpaceMemberDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;

@RemoteServiceRelativePath("teamspace")
public interface TeamSpaceRpcService extends RemoteService {

	public TeamSpaceDto getPrimarySpace() throws ServerCallFailedException;
	
	public TeamSpaceDto getExplicitPrimarySpace() throws ServerCallFailedException;
	
	public void setPrimarySpace(TeamSpaceDto teamSpaceDto) throws ServerCallFailedException;

	public TeamSpaceDto reloadTeamSpace(TeamSpaceDto teamSpace) throws ServerCallFailedException;
	
	public TeamSpaceDto createNewTeamSpace(TeamSpaceDto dummySpace) throws ServerCallFailedException;

	public ListLoadResult<TeamSpaceDto> loadTeamSpaces() throws ServerCallFailedException;
	
	public void removeTeamSpace(TeamSpaceDto teamSpace) throws ServerCallFailedException;
	
	public TeamSpaceDto editTeamSpaceSettings(TeamSpaceDto space) throws ServerCallFailedException;
	
	public TeamSpaceDto setMembers(TeamSpaceDto teamSpace, Collection<StrippedDownTeamSpaceMemberDto> members) throws ServerCallFailedException;
	
	public ListLoadResult<TeamSpaceDto> loadAllTeamSpaces() throws ServerCallFailedException;
	
	public TeamSpaceDto reloadTeamSpaceForEdit(TeamSpaceDto teamSpaceDto) throws ServerCallFailedException;
	
}
