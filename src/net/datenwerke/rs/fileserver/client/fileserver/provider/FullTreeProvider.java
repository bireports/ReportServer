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
 
 
package net.datenwerke.rs.fileserver.client.fileserver.provider;

import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTree;
import net.datenwerke.gf.client.treedb.dnd.UITreeDragDropConfiguration;
import net.datenwerke.rs.fileserver.client.fileserver.FileServerTreeManagerDao;
import net.datenwerke.rs.fileserver.client.fileserver.FileServerUiModule;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFolderDto;
import net.datenwerke.rs.fileserver.client.fileserver.ui.FileServerManagerMainPanel;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class FullTreeProvider implements Provider<ManagerHelperTree>{

	private final BasicTreeProvider basicTreeProvider;
	private final FileServerTreeManagerDao treeHandler;
	private final FileServerManagerMainPanel mainPanel;
	
	@Inject
	public FullTreeProvider(
			BasicTreeProvider basicTreeProvider,
			FileServerTreeManagerDao datasourceTreeHandler,
			FileServerManagerMainPanel mainPanel
		){
		
		this.basicTreeProvider = basicTreeProvider;
		this.treeHandler = datasourceTreeHandler;
		this.mainPanel = mainPanel;
	}

	public ManagerHelperTree get() {
		/* build tree */
		final ManagerHelperTree tree = basicTreeProvider.get();
		tree.getStore().enableDtoAwareness(true);
				
		/* dnd */
		UITreeDragDropConfiguration dndConfig = new UITreeDragDropConfiguration();
		dndConfig.addDropTarget(FileServerFolderDto.class);
		tree.enableDragDrop(treeHandler, dndConfig);
		tree.enableClipboardProvider();

		/* set selections */
		tree.setSelectionProvider(mainPanel);
		
		/* double click */
		tree.enableDoubleClickAction();
		
		/* history location */
		tree.setHistoryLocation(FileServerUiModule.FILESERVER_HISTORY_TOKEN);
		
		/* tree menu */
		tree.configureMenuProvider(FileServerUiModule.ADMIN_TREE_MENU_NAME);
		
		return tree;
	}
}
