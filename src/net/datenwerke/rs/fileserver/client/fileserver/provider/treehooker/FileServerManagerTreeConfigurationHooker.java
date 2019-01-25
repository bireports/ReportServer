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
 
 
package net.datenwerke.rs.fileserver.client.fileserver.provider.treehooker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.user.client.Random;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.menu.SeparatorMenuItem;

import net.datenwerke.gf.client.managerhelper.hooks.TreeConfiguratorHook;
import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTree;
import net.datenwerke.gf.client.treedb.helper.menu.DeleteMenuItem;
import net.datenwerke.gf.client.treedb.helper.menu.DownloadMenuItem;
import net.datenwerke.gf.client.treedb.helper.menu.DownloadMenuItem.DownloadMenuUrlGenerator;
import net.datenwerke.gf.client.treedb.helper.menu.DuplicateMenuItem;
import net.datenwerke.gf.client.treedb.helper.menu.InsertMenuItem;
import net.datenwerke.gf.client.treedb.helper.menu.TreeDBUIMenuProvider;
import net.datenwerke.gf.client.treedb.icon.TreeDBUIIconProvider;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.rs.fileserver.client.fileserver.FileServerTreeManagerDao;
import net.datenwerke.rs.fileserver.client.fileserver.FileServerUiModule;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFileDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFolderDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.decorator.FileServerFileDtoDec;
import net.datenwerke.rs.fileserver.client.fileserver.locale.FileServerMessages;
import net.datenwerke.rs.fileserver.client.fileserver.provider.helper.FileIconMapping;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

public class FileServerManagerTreeConfigurationHooker implements
		TreeConfiguratorHook {

	private final FileServerTreeManagerDao treeHandler;
	
	class DownloadHelper implements DownloadMenuUrlGenerator{

		@Override
		public String getUrl(AbstractNodeDto node) {
			int nonce = Random.nextInt();
			String url = GWT.getModuleBaseURL() + FileServerUiModule.FILE_ACCESS_SERVLET + "?nonce=" + nonce + "&id=" + node.getId() + "&download=true"; //$NON-NLS-1$
			if(node instanceof FileServerFolderDto)
				url += "&folder=true";
			return url;
		}
		
	}
	
	@Inject
	public FileServerManagerTreeConfigurationHooker(
		FileServerTreeManagerDao treeHandler	
		){
		
		/* store objects */
		this.treeHandler = treeHandler;
	}
	
	@Override
	public boolean consumes(ManagerHelperTree tree) {
		return FileServerUiModule.class.equals(tree.getGuarantor());
	}

	@Override
	public void configureTreeIcons(TreeDBUIIconProvider iconProvider) {
		iconProvider.addMappings(	
			new FileIconMapping()
		);
	}

	@Override
	public void configureTreeMenu(TreeDBUIMenuProvider menuProvider) {
		/* Folder */
		Menu folderMenu = menuProvider.createOrGetMenuFor(FileServerFolderDto.class);
		MenuItem insertItem = generateInsertMenu();
		folderMenu.add(insertItem);
		folderMenu.add(new DeleteMenuItem(treeHandler));
		folderMenu.add(new SeparatorMenuItem());
		folderMenu.add(new DownloadMenuItem(new DownloadHelper()));
		
		/* File */
		Menu fileMenu = menuProvider.createOrGetMenuFor(FileServerFileDto.class);
		insertItem = generateInsertMenu();
		insertItem.disable();
		fileMenu.add(insertItem);
		fileMenu.add(new DuplicateMenuItem(treeHandler));
		fileMenu.add(new DeleteMenuItem(treeHandler));
		fileMenu.add(new SeparatorMenuItem());
		fileMenu.add(new DownloadMenuItem(new DownloadHelper()));
	}


	private MenuItem generateInsertMenu(){
		Menu insertMenu = new DwMenu();
		insertMenu.add(new InsertMenuItem(new FileServerFolderDto(), FileServerMessages.INSTANCE.folder(), treeHandler, BaseIcon.FOLDER_O));
		insertMenu.add(new InsertMenuItem(new FileServerFileDtoDec(), FileServerMessages.INSTANCE.file(), treeHandler, BaseIcon.FILE_O));
		
		MenuItem insertItem = new DwMenuItem(FileServerMessages.INSTANCE.insert(), BaseIcon.FILE_O);
		insertItem.setSubMenu(insertMenu);
		
		return insertItem;
	}

	@Override
	public void onDoubleClick(AbstractNodeDto selectedItem, DoubleClickEvent event) {
		
	}
	
	@Override
	public void configureFolderTypes(ManagerHelperTree tree) {
		tree.addFolderTypes(FileServerFolderDto.class);
	}
}
