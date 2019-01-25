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
 
 
package net.datenwerke.rs.core.client.reportmanager.hookers;

import java.util.List;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gf.client.treedb.simpleform.SFFCGenericTreeNode;
import net.datenwerke.gxtdto.client.baseex.widget.layout.DwNorthSouthContainer;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.DwToolBar;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.gxtdto.client.utils.modelkeyprovider.DtoIdModelKeyProvider;
import net.datenwerke.rs.core.client.reportmanager.ReportManagerTreeLoaderDao;
import net.datenwerke.rs.core.client.reportmanager.ReportManagerUIService;
import net.datenwerke.rs.core.client.reportmanager.dto.ReportFolderDto;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportVariantDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.decorator.ReportDtoDec;
import net.datenwerke.rs.core.client.reportmanager.helper.reportselector.ReportSelectionDialog;
import net.datenwerke.rs.core.client.reportmanager.hooks.ReportSelectionRepositoryProviderHookImpl;
import net.datenwerke.rs.core.client.reportmanager.locale.ReportmanagerMessages;
import net.datenwerke.rs.core.client.reportmanager.provider.annotations.ReportManagerTreeFolders;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.widget.core.client.container.NorthSouthContainer;
import com.sencha.gxt.widget.core.client.event.BeforeShowContextMenuEvent;
import com.sencha.gxt.widget.core.client.event.BeforeShowContextMenuEvent.BeforeShowContextMenuHandler;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent.CellDoubleClickHandler;
import com.sencha.gxt.widget.core.client.form.StoreFilterField;
import com.sencha.gxt.widget.core.client.form.ValueBaseField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class ReportCatalogRepositoryProvider extends ReportSelectionRepositoryProviderHookImpl {

	public interface Config extends ReportSelectionDialog.RepositoryProviderConfig{
		public boolean includeVariants();

		boolean showCatalog();
	}
	
	private final ReportManagerUIService reportService;
	private final ReportManagerTreeLoaderDao treeLoaderDao;
	private final ToolbarService toolbarService;
	private Provider<UITree> reportFolderTree;
	
	
	@Inject
	public ReportCatalogRepositoryProvider(
			ReportManagerUIService reportService,
			ReportManagerTreeLoaderDao treeLoaderDao,
			ToolbarService toolbarService,
			@ReportManagerTreeFolders Provider<UITree> reportFolderTree) {
		this.reportService = reportService;
		this.treeLoaderDao = treeLoaderDao;
		this.toolbarService = toolbarService;
		this.reportFolderTree = reportFolderTree;
	}

	@Override
	public void addCards(final ReportSelectionDialog dialog, ReportSelectionDialog.RepositoryProviderConfig... configs){
		Config conf = getConfig(configs);
		if(null != conf && ! conf.showCatalog())
			return;
		
		addBasicCard(dialog,null != conf && conf.includeVariants());
	}
	
	protected <C extends ReportSelectionDialog.RepositoryProviderConfig> C getConfig(ReportSelectionDialog.RepositoryProviderConfig... configs){
		if(null == configs)
			return null;
		for(ReportSelectionDialog.RepositoryProviderConfig c : configs){
			if(c instanceof Config)
				return (C) c;
		}
		return null;
	}

	private void addBasicCard(final ReportSelectionDialog dialog, final boolean showVariants) {
		/* create store */
		final ListStore<ReportDto> store = new ListStore<ReportDto>(new DtoIdModelKeyProvider());
		
		/* create grid */
		List<ColumnConfig<ReportDto,?>> columns = reportService.getColumnConfigForReportGrid(showVariants, true);
		
		final Grid<ReportDto> grid = new Grid<ReportDto>(store, new ColumnModel<ReportDto>(columns));
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		/* load data */
		grid.mask(BaseMessages.INSTANCE.loadingMsg());
		treeLoaderDao.getReportsInCatalog(showVariants, new RsAsyncCallback<List<ReportDto>>(){
			@Override
			public void onSuccess(List<ReportDto> result) {
				store.addAll(result);
				grid.unmask();
			}
		});
		
		grid.setContextMenu(new DwMenu());
		grid.addBeforeShowContextMenuHandler(new BeforeShowContextMenuHandler() {
			@Override
			public void onBeforeShowContextMenu(BeforeShowContextMenuEvent event) {
				ReportDtoDec dto = (ReportDtoDec) grid.getSelectionModel().getSelectedItem();
				if(null == dto){
					event.setCancelled(true);
					return;
				}
			
				Menu menu =  dialog.getContextMenuFor(dto, ReportCatalogRepositoryProvider.this, event);
				if(null == menu){
					event.setCancelled(true);
					return;
				}
				grid.setContextMenu(menu);
			}
		});
		
		/* show context menue on doubleclick */
		grid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			@Override
			public void onCellClick(CellDoubleClickEvent event) {
				ReportDtoDec dto = (ReportDtoDec) grid.getStore().get(event.getRowIndex());
                if(null != dto)
                	dialog.handleDoubleClick(dto, ReportCatalogRepositoryProvider.this, event.getEvent());
            }
        });
		
		/* create wrapper */
		NorthSouthContainer wrapper = new DwNorthSouthContainer();
		wrapper.add(grid);
		
		ToolBar tb = new DwToolBar();
		wrapper.setNorthWidget(tb);
		
		final StoreFilterField<ReportDto> textFilter = new StoreFilterField<ReportDto>(){
			
			@Override
			protected boolean doSelect(Store<ReportDto> store,
					ReportDto parent, ReportDto item, String filter) {
				String title = item.getName();
				if(null != title && title.toLowerCase().contains(filter.toLowerCase()))
					return true;
				
				if(item instanceof ReportVariantDto){
					title = item.getParentReportName();
					if(null != title && title.toLowerCase().contains(filter.toLowerCase()))
						return true;
				}
				
				title = String.valueOf(item.getId());
				if(null != title && title.toLowerCase().contains(filter.toLowerCase()))
					return true;
				
				return false;
			}
		   
	    };  
	    textFilter.bind(store);
	    
	    /* folder selector */
	    ValueBaseField<ReportFolderDto> folderSelector = (ValueBaseField<ReportFolderDto>) SimpleForm.createFormlessField(ReportFolderDto.class, new SFFCGenericTreeNode() { 
				public UITree getTreeForPopup() {
					return reportFolderTree.get();
				}
		});
	    folderSelector.addValueChangeHandler(new ValueChangeHandler<ReportFolderDto>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<ReportFolderDto> event) {
				textFilter.clear();
				ReportFolderDto folder = event.getValue();
				loadIn(folder, grid, store, showVariants);
			}
		});

	    /* add items to toolbar */
	    toolbarService.addPlainToolbarItem(tb, BaseIcon.SEARCH);
	    tb.add(textFilter);
	    toolbarService.addPlainToolbarItem(tb, BaseIcon.FOLDER_OPEN_O);
	    tb.add(folderSelector);
	    tb.add(new FillToolItem());
		
	    /* create card */
		dialog.addCard(
			ReportmanagerMessages.INSTANCE.catalog(), 
			BaseIcon.HDD_O, 
			wrapper	
		);
	}

	protected void loadIn(ReportFolderDto folder, final Grid<ReportDto> grid, final ListStore<ReportDto> store, boolean showVariants) {
		grid.mask(BaseMessages.INSTANCE.loadingMsg());
		treeLoaderDao.getReportsInCatalog(folder, showVariants, new RsAsyncCallback<List<ReportDto>>(){
			@Override
			public void onSuccess(List<ReportDto> result) {
				store.removeFilters();
				store.clear();
				store.addAll(result);
				grid.unmask();
			}
		});
	}

}
