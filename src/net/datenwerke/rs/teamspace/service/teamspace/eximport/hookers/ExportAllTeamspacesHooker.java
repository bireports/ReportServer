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
 
 
package net.datenwerke.rs.teamspace.service.teamspace.eximport.hookers;

import net.datenwerke.eximport.ex.ExportConfig;
import net.datenwerke.eximport.ex.entity.EntityExportItemConfig;
import net.datenwerke.rs.eximport.service.eximport.hooks.ExportAllHook;
import net.datenwerke.rs.teamspace.service.teamspace.TeamSpaceService;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;

import com.google.inject.Inject;

public class ExportAllTeamspacesHooker implements ExportAllHook {

	private final TeamSpaceService teamSpaceService;
	
	@Inject
	public ExportAllTeamspacesHooker(TeamSpaceService teamSpaceService) {
		this.teamSpaceService = teamSpaceService;
	}

	@Override
	public void configure(ExportConfig config) {
		if(! teamSpaceService.isGlobalTsAdmin())
			throw new ViolatedSecurityException();
		
		for(TeamSpace ts : teamSpaceService.getAllTeamSpaces())
			config.addItemConfig(new EntityExportItemConfig(ts));
	}

}
