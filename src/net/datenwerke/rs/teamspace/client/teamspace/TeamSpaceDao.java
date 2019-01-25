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
 
 
package net.datenwerke.rs.teamspace.client.teamspace;

import java.util.Collection;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.loader.ListLoadResult;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.teamspace.client.teamspace.dto.StrippedDownTeamSpaceMemberDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.client.teamspace.rpc.TeamSpaceRpcServiceAsync;

public class TeamSpaceDao extends Dao {

	private final TeamSpaceRpcServiceAsync rpcService;
	
	@Inject
	public TeamSpaceDao(
		TeamSpaceRpcServiceAsync rpcService	
		){
		
		/* store objects */
		this.rpcService = rpcService;
	}
	
	public void getPrimarySpace(AsyncCallback<TeamSpaceDto> callback){
		rpcService.getPrimarySpace(transformAndKeepCallback(callback));
	}
	
	public void setPrimarySpace(TeamSpaceDto teamSpaceDto, AsyncCallback<Void> callback){
		rpcService.setPrimarySpace(teamSpaceDto, transformAndKeepCallback(callback));
	}

	
	public void reloadTeamSpace(TeamSpaceDto teamSpace,
			AsyncCallback<TeamSpaceDto> callback){
		rpcService.reloadTeamSpace(teamSpace, transformAndKeepCallback(callback));
	}

	
	public void createNewTeamSpace(TeamSpaceDto dummySpace,
			AsyncCallback<TeamSpaceDto> callback){
		rpcService.createNewTeamSpace(dummySpace, transformAndKeepCallback(callback));
	}
	
	public void loadTeamSpaces(AsyncCallback<ListLoadResult<TeamSpaceDto>> callback){
		rpcService.loadTeamSpaces(transformListLoadCallback(callback));
	}
	
	public void removeTeamSpace(TeamSpaceDto teamSpace, AsyncCallback<Void> callback){
		rpcService.removeTeamSpace(teamSpace, transformAndKeepCallback(callback));
	}
	
	public void editTeamSpaceSettings(TeamSpaceDto space, AsyncCallback<TeamSpaceDto> callback){
		rpcService.editTeamSpaceSettings(space, transformAndKeepCallback(callback));
	}
	
	public void setMembers(TeamSpaceDto teamSpace,
			Collection<StrippedDownTeamSpaceMemberDto> members,
			AsyncCallback<TeamSpaceDto> callback){
		rpcService.setMembers(teamSpace, members, transformAndKeepCallback(callback));
	}
	
	public void loadAllTeamSpaces(AsyncCallback<ListLoadResult<TeamSpaceDto>> callback){
		rpcService.loadAllTeamSpaces(transformListLoadCallback(callback));
	}
	
	public void reloadTeamSpaceForEdit(TeamSpaceDto teamSpace, AsyncCallback<TeamSpaceDto> callback) {
		rpcService.reloadTeamSpaceForEdit(teamSpace, transformAndKeepCallback(callback));
	}

}
