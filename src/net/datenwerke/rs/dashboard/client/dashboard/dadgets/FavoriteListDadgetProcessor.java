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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.dadgets;

import net.datenwerke.gf.client.history.HistoryUiService;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.utils.modelkeyprovider.DtoIdModelKeyProvider;
import net.datenwerke.rs.dashboard.client.dashboard.DashboardDao;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.FavoriteListDadgetDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.FavoriteListDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.FavoriteListEntryDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.decorator.FavoriteListDadgetDtoDec;
import net.datenwerke.rs.dashboard.client.dashboard.hooks.DadgetProcessorHook;
import net.datenwerke.rs.dashboard.client.dashboard.locale.DashboardMessages;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DadgetPanel;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.TsDiskUIService;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent.CellDoubleClickHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;

public class FavoriteListDadgetProcessor implements DadgetProcessorHook {

	private final DashboardDao dao;
	private final HistoryUiService historyService;
	private final TsDiskUIService diskService;
	
	@Inject
	public FavoriteListDadgetProcessor(
		DashboardDao dao,
		HistoryUiService historyService,
		TsDiskUIService diskService
		) {
		
		this.dao = dao;
		this.historyService = historyService;
		this.diskService = diskService;
	}

	@Override
	public BaseIcon getIcon() {
		return BaseIcon.STAR_O;
	}
	
	@Override
	public boolean isRedrawOnMove() {
		return false;
	}


	@Override
	public String getTitle() {
		return DashboardMessages.INSTANCE.favoriteListTitle();
	}

	@Override
	public String getDescription() {
		return DashboardMessages.INSTANCE.favoriteListDescription();
	}

	@Override
	public boolean consumes(DadgetDto dadget) {
		return dadget instanceof FavoriteListDadgetDto;
	}

	@Override
	public DadgetDto instantiateDadget() {
		return new FavoriteListDadgetDtoDec();
	}

	@Override
	public void draw(DadgetDto dadget, DadgetPanel panel) {
		/* configure columns */ 
		ColumnModel<AbstractTsDiskNodeDto> cm = new ColumnModel<AbstractTsDiskNodeDto>(diskService.createGridColumnConfig(AbstractTsDiskNodeDto.class, false));

		/* store */
		final ListStore<AbstractTsDiskNodeDto> store = new ListStore<AbstractTsDiskNodeDto>(new DtoIdModelKeyProvider());
		dao.loadFavorites(new RsAsyncCallback<FavoriteListDto>(){
			@Override
			public void onSuccess(FavoriteListDto result) {
				if(null != result)
					for(FavoriteListEntryDto entry : result.getReferenceEntries())
						if(null != entry.getReferenceEntry())
							store.add(entry.getReferenceEntry());
			}
		});
		
		/* create grid */
		final Grid<AbstractTsDiskNodeDto> grid = new Grid<AbstractTsDiskNodeDto>(store, cm);
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.getView().setAutoExpandColumn(cm.getColumn(1));
		grid.getView().setShowDirtyCells(false);
		
		grid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			
			@Override
			public void onCellClick(CellDoubleClickEvent event) {
				final AbstractTsDiskNodeDto node = store.get(event.getRowIndex());
				if(null != node){
					historyService.jumpTo(node);
				}
			}
		});
		
		Menu menu = new DwMenu();
		grid.setContextMenu(menu);
		
		MenuItem removeItem = new DwMenuItem(BaseMessages.INSTANCE.remove(), BaseIcon.DELETE);
		menu.add(removeItem);
		removeItem.addSelectionHandler(new SelectionHandler<Item>() {
			
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				AbstractTsDiskNodeDto selectedItem = grid.getSelectionModel().getSelectedItem();
				if(null == selectedItem)
					return;
				
				store.remove(selectedItem);
				dao.removeFromFavorites(selectedItem, new RsAsyncCallback<Void>());
			}
		});
		
		panel.setHeadingText(getTitle());
		panel.setHeaderIcon(BaseIcon.STAR);
		panel.add(grid);
	}

	@Override
	public void displayConfigDialog(DadgetDto dadget,
			DadgetConfigureCallback dadgetConfigureCallback) {
	}

	@Override
	public Widget getAdminConfigDialog(DadgetDto dadget, SimpleForm wrappingForm) {
		return null;
	}
	
	@Override
	public boolean supportsDadgetLibrary() {
		return false;
	}

	@Override
	public boolean readyToDisplayParameters(DadgetPanel dadgetPanel) {
		return false;
	}

	
	@Override
	public boolean hasConfigDialog() {
		return false;
	}

	@Override
	public void addTools(DadgetPanel dadgetPanel) {
		// TODO Auto-generated method stub
		
	}
}
