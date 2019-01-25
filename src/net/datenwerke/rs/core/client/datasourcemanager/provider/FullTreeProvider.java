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
 
 
package net.datenwerke.rs.core.client.datasourcemanager.provider;

import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTree;
import net.datenwerke.gf.client.treedb.dnd.UITreeDragDropConfiguration;
import net.datenwerke.rs.core.client.datasourcemanager.DatasourceTreeManagerDao;
import net.datenwerke.rs.core.client.datasourcemanager.DatasourceUIModule;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceFolderDto;
import net.datenwerke.rs.core.client.datasourcemanager.ui.DatasourceManagerMainPanel;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class FullTreeProvider implements Provider<ManagerHelperTree>{

	private final BasicTreeProvider basicTreeProvider;
	private final DatasourceTreeManagerDao datasourceTreeHandler;
	private final DatasourceManagerMainPanel mainPanel;
	
	@Inject
	public FullTreeProvider(
			BasicTreeProvider basicTreeProvider,
			DatasourceTreeManagerDao datasourceTreeHandler,
			DatasourceManagerMainPanel mainPanel
		){
		
		this.basicTreeProvider = basicTreeProvider;
		this.datasourceTreeHandler = datasourceTreeHandler;
		this.mainPanel = mainPanel;
	}

	public ManagerHelperTree get() {
		/* build tree */
		final ManagerHelperTree tree = basicTreeProvider.get();
		tree.getStore().enableDtoAwareness(true);
				
		/* dnd */
		UITreeDragDropConfiguration dndConfig = new UITreeDragDropConfiguration();
		dndConfig.addDropTarget(DatasourceFolderDto.class);
		tree.enableDragDrop(datasourceTreeHandler, dndConfig);
		tree.enableClipboardProvider();

		/* set selections */
		tree.setSelectionProvider(mainPanel);
		
		/* double click */
		tree.enableDoubleClickAction();
		
		/* history location */
		tree.setHistoryLocation(DatasourceUIModule.DATASOURCE_FAV_HISTORY_TOKEN);
		
		/* tree menu */
		tree.configureMenuProvider(DatasourceUIModule.ADMIN_TREE_MENU_NAME);
		
		return tree;
	}
}
