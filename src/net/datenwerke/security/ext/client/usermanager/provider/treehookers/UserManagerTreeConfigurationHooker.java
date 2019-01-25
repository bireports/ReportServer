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
 
 
package net.datenwerke.security.ext.client.usermanager.provider.treehookers;

import net.datenwerke.gf.client.managerhelper.hooks.TreeConfiguratorHook;
import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTree;
import net.datenwerke.gf.client.treedb.helper.menu.DeleteMenuItem;
import net.datenwerke.gf.client.treedb.helper.menu.InsertMenuItem;
import net.datenwerke.gf.client.treedb.helper.menu.TreeDBUIMenuProvider;
import net.datenwerke.gf.client.treedb.icon.IconMapping;
import net.datenwerke.gf.client.treedb.icon.TreeDBUIIconProvider;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.usermanager.dto.GroupDto;
import net.datenwerke.security.client.usermanager.dto.OrganisationalUnitDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.client.usermanager.dto.decorator.UserDtoDec;
import net.datenwerke.security.ext.client.usermanager.UserManagerTreeManagerDao;
import net.datenwerke.security.ext.client.usermanager.UserManagerUIModule;
import net.datenwerke.security.ext.client.usermanager.locale.UsermanagerMessages;
import net.datenwerke.security.ext.client.usermanager.utils.UserIconMapping;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.menu.Menu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public class UserManagerTreeConfigurationHooker implements
		TreeConfiguratorHook {

	final private UserManagerTreeManagerDao treeHandler;
	
	@Inject
	public UserManagerTreeConfigurationHooker(
		UserManagerTreeManagerDao treeHandler	
		){
		
		/* store objects */
		this.treeHandler = treeHandler;
	}
	
	@Override
	public boolean consumes(ManagerHelperTree tree) {
		return UserManagerUIModule.class.equals(tree.getGuarantor());
	}

	@Override
	public void configureTreeIcons(TreeDBUIIconProvider iconProvider) {
		iconProvider.addMappings(
				new UserIconMapping(),
				new IconMapping(GroupDto.class, BaseIcon.GROUP)
		);
	}

	@Override
	public void configureTreeMenu(TreeDBUIMenuProvider menuProvider) {
		/* user */
		Menu userMenu = menuProvider.createOrGetMenuFor(UserDto.class);
		MenuItem inserItem = generateInsertMenu();
		inserItem.disable();
		userMenu.add(inserItem);
		userMenu.add(new DeleteMenuItem(treeHandler));
		
		/* role */
		Menu groupMenu = menuProvider.createOrGetMenuFor(GroupDto.class);
		inserItem = generateInsertMenu();
		inserItem.disable();
		groupMenu.add(inserItem);
		groupMenu.add(new DeleteMenuItem(treeHandler));
		
		/* OU */
		Menu ouMenu = menuProvider.createOrGetMenuFor(OrganisationalUnitDto.class);
		inserItem = generateInsertMenu();
		ouMenu.add(inserItem);
		ouMenu.add(new DeleteMenuItem(treeHandler));
	}

	private MenuItem generateInsertMenu(){
		Menu insertMenu = new DwMenu();
		insertMenu.add(new InsertMenuItem(new OrganisationalUnitDto(), UsermanagerMessages.INSTANCE.ou(), treeHandler, BaseIcon.FOLDER_USER));
		insertMenu.add(new InsertMenuItem(new GroupDto(), UsermanagerMessages.INSTANCE.role(), treeHandler, BaseIcon.GROUP));
		insertMenu.add(new InsertMenuItem(new UserDtoDec(), UsermanagerMessages.INSTANCE.user(), treeHandler, BaseIcon.USER));
		
		MenuItem insertItem = new DwMenuItem(BaseMessages.INSTANCE.insert(), BaseIcon.FILE_O);
		insertItem.setSubMenu(insertMenu);
		
		return insertItem;
	}

	@Override
	public void onDoubleClick(AbstractNodeDto selectedItem, DoubleClickEvent event) {
		
	}

	@Override
	public void configureFolderTypes(ManagerHelperTree tree) {
		tree.addFolderTypes(OrganisationalUnitDto.class);
	}
	
}
