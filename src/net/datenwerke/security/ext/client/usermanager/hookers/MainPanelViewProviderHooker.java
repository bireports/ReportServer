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
 
 
package net.datenwerke.security.ext.client.usermanager.hookers;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewProviderHookImpl;
import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.security.client.usermanager.dto.GroupDto;
import net.datenwerke.security.client.usermanager.dto.OrganisationalUnitDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.ext.client.security.ui.SecurityView;
import net.datenwerke.security.ext.client.usermanager.ui.forms.GroupForm;
import net.datenwerke.security.ext.client.usermanager.ui.forms.OrganisationalUnitForm;
import net.datenwerke.security.ext.client.usermanager.ui.forms.UserForm;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class MainPanelViewProviderHooker extends MainPanelViewProviderHookImpl {

	public static final String USERMANAGER_VIEW_PROVIDER_ID = "USERMANAGER_MAIN_VIEW_PROVIDER";
	private final Provider<UserForm> userFormProvider;
	private final Provider<GroupForm> groupFormProvider;
	private final Provider<OrganisationalUnitForm> ouFormProvider;
	private final Provider<SecurityView> securityViewProvider;
	
	@Inject
	public MainPanelViewProviderHooker(
		HookHandlerService hookHandler,
		
		Provider<UserForm> userFormProvider,
		Provider<GroupForm> roleFormProvider,
		Provider<OrganisationalUnitForm> ouFormProvider,
		Provider<SecurityView> securityViewProvider
		){
		super(hookHandler);
		
		/* store objects */
		this.userFormProvider = userFormProvider;
		this.groupFormProvider = roleFormProvider;
		this.ouFormProvider = ouFormProvider;
		this.securityViewProvider = securityViewProvider;
	}
	
	public List<MainPanelView> getPrimaryViews(AbstractNodeDto node) {
		if(node instanceof UserDto)
			return getViewForUser();
		if(node instanceof GroupDto)
			return getViewForGroup();
		if(node instanceof OrganisationalUnitDto)
			return getViewForOU();
		return new ArrayList<MainPanelView>();
	}

	private List<MainPanelView> getViewForOU() {
		List<MainPanelView> views = new ArrayList<MainPanelView>();
		views.add(ouFormProvider.get());
		views.add(securityViewProvider.get());
		return views;
	}

	private List<MainPanelView> getViewForGroup() {
		List<MainPanelView> views = new ArrayList<MainPanelView>();
		views.add(groupFormProvider.get());
		views.add(securityViewProvider.get());
		return views;
	}

	private List<MainPanelView> getViewForUser() {
		List<MainPanelView> views = new ArrayList<MainPanelView>();
		views.add(userFormProvider.get());
		views.add(securityViewProvider.get());
		return views;
	}

	@Override
	public String getViewProviderId() {
		return USERMANAGER_VIEW_PROVIDER_ID;
	}

}
