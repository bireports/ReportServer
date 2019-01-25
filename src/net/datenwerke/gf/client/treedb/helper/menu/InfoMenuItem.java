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

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gxtdto.client.objectinformation.ObjectInfoPanelService;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.client.treedb.locale.TreedbMessages;

import com.google.inject.Inject;

public class InfoMenuItem extends TreeMenuItem {
	
	@Inject
	private static ObjectInfoPanelService objectInfoService;

	public InfoMenuItem(){
		super();
		
		setText(TreedbMessages.INSTANCE.infoMenuLabel());
		
		addMenuSelectionListener(new TreeMenuSelectionEvent() {
			
			public void menuItemSelected(final UITree tree, final AbstractNodeDto node) {
				objectInfoService.displayInformationOn(node);
			}
		});
	}
}