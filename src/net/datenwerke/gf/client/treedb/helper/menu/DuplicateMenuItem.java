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
 
 
package net.datenwerke.gf.client.treedb.helper.menu;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gxtdto.client.servercommunication.callback.NotamCallback;
import net.datenwerke.security.client.security.dto.WriteDto;
import net.datenwerke.security.client.treedb.dto.decorator.SecuredAbstractNodeDtoDec;
import net.datenwerke.treedb.client.treedb.TreeDbManagerDao;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.client.treedb.locale.TreedbMessages;

public class DuplicateMenuItem extends TreeMenuItem {
	
	public DuplicateMenuItem(final TreeDbManagerDao treeManager){
		super();
		
		setText(TreedbMessages.INSTANCE.duplicateText());
		addMenuSelectionListener(new TreeMenuSelectionEvent() {
			public void menuItemSelected(final UITree tree, final AbstractNodeDto node) {
				treeManager.duplicateNode(node, new NotamCallback<AbstractNodeDto>(TreedbMessages.INSTANCE.duplicated()) {});
			}
		});
	}

	@Override
	public void toBeDisplayed(AbstractNodeDto selectedItem) {
		disable();
	
		/* try to get parent */
		AbstractNodeDto parent = tree.getParentNode(selectedItem);
		if(null != parent && 
				(! (parent instanceof SecuredAbstractNodeDtoDec) || 
				 ! ((SecuredAbstractNodeDtoDec)parent).isAccessRightsLoaded() || 
				 ( 	
				    ((SecuredAbstractNodeDtoDec)parent).hasAccessRight(WriteDto.class) &&
				    ((SecuredAbstractNodeDtoDec)parent).hasInheritedAccessRight(WriteDto.class)
				 )	
				)
		){
			enable();
		}
	}
}
