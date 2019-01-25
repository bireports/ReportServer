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
 
 
package net.datenwerke.rs.fileserver.client.fileserver.hookers;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

import net.datenwerke.gf.client.treedb.selection.SingleTreeSelectionField;
import net.datenwerke.gf.client.treedb.selection.hooks.AddSelectionFieldMenuItemHook;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.fileserver.client.fileserver.FileSeverUiService;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFileDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.decorator.FileServerFileDtoDec;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

public class EditFileFromSelectionWidgetHooker implements AddSelectionFieldMenuItemHook {

	private final FileSeverUiService fileService;
	
	@Inject
	public EditFileFromSelectionWidgetHooker(FileSeverUiService fileService) {
		this.fileService = fileService;
	}



	@Override
	public void addMenuEntries(SingleTreeSelectionField field, Menu menu,
			final MenuNodeProvider provider) {
		if(! field.isValidType(FileServerFileDtoDec.class))
			return;

		final MenuItem editItem = new DwMenuItem(BaseMessages.INSTANCE.edit(),BaseIcon.EDIT);
		menu.add(editItem);
		
		editItem.addSelectionHandler(new SelectionHandler<Item>() {
			
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				AbstractNodeDto node = provider.getNode();
				if(node instanceof FileServerFileDto)
					fileService.editFileDirectly((FileServerFileDto) node);
			}
		});
		
		editItem.disable();
		
		menu.addShowHandler(new ShowHandler() {
			@Override
			public void onShow(ShowEvent event) {
				AbstractNodeDto node = provider.getNode();
				if(node instanceof FileServerFileDto)
					editItem.enable();
				else
					editItem.disable();
			}
		});
		
	}


}
