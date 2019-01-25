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
 
 
package net.datenwerke.rs.tsreportarea.service.tsreportarea;

import net.datenwerke.rs.teamspace.service.teamspace.TeamSpaceAppDefinition;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpaceApp;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class TsDiskTeamSpaceAppDefinition implements TeamSpaceAppDefinition {

	public static final String APP_ID = "tsApp-favoriteReports";
	
	private final TsDiskService diskService;

	@Inject
	public TsDiskTeamSpaceAppDefinition(TsDiskService diskService) {
		this.diskService = diskService;
	}

	@Override
	public String getAppId() {
		return APP_ID;
	}

	@Override
	public String getName() {
		return "Favoriten";
	}

	@Override
	public String getDescription() {
		return "Favoriten";
	}

	@Override
	public void initializeApp(TeamSpace teamSpace, TeamSpaceApp app) {
		diskService.createRoot(teamSpace);
	}

}
