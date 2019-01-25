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
 
 
package net.datenwerke.rs.teamspace.client.teamspace;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import net.datenwerke.gf.client.homepage.modules.ClientMainModuleImpl;
import net.datenwerke.rs.teamspace.client.teamspace.locale.TeamSpaceMessages;
import net.datenwerke.rs.teamspace.client.teamspace.ui.TeamSpaceMainComponent;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

/**
 * 
 *
 */
@Singleton
public class TeamSpaceClientMainModule extends ClientMainModuleImpl {
	
	private final Provider<TeamSpaceMainComponent> mainComponentProvider;
	private TeamSpaceMainComponent mainComponent;
	
	@Inject
	public TeamSpaceClientMainModule(
		Provider<TeamSpaceMainComponent> mainComponentProvider
		){
		
		/* store objects */
		this.mainComponentProvider = mainComponentProvider;
		
	}
	
	@Override
	public String getModuleName() {
		return TeamSpaceMessages.INSTANCE.clientModuleName();
	}

	@Override
	public ImageResource getIcon() {
		return BaseIcon.GROUP_EDIT.toImageResource();
	}

	@Override
	public TeamSpaceMainComponent getMainWidget() {
		if(null == mainComponent)
			mainComponent = mainComponentProvider.get();
		return mainComponent;
	}

	@Override
	public void selected() {
		mainComponent.notifyOfSelection();
	}

	@Override
	public boolean isRecyclable() {
		return true;
	}
}
