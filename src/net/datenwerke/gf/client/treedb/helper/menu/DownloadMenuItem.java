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
import net.datenwerke.gf.client.uiutils.ClientDownloadHelper;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.security.dto.ReadDto;
import net.datenwerke.security.client.treedb.dto.decorator.SecuredAbstractNodeDtoDec;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

public class DownloadMenuItem extends TreeMenuItem {
	
	public static interface DownloadMenuUrlGenerator{

		public String getUrl(AbstractNodeDto node);
		
	}
	
	public DownloadMenuItem(final DownloadMenuUrlGenerator downloadHelper){
		super();
		
		setIcon(BaseIcon.DOWNLOAD);
		setText(BaseMessages.INSTANCE.download());
		addMenuSelectionListener(new TreeMenuSelectionEvent() {
			
			public void menuItemSelected(final UITree tree, final AbstractNodeDto node) {
				if(null == node)
					return;
				String url = downloadHelper.getUrl(node);
				ClientDownloadHelper.triggerDownload(url);
			}
		});
	}
	
	@Override
	public void toBeDisplayed(AbstractNodeDto selectedItem) {
		disable();
		
		if(! (selectedItem instanceof SecuredAbstractNodeDtoDec) || ! ((SecuredAbstractNodeDtoDec)selectedItem).isAccessRightsLoaded()|| ((SecuredAbstractNodeDtoDec)selectedItem).hasAccessRight(ReadDto.class))
			enable();
	}
}
