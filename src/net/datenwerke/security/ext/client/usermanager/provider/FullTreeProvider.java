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
 
 
package net.datenwerke.security.ext.client.usermanager.provider;

import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTree;
import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gf.client.treedb.dnd.UITreeDragDropConfiguration;
import net.datenwerke.security.client.usermanager.dto.GroupDto;
import net.datenwerke.security.client.usermanager.dto.OrganisationalUnitDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.ext.client.usermanager.UserManagerTreeManagerDao;
import net.datenwerke.security.ext.client.usermanager.UserManagerUIModule;
import net.datenwerke.security.ext.client.usermanager.ui.UserMainPanel;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * Provides the user manager tree with all goodies.
 *
 */
public class FullTreeProvider implements Provider<UITree>{

	private final BasicTreeProvider basicTreeProvider;
	private final UserManagerTreeManagerDao userManagerTreeHandler;
	private final UserMainPanel mainPanel;

	private ManagerHelperTree managerHelperTree;
	
	@Inject
	public FullTreeProvider(
			BasicTreeProvider basicTreeProvider,
			UserManagerTreeManagerDao userManagerTreeHandler,
			UserMainPanel mainPanel 
		){
		
		this.basicTreeProvider = basicTreeProvider;
		this.userManagerTreeHandler = userManagerTreeHandler;
		this.mainPanel = mainPanel;
	}
	


	private ManagerHelperTree getTree(){
		if(null == managerHelperTree){
			managerHelperTree = basicTreeProvider.get();
		}
		return managerHelperTree;
	}
	
	public UITree get() {
		/* build tree */
		final ManagerHelperTree tree = getTree();
		tree.getStore().enableDtoAwareness(true);
		
		/* dnd */
		UITreeDragDropConfiguration dndConfig = new UITreeDragDropConfiguration();
		dndConfig.addDropTarget(OrganisationalUnitDto.class, GroupDto.class, UserDto.class, OrganisationalUnitDto.class);
		tree.enableDragDrop(userManagerTreeHandler, dndConfig);
		tree.enableClipboardProvider();
		
		/* set selections */
		tree.setSelectionProvider(mainPanel);
		
		/* double click */
		tree.enableDoubleClickAction();
		
		/* history location */
		tree.setHistoryLocation(UserManagerUIModule.USERMANAGER_FAV_HISTORY_TOKEN);
		
		/* tree menu */
		tree.configureMenuProvider(UserManagerUIModule.ADMIN_TREE_MENU_NAME);
		
		return tree;
	}
}
