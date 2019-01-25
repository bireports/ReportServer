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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.hookers;

import java.util.Arrays;
import java.util.List;

import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewProviderHook;
import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetNodeDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardFolderDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardNodeDto;
import net.datenwerke.rs.dashboard.client.dashboard.ui.admin.forms.DadgetNodeForm;
import net.datenwerke.rs.dashboard.client.dashboard.ui.admin.forms.DashboardNodeForm;
import net.datenwerke.rs.dashboard.client.dashboard.ui.admin.forms.FolderForm;
import net.datenwerke.security.ext.client.security.ui.SecurityView;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class MainPanelViewProviderHooker implements MainPanelViewProviderHook {

	private final Provider<FolderForm> folderFormProvider;
	
	private final Provider<SecurityView> securityViewProvider;

	private final Provider<DadgetNodeForm> dadgetNodeFormProvider;

	private final  Provider<DashboardNodeForm> dashboardNodeFormProvider;
	
	
	@Inject
	public MainPanelViewProviderHooker(
			Provider<FolderForm> folderFormProvider,
			Provider<DadgetNodeForm> dadgetNodeFormProvider,
			Provider<DashboardNodeForm> dashboardNodeFormProvider,
			
			Provider<SecurityView> securityViewProvider
		){

		/* store objects */
		this.folderFormProvider = folderFormProvider;
		this.dadgetNodeFormProvider = dadgetNodeFormProvider;
		this.dashboardNodeFormProvider = dashboardNodeFormProvider;
		this.securityViewProvider = securityViewProvider;
	}
	
	public List<MainPanelView> mainPanelViewProviderHook_getView(AbstractNodeDto node) {
		if(node instanceof DashboardFolderDto)
			return getViewForFolder();
		if(node instanceof DadgetNodeDto)
			return getViewForDadgetNode();
		if(node instanceof DashboardNodeDto)
			return getViewForDashboardNode();
		return null;
	}

	private List<MainPanelView> getViewForDashboardNode() {
		return Arrays.asList(dashboardNodeFormProvider.get(), securityViewProvider.get());
	}

	private List<MainPanelView> getViewForDadgetNode() {
		return Arrays.asList(dadgetNodeFormProvider.get(), securityViewProvider.get());
	}

	private List<MainPanelView> getViewForFolder() {
		return Arrays.asList(folderFormProvider.get(), securityViewProvider.get());
	}

}
