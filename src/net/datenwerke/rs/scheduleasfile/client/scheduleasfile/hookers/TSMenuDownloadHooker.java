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
 
 
package net.datenwerke.rs.scheduleasfile.client.scheduleasfile.hookers;

import java.util.List;

import net.datenwerke.gf.client.uiutils.ClientDownloadHelper;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.ScheduleAsFileUiModule;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.dto.ExecutedReportFileReferenceDto;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceUIService;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.TsDiskTreeManagerDao;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.hooks.TsFavoriteMenuHook;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.locale.TsFavoriteMessages;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.ui.ItemSelector;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.ui.TsDiskMainComponent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public class TSMenuDownloadHooker implements TsFavoriteMenuHook {

private final static TsFavoriteMessages messages = GWT.create(TsFavoriteMessages.class);
	
	
	private final TsDiskTreeManagerDao treeManagerDao;
	private final TeamSpaceUIService teamSpaceService;
	private final ClientDownloadHelper clientDownloadHelper;
	
	@Inject
	public TSMenuDownloadHooker(
		TsDiskTreeManagerDao treeManagerDao,
		TeamSpaceUIService teamSpaceService, 
		ClientDownloadHelper clientDownloadHelper
		){
	
		/* store objects */
		this.treeManagerDao = treeManagerDao;
		this.teamSpaceService = teamSpaceService;
		this.clientDownloadHelper = clientDownloadHelper;
	}
	
	@Override
	public boolean addContextMenuEntries(Menu menu, final List<AbstractTsDiskNodeDto> items, ItemSelector selector, final TsDiskMainComponent mainComponent) {
		if(null == items || items.isEmpty() || items.size() > 1)
			return false;
		if(! teamSpaceService.isGuest(mainComponent.getCurrentSpace()))
			return false;

		final AbstractTsDiskNodeDto item = items.get(0);
		if(! (item instanceof ExecutedReportFileReferenceDto))
			return false;

		MenuItem downloadItem = new DwMenuItem(BaseMessages.INSTANCE.save(), BaseIcon.DOWNLOAD);
		downloadItem.addSelectionHandler(new SelectionHandler<Item>() {
			
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				String id = String.valueOf(item.getId());
				String url = GWT.getModuleBaseURL() +  ScheduleAsFileUiModule.EXPORT_SERVLET + "?fileId=" + id + "&download=true"; //$NON-NLS-1$
				
				clientDownloadHelper.triggerDownload(url);
			}
		});

		menu.add(downloadItem);
		
		return true;
	}

}
