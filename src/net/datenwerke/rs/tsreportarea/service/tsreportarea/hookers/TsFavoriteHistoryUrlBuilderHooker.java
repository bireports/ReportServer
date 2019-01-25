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
 
 
package net.datenwerke.rs.tsreportarea.service.tsreportarea.hookers;

import com.google.inject.Inject;

import net.datenwerke.gf.client.history.HistoryLocation;
import net.datenwerke.rs.core.service.history.helper.TreePanelHistoryUrlBuilderHooker;
import net.datenwerke.rs.teamspace.service.teamspace.TeamSpaceService;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.TsDiskUIModule;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskRoot;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.locale.TsDiskMessages;
import net.datenwerke.rs.utils.localization.LocalizationServiceImpl;

public class TsFavoriteHistoryUrlBuilderHooker extends TreePanelHistoryUrlBuilderHooker {

	private final TsDiskMessages messages = LocalizationServiceImpl.getMessages(TsDiskMessages.class);
	
	private final static String HISTORY_BUILDER_NAME = "TsFavoritesUrlBuilder";
	
	private final TsDiskService favoriteService;
	private final TeamSpaceService teamSpaceService;
	
	@Inject
	public TsFavoriteHistoryUrlBuilderHooker(
		TsDiskService favoriteService,
		TeamSpaceService teamSpaceService
		){
		
		/* store object */
		this.favoriteService = favoriteService;
		this.teamSpaceService = teamSpaceService;
	}
	
	@Override
	public boolean consumes(Object o) {
		if(! (o instanceof AbstractTsDiskNode))
			return false;
		
		AbstractTsDiskNode node = (AbstractTsDiskNode) o;
		TeamSpace teamSpace = favoriteService.getTeamSpaceFor(node);
		
		return teamSpaceService.mayAccess(teamSpace);
	}
	
	@Override
	protected void adjustLocation(Object o, HistoryLocation location) {
		AbstractTsDiskNode node = (AbstractTsDiskNode) o;

		TeamSpace teamSpace = favoriteService.getTeamSpaceFor(node);
		location.addParameter("ts", teamSpace.getId().toString());
		
		if(! (node instanceof TsDiskRoot)){
			location.addParameter("sel", node.getId().toString());
			location.addParameter("id", node.getParent().getId().toString());
		}
	}

	@Override
	protected String getTokenName() {
		return TsDiskUIModule.TEAMSPACE_SELECT_ITEM_HISTORY_TOKEN;
	}

	@Override
	protected String getBuilderId() {
		return HISTORY_BUILDER_NAME;
	}

	@Override
	protected String getNameFor(Object o) {
		AbstractTsDiskNode node = (AbstractTsDiskNode) o;
		TeamSpace teamSpace = favoriteService.getTeamSpaceFor(node);
		
		return messages.historyUrlBuilderName(teamSpace.getName());
	}

	@Override
	protected String getIconFor(Object o) {
		return "file";
	}

}
