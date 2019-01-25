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
 
 
package net.datenwerke.rs.teamspace.service.teamspace.eventhandler;

import java.util.Collection;

import net.datenwerke.rs.teamspace.service.teamspace.TeamSpaceService;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.security.service.eventlogger.jpa.ForceRemoveEntityEvent;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;

public class UserForceRemoveEventHandler implements EventHandler<ForceRemoveEntityEvent> {

	private final TeamSpaceService tsService;
	
	@Inject
	public UserForceRemoveEventHandler(TeamSpaceService tsService) {
		this.tsService = tsService;
	}

	@Override
	public void handle(ForceRemoveEntityEvent event) {
		User user = (User) event.getObject();
	
		Collection<TeamSpace> ownedTs = tsService.getOwnedTeamSpaces(user);
		if(null != ownedTs && ! ownedTs.isEmpty()){
			for(TeamSpace teamspace : ownedTs){
				teamspace.setOwner(null);
				tsService.merge(teamspace);
			}
		}
		
	
	}
	

}
