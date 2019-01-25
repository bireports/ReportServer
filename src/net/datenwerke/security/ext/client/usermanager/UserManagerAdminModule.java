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
 
 
package net.datenwerke.security.ext.client.usermanager;

import net.datenwerke.gf.client.administration.interfaces.AdminModule;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.ext.client.usermanager.locale.UsermanagerMessages;
import net.datenwerke.security.ext.client.usermanager.ui.UserManagerPanel;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 
 *
 */
public class UserManagerAdminModule implements AdminModule{
	
	final private Provider<UserManagerPanel> userManagerPanelProvider;
	
	@Inject
	public UserManagerAdminModule(
		Provider<UserManagerPanel> userManagerPanel	
		){
		
		/* store objects */
		this.userManagerPanelProvider = userManagerPanel;
	}
	
	@Override
	public ImageResource getNavigationIcon(){
		return BaseIcon.USER.toImageResource();
	}
	
	@Override
	public String getNavigationText() {
		return UsermanagerMessages.INSTANCE.userManagernavtext();
	}

	@Override
	public Widget getMainWidget() {
		return userManagerPanelProvider.get();
	}
	
	@Override
	public void notifyOfSelection() {
	}
}
