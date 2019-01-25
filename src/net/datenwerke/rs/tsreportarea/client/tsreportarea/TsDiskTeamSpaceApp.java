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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea;

import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceApp;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.locale.TsFavoriteMessages;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.ui.TsDiskMainComponent;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.widget.core.client.Component;

/**
 * 
 *
 */
public class TsDiskTeamSpaceApp implements TeamSpaceApp {

	public static final String APP_ID = "tsApp-favoriteReports";
	
	private final Provider<TsDiskMainComponent> mainComponentProvider;
	
	private TsDiskMainComponent appComponent;
	
	@Inject
	public TsDiskTeamSpaceApp(
		Provider<TsDiskMainComponent> mainComponentProvider	
		){
		
		/* store objects */
		this.mainComponentProvider = mainComponentProvider;
	}
	
	@Override
	public String getAppId() {
		return APP_ID;
	}

	
	@Override
	public String getName() {
		return TsFavoriteMessages.INSTANCE.appName();
	}
	
	@Override
	public String getDescription() {
		return TsFavoriteMessages.INSTANCE.appDescription();
	}

	@Override
	public ImageResource getIcon() {
		return BaseIcon.NEWSPAPER_O.toImageResource();
	}

	@Override
	public Component getAppComponent() {
		if(null == appComponent)
			appComponent = mainComponentProvider.get();
		return appComponent;
	}

	@Override
	public void displaySpace(TeamSpaceDto currentSpace) {
		appComponent.setCurrentSpace(currentSpace);
	}


}
