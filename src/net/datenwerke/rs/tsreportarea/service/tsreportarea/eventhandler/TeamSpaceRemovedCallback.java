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
 
 
package net.datenwerke.rs.tsreportarea.service.tsreportarea.eventhandler;

import javax.persistence.NoResultException;

import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskRoot;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.security.service.eventlogger.jpa.ForceRemoveEntityEvent;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;

import com.google.inject.Inject;

public class TeamSpaceRemovedCallback implements EventHandler<RemoveEntityEvent> {

	private final TsDiskService favoriteService;
	
	@Inject
	public TeamSpaceRemovedCallback(
		TsDiskService favoriteService	
		){
		
		/* store objects */
		this.favoriteService = favoriteService;
	}
	

	@Override
	public void handle(RemoveEntityEvent event) {
		TeamSpace teamSpace = (TeamSpace) event.getObject();
		
		try{
			TsDiskRoot root = favoriteService.getRoot(teamSpace);
			if(event instanceof ForceRemoveEntityEvent)
				favoriteService.forceRemove(root);
			else
				favoriteService.remove(root);
		} catch(NoResultException e){
			
		}
	}


}
