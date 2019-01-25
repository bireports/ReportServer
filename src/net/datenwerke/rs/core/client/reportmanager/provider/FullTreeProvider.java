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
 
 
package net.datenwerke.rs.core.client.reportmanager.provider;

import java.util.Collection;

import net.datenwerke.gf.client.history.HistoryUiService;
import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTree;
import net.datenwerke.gf.client.treedb.dnd.UITreeDragDropConfiguration;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportmanager.ReportManagerTreeManagerDao;
import net.datenwerke.rs.core.client.reportmanager.ReportManagerUIModule;
import net.datenwerke.rs.core.client.reportmanager.dto.ReportFolderDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.hooks.ReportTypeConfigHook;
import net.datenwerke.rs.core.client.reportmanager.ui.ReportManagerMainPanel;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class FullTreeProvider implements Provider<ManagerHelperTree>{

	private final HookHandlerService hookHandler;
	private final BasicTreeProvider basicTreeProvider;
	private final ReportManagerTreeManagerDao reportManagerTreeHandler;
	private final ReportManagerMainPanel mainPanel;
	private ManagerHelperTree managerHelperTree;
	
	@Inject
	public FullTreeProvider(
			HookHandlerService hookHandler,
			
			BasicTreeProvider basicTreeProvider,
			ReportManagerTreeManagerDao reportManagerTreeHandler,
			ReportManagerMainPanel mainPanel, 
			HistoryUiService historyService 
		){
		
		this.hookHandler = hookHandler;
		this.basicTreeProvider = basicTreeProvider;
		this.reportManagerTreeHandler = reportManagerTreeHandler;
		this.mainPanel = mainPanel;
		
	}
	
	

	private ManagerHelperTree getTree(){
		if(null == managerHelperTree){
			managerHelperTree = basicTreeProvider.get();
		}
		return managerHelperTree;
	}
	
	public ManagerHelperTree get() {
		/* build tree */
		final ManagerHelperTree tree = getTree();
		tree.getStore().enableDtoAwareness(true);
				
		/* dnd */
		UITreeDragDropConfiguration dndConfig = new UITreeDragDropConfiguration();
		dndConfig.addDropTarget(ReportFolderDto.class);
		
		Collection<ReportTypeConfigHook> configs = hookHandler.getHookers(ReportTypeConfigHook.class);
		for(ReportTypeConfigHook config : configs){
			dndConfig.addDropTarget(ReportDto.class, config.getReportVariantClass());
			dndConfig.denyDropCombination(ReportFolderDto.class, config.getReportVariantClass());
			
			for(ReportTypeConfigHook innerConfig : configs){
				if(config != innerConfig)
					dndConfig.denyDropCombination(config.getReportVariantClass(), innerConfig.getReportVariantClass());
			}
		}
		
		tree.enableDragDrop(reportManagerTreeHandler, dndConfig);
		tree.enableClipboardProvider();
		
		/* set selections */
		tree.setSelectionProvider(mainPanel);
		
		/* double click */
		tree.enableDoubleClickAction();
		
		/* history location */
		tree.setHistoryLocation(ReportManagerUIModule.REPORTMANAGER_FAV_HISTORY_TOKEN);
		
		/* tree menu */
		tree.configureMenuProvider(ReportManagerUIModule.ADMIN_TREE_MENU_NAME);
		
		return tree;
	}
}
