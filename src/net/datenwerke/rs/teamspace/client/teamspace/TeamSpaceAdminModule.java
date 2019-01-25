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
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gf.client.administration.interfaces.AdminModule;
import net.datenwerke.rs.teamspace.client.teamspace.locale.TeamSpaceMessages;
import net.datenwerke.rs.teamspace.client.teamspace.ui.admin.TeamSpaceManagerPanel;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

/**
 * 
 *
 */
public class TeamSpaceAdminModule implements AdminModule {

	final private Provider<TeamSpaceManagerPanel> managerPanelanelProvider;
	
	@Inject
	public TeamSpaceAdminModule(
		Provider<TeamSpaceManagerPanel> managerPanelanelProvider
		){
		
		/* store objects */
		this.managerPanelanelProvider = managerPanelanelProvider;
	}
	
	@Override
	public ImageResource getNavigationIcon(){
		return BaseIcon.GROUP_EDIT.toImageResource();
	}
	
	@Override
	public String getNavigationText() {
		return TeamSpaceMessages.INSTANCE.clientModuleName();
	}

	@Override
	public Widget getMainWidget() {
		return managerPanelanelProvider.get();
	}
	
	@Override
	public void notifyOfSelection() {
		
	}
}