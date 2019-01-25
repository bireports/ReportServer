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
 
 
package net.datenwerke.rs.core.client.datasourcemanager.hookers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewProviderHook;
import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceFolderDto;
import net.datenwerke.rs.core.client.datasourcemanager.hooks.DatasourceDefinitionConfigProviderHook;
import net.datenwerke.rs.core.client.datasourcemanager.ui.forms.FolderForm;
import net.datenwerke.security.ext.client.security.ui.SecurityView;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class MainPanelViewProviderHooker implements MainPanelViewProviderHook {

	private final HookHandlerService hookHandler;
	private final Provider<FolderForm> folderFormProvider;
	
	private final Provider<SecurityView> securityViewProvider;
	
	
	@Inject
	public MainPanelViewProviderHooker(
			HookHandlerService hookHandler,
			Provider<FolderForm> folderFormProvider,
			
			Provider<SecurityView> securityViewProvider
		){

		/* store objects */
		this.hookHandler = hookHandler;
		this.folderFormProvider = folderFormProvider;
		this.securityViewProvider = securityViewProvider;
	}
	
	public List<MainPanelView> mainPanelViewProviderHook_getView(AbstractNodeDto node) {
		if(node instanceof DatasourceFolderDto)
			return getViewForDatasourceFolder();
		if(node instanceof DatasourceDefinitionDto)
			return getViewForDatasources((DatasourceDefinitionDto) node);
		return null;
	}

	private List<MainPanelView> getViewForDatasources(DatasourceDefinitionDto dsd) {
		List<MainPanelView> views = new ArrayList<MainPanelView>();
		
		for(DatasourceDefinitionConfigProviderHook config : hookHandler.getHookers(DatasourceDefinitionConfigProviderHook.class)){
			if(config.consumes(dsd))
				views.addAll(config.getAdminViews(dsd));
		}
		
		views.add(securityViewProvider.get());
		
		return views;
	}

	private List<MainPanelView> getViewForDatasourceFolder() {
		return Arrays.asList(folderFormProvider.get(), securityViewProvider.get());
	}

}
