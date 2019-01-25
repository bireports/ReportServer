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
import java.util.Iterator;

import com.google.inject.Inject;

import net.datenwerke.rs.teamspace.service.teamspace.TeamSpaceService;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpaceMember;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.rs.utils.exception.exceptions.NeedForcefulDeleteException;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;
import net.datenwerke.security.service.usermanager.entities.User;

public class UserRemoveEventHandler implements EventHandler<RemoveEntityEvent> {

	private final TeamSpaceService tsService;
	
	@Inject
	public UserRemoveEventHandler(TeamSpaceService tsService) {
		this.tsService = tsService;
	}

	@Override
	public void handle(RemoveEntityEvent event) {
		User user = (User) event.getObject();
	
		Collection<TeamSpace> ownedTs = tsService.getOwnedTeamSpaces(user);
		if(null != ownedTs && ! ownedTs.isEmpty()){
			Iterator<TeamSpace> it = ownedTs.iterator();
			StringBuilder error = new StringBuilder("User " + user.getUsername() + " is owner of teamspaces. Teamspace Ids: " + it.next().getId());
			while(it.hasNext())
				error.append(", ").append(it.next().getId());
			
			throw new NeedForcefulDeleteException(error.toString());
		}
		
		Collection<TeamSpace> teamspaces = tsService.getTeamSpaces(user);
		if(null != teamspaces && ! teamspaces.isEmpty()){
			for(TeamSpace teamspace : teamspaces){
				TeamSpaceMember member = tsService.getMemberFor(teamspace,user);
				teamspace.removeMember(member);
				tsService.remove(member);
			}
		}
	}
	

}
