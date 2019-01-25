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
 
 
package net.datenwerke.usermanager.ext.client.properties.hookers;

import java.util.Arrays;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewProviderHook;
import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.usermanager.ext.client.properties.ui.UserPropertiesView;

/**
 * 
 *
 */
public class UserPropertiesViewProviderHooker implements MainPanelViewProviderHook {

	private final Provider<UserPropertiesView> propertiesViewProvider;
	
	@Inject
	public UserPropertiesViewProviderHooker(
		Provider<UserPropertiesView> propertiesViewProvider
		){

		/* store objects */
		this.propertiesViewProvider = propertiesViewProvider;
	}
	
	public List<MainPanelView> mainPanelViewProviderHook_getView(AbstractNodeDto node) {
		if(node instanceof UserDto)
			return getView();

		return null;
	}

	private List<MainPanelView> getView() {
		return Arrays.asList(new MainPanelView[]{propertiesViewProvider.get()});
	}

}
