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
 
 
package net.datenwerke.rs.core.client.datasourcemanager.provider.treehooker;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.menu.SeparatorMenuItem;

import net.datenwerke.gf.client.managerhelper.hooks.TreeConfiguratorHook;
import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTree;
import net.datenwerke.gf.client.treedb.helper.menu.AvailabilityCallback;
import net.datenwerke.gf.client.treedb.helper.menu.DeleteMenuItem;
import net.datenwerke.gf.client.treedb.helper.menu.DuplicateMenuItem;
import net.datenwerke.gf.client.treedb.helper.menu.InsertMenuItem;
import net.datenwerke.gf.client.treedb.helper.menu.ReloadMenuItem;
import net.datenwerke.gf.client.treedb.helper.menu.TreeDBUIMenuProvider;
import net.datenwerke.gf.client.treedb.icon.IconMapping;
import net.datenwerke.gf.client.treedb.icon.TreeDBUIIconProvider;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.datasourcemanager.DatasourceTreeManagerDao;
import net.datenwerke.rs.core.client.datasourcemanager.DatasourceUIModule;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceFolderDto;
import net.datenwerke.rs.core.client.datasourcemanager.hooks.DatasourceDefinitionConfigProviderHook;
import net.datenwerke.rs.core.client.datasourcemanager.locale.DatasourcesMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

public class DatasourceManagerTreeConfigurationHooker implements
		TreeConfiguratorHook {

	private final HookHandlerService hookHandler;
	private final DatasourceTreeManagerDao treeHandler;
	
	@Inject
	public DatasourceManagerTreeConfigurationHooker(
		HookHandlerService hookHandler,
		DatasourceTreeManagerDao treeHandler	
		){
		
		/* store objects */
		this.hookHandler = hookHandler;
		this.treeHandler = treeHandler;
	}
	
	@Override
	public boolean consumes(ManagerHelperTree tree) {
		return DatasourceUIModule.class.equals(tree.getGuarantor());
	}

	@Override
	public void configureTreeIcons(TreeDBUIIconProvider iconProvider) {
		for(DatasourceDefinitionConfigProviderHook config : hookHandler.getHookers(DatasourceDefinitionConfigProviderHook.class)){
			iconProvider.addMappings(
				new IconMapping(config.getDatasourceClass(),  config.getDatasourceIcon())
			);
		}
	}

	@Override
	public void configureTreeMenu(TreeDBUIMenuProvider menuProvider) {
		/* Folder */
		Menu folderMenu = menuProvider.createOrGetMenuFor(DatasourceFolderDto.class);
		MenuItem insertItem = generateInsertMenu();
		folderMenu.add(insertItem);
		folderMenu.add(new DeleteMenuItem(treeHandler));
		folderMenu.add(new SeparatorMenuItem());
		folderMenu.add(new ReloadMenuItem());
		
		/* DSDDB */
		for(DatasourceDefinitionConfigProviderHook config : hookHandler.getHookers(DatasourceDefinitionConfigProviderHook.class)){
			Menu dsMenu = menuProvider.createOrGetMenuFor(config.getDatasourceClass());
			insertItem = generateInsertMenu();
			insertItem.disable();
			dsMenu.add(insertItem);
			dsMenu.add(new DuplicateMenuItem(treeHandler));
			dsMenu.add(new DeleteMenuItem(treeHandler));
		}
	}

	private MenuItem generateInsertMenu(){
		Menu insertMenu = new DwMenu();
		InsertMenuItem folderInsert = new InsertMenuItem(new DatasourceFolderDto(), DatasourcesMessages.INSTANCE.folder(), treeHandler);
		folderInsert.setIcon(BaseIcon.FOLDER_O);
		insertMenu.add(folderInsert);
		
		for(final DatasourceDefinitionConfigProviderHook config : hookHandler.getHookers(DatasourceDefinitionConfigProviderHook.class)){
			InsertMenuItem item = new InsertMenuItem(config.instantiateDatasource(), config.getDatasourceName(), treeHandler);
			item.setIcon(config.getDatasourceIcon());
			insertMenu.add(item);
			
			item.setAvailableCallback(new AvailabilityCallback() {
				@Override
				public boolean isAvailable() {
					return config.isAvailable();
				}
			});
			
		}
		
		MenuItem insertItem = new DwMenuItem(DatasourcesMessages.INSTANCE.insert(), BaseIcon.FILE_O);
		insertItem.setSubMenu(insertMenu);
		
		return insertItem;
	}

	@Override
	public void onDoubleClick(AbstractNodeDto selectedItem, DoubleClickEvent event) {
		// ignore double clicks
	}
	
	@Override
	public void configureFolderTypes(ManagerHelperTree tree) {
		tree.addFolderTypes(DatasourceFolderDto.class);
	}
}
