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
 
 
package net.datenwerke.gf.client.treedb.helper.menu;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.sencha.gxt.widget.core.client.menu.Item;


public class TreeMenuItem extends DwMenuItem {

	final protected List<TreeMenuSelectionEvent> eventListeners = new ArrayList<TreeMenuSelectionEvent>();
	protected UITree tree;

	public TreeMenuItem(){
		super();
		
		/* add selection listener */
		addSelectionHandler(new SelectionHandler<Item>() {
			
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				/* find node */
				if(null == tree)
					return;
				
				//GenericNodeDTO node = tree.getSelectionModel().getSelectedItem();
				AbstractNodeDto node = tree.getContextMenuNode();
				
				/* inform listeners */
				if(null != node)
					for(TreeMenuSelectionEvent listener : eventListeners)
						listener.menuItemSelected(tree, node);
			}
		});
	}
	
	/**
	 * Adds a listener to this menu item
	 * @param treeMenuSelectionEvent
	 */
	public void addMenuSelectionListener(
			TreeMenuSelectionEvent listener) {
		eventListeners.add(listener);
	}

	public void setTree(UITree tree) {
		this.tree = tree;
	}
	

	public void toBeDisplayed(AbstractNodeDto node) {
	}

}
