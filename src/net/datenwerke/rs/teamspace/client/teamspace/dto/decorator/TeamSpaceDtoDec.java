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
 
 
package net.datenwerke.rs.teamspace.client.teamspace.dto.decorator;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.IdedDto;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceApp;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceAppDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceRoleDto;

/**
 * Dto Decorator for {@link TeamSpaceDto}
 *
 */
public class TeamSpaceDtoDec extends TeamSpaceDto implements IdedDto {


	private static final long serialVersionUID = 1L;
	
	
	/* the current users role in this teamspace */
	private TeamSpaceRoleDto role;

	public TeamSpaceDtoDec() {
		super();
	}

	public List<TeamSpaceAppDto> getInstalledApps() {
		List<TeamSpaceAppDto> installedApps = new ArrayList<TeamSpaceAppDto>();
		
		for(TeamSpaceAppDto app : getApps())
			if(app.isInstalled())
				installedApps.add(app);
		
		return installedApps;
	}

	public boolean isAppInstalled(TeamSpaceApp app) {
		for(TeamSpaceAppDto installedApp : getInstalledApps())
			if(app.getAppId().equals(installedApp.getType()))
				return true;
		return false;
	}

	public TeamSpaceRoleDto getRole() {
		return role;
	}

	public void setRole(TeamSpaceRoleDto role) {
		this.role = role;
	}

	
}
