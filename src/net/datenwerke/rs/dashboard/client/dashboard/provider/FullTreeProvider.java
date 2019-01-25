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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.provider;

import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTree;
import net.datenwerke.gf.client.treedb.dnd.UITreeDragDropConfiguration;
import net.datenwerke.rs.dashboard.client.dashboard.DashboardTreeManagerDao;
import net.datenwerke.rs.dashboard.client.dashboard.DashboardUiModule;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardFolderDto;
import net.datenwerke.rs.dashboard.client.dashboard.ui.admin.DashboardManagerMainPanel;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class FullTreeProvider implements Provider<ManagerHelperTree>{

	private final BasicTreeProvider basicTreeProvider;
	private final DashboardTreeManagerDao treeHandler;
	private final DashboardManagerMainPanel mainPanel;
	
	@Inject
	public FullTreeProvider(
			BasicTreeProvider basicTreeProvider,
			DashboardTreeManagerDao treeHandler,
			DashboardManagerMainPanel mainPanel
		){
		
		this.basicTreeProvider = basicTreeProvider;
		this.treeHandler = treeHandler;
		this.mainPanel = mainPanel;
	}

	public ManagerHelperTree get() {
		/* build tree */
		final ManagerHelperTree tree = basicTreeProvider.get();
		tree.getStore().enableDtoAwareness(true);
				
		/* dnd */
		UITreeDragDropConfiguration dndConfig = new UITreeDragDropConfiguration();
		dndConfig.addDropTarget(DashboardFolderDto.class);
		tree.enableDragDrop(treeHandler, dndConfig);
		tree.enableClipboardProvider();

		/* set selections */
		tree.setSelectionProvider(mainPanel);
		
		/* double click */
		tree.enableDoubleClickAction();
		
		/* history location */
		tree.setHistoryLocation(DashboardUiModule.DASHBOARD_HISTORY_TOKEN);
		
		/* tree menu */
		tree.configureMenuProvider(DashboardUiModule.ADMIN_TREE_MENU_NAME);
		
		return tree;
	}
}
