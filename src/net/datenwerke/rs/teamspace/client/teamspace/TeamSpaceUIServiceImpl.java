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

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.stores.LoadableListStore;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceMemberDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceRoleDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.decorator.TeamSpaceDtoDec;
import net.datenwerke.rs.teamspace.client.teamspace.dto.pa.TeamSpaceDtoPA;
import net.datenwerke.rs.teamspace.client.teamspace.security.TeamSpaceGenericTargetIdentifier;
import net.datenwerke.rs.teamspace.client.teamspace.security.rights.TeamSpaceAdministratorDto;
import net.datenwerke.security.client.security.SecurityUIService;
import net.datenwerke.security.client.security.dto.WriteDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoader;

/**
 * 
 *
 */
public class TeamSpaceUIServiceImpl implements TeamSpaceUIService {

	private static TeamSpaceDtoPA tsPA = GWT.create(TeamSpaceDtoPA.class);
	
	private final LoginService loginService;
	private final SecurityUIService securityService;
	private final TeamSpaceDao tsDao;
	
	@Inject
	public TeamSpaceUIServiceImpl(
		LoginService loginService,
		SecurityUIService securityService,
		TeamSpaceDao tsDao
		){
		
		/* store objects */
		this.loginService = loginService;
		this.securityService = securityService;
		this.tsDao = tsDao;
	}
	
	@Override
	public boolean isGlobalTsAdmin() {
		return securityService.hasRight(TeamSpaceGenericTargetIdentifier.class, TeamSpaceAdministratorDto.class);
	}

	@Override
	public boolean hasTeamSpaceCreateRight() {
		return securityService.hasRight(TeamSpaceGenericTargetIdentifier.class, WriteDto.class);
	}

	@Override
	public boolean isAdmin(TeamSpaceDto teamSpace) {
		return hasRole(teamSpace, TeamSpaceRoleDto.ADMIN);
	}

	@Override
	public boolean isManager(TeamSpaceDto teamSpace) {
		return hasRole(teamSpace, TeamSpaceRoleDto.MANAGER);
	}

	@Override
	public boolean isUser(TeamSpaceDto teamSpace) {
		return hasRole(teamSpace, TeamSpaceRoleDto.USER);
	}

	@Override
	public boolean isGuest(TeamSpaceDto teamSpace) {
		return hasRole(teamSpace, TeamSpaceRoleDto.GUEST);
	}

	private boolean hasRole(TeamSpaceDto teamSpace, TeamSpaceRoleDto roleToHave) {
		if(isGlobalTsAdmin())
			return true;
		TeamSpaceRoleDto role = getRole(teamSpace);
		if(null == role)
			return false;
		return roleToHave.compareTo(role) >= 0;
	}
	
	@Override
	public TeamSpaceRoleDto getRole(TeamSpaceDto teamSpace){
		return ((TeamSpaceDtoDec)teamSpace).getRole();
	}
	
	@Override
	public ListLoader<ListLoadConfig, ListLoadResult<TeamSpaceDto>> getTeamSpacesLoader(){
		/* create store */
		RpcProxy<ListLoadConfig, ListLoadResult<TeamSpaceDto>> proxy = new RpcProxy<ListLoadConfig, ListLoadResult<TeamSpaceDto>>() {
			@Override
			public void load(ListLoadConfig loadConfig,
					AsyncCallback<ListLoadResult<TeamSpaceDto>> callback) {
				tsDao.loadTeamSpaces(callback);
			}
		};

		return new ListLoader<ListLoadConfig, ListLoadResult<TeamSpaceDto>>(proxy);
	}
	
	@Override
	public LoadableListStore<ListLoadConfig, TeamSpaceDto, ListLoadResult<TeamSpaceDto>> getTeamSpacesStore(){
		return new LoadableListStore<ListLoadConfig, TeamSpaceDto, ListLoadResult<TeamSpaceDto>>(tsPA.dtoId(), getTeamSpacesLoader());
	}
	
	@Override
	public ListLoader<ListLoadConfig, ListLoadResult<TeamSpaceDto>> getAllTeamSpacesLoader(){
		/* create store */
		RpcProxy<ListLoadConfig, ListLoadResult<TeamSpaceDto>> proxy = new RpcProxy<ListLoadConfig, ListLoadResult<TeamSpaceDto>>() {
			
			@Override
			public void load(ListLoadConfig loadConfig,
					AsyncCallback<ListLoadResult<TeamSpaceDto>> callback) {
				tsDao.loadAllTeamSpaces(callback);
			}
		};
		return new ListLoader<ListLoadConfig, ListLoadResult<TeamSpaceDto>>(proxy);
	}
	
	@Override
	public LoadableListStore<ListLoadConfig, TeamSpaceDto, ListLoadResult<TeamSpaceDto>> getAllTeamSpacesStore(){
		return new LoadableListStore<ListLoadConfig, TeamSpaceDto, ListLoadResult<TeamSpaceDto>>(tsPA.dtoId(), getAllTeamSpacesLoader());
	}
	

}
