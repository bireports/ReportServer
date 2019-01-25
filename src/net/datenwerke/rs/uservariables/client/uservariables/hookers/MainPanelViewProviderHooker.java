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
 
 
package net.datenwerke.rs.uservariables.client.uservariables.hookers;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewProviderHookImpl;
import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.uservariables.client.uservariables.genrights.UserVariableAdminViewGenericTargetIdentifier;
import net.datenwerke.rs.uservariables.client.uservariables.mainpanel.UserVariablesDefinitionPanel;
import net.datenwerke.rs.uservariables.client.uservariables.mainpanel.UserVariablesView;
import net.datenwerke.security.client.security.SecurityUIService;
import net.datenwerke.security.client.security.dto.ReadDto;
import net.datenwerke.security.client.usermanager.dto.OrganisationalUnitDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class MainPanelViewProviderHooker extends MainPanelViewProviderHookImpl {

	public static final String USERMANAGER_VIEW_PROVIDER_ID = "USERMANAGER_MAIN_VIEW_PROVIDER";
	
	private final Provider<UserVariablesView> userVariablesViewProvider;
	private final Provider<UserVariablesDefinitionPanel> defProvider;
	private final SecurityUIService securityService;


	@Inject
	public MainPanelViewProviderHooker(
			HookHandlerService hookHandlerService, 
			Provider<UserVariablesView> userVariablesViewProvider,
			Provider<UserVariablesDefinitionPanel> defProvider,
			SecurityUIService securityService
			) {
		super(hookHandlerService);
		this.userVariablesViewProvider = userVariablesViewProvider;
		this.defProvider = defProvider;
		this.securityService = securityService;
	}

	public List<MainPanelView> getPrimaryViews(AbstractNodeDto node) {
		List<MainPanelView> views = new ArrayList<MainPanelView>();
		
		if(node instanceof OrganisationalUnitDto && Boolean.TRUE.equals(((OrganisationalUnitDto)node).isIsUserRoot()))
			if(securityService.hasRight(UserVariableAdminViewGenericTargetIdentifier.class, ReadDto.class))
				views.add(defProvider.get());
		
		if(node instanceof UserDto || node instanceof OrganisationalUnitDto)
			views.add(userVariablesViewProvider.get());
		
		return views;
	}



	@Override
	public String getViewProviderId() {
		return USERMANAGER_VIEW_PROVIDER_ID;
	}

}
