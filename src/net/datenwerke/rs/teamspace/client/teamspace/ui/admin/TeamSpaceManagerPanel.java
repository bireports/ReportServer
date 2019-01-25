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
 
 
package net.datenwerke.rs.teamspace.client.teamspace.ui.admin;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.RowDoubleClickEvent;
import com.sencha.gxt.widget.core.client.event.RowDoubleClickEvent.RowDoubleClickHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.StoreFilterField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.btn.impl.DwRemoveButton;
import net.datenwerke.gxtdto.client.dialog.error.DetailErrorDialog;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.servercommunication.callback.NotamCallback;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.DwToolBar;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceDao;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceUIService;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceUIService.TeamSpaceOperationSuccessHandler;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.pa.TeamSpaceDtoPA;
import net.datenwerke.rs.teamspace.client.teamspace.locale.TeamSpaceMessages;
import net.datenwerke.rs.teamspace.client.teamspace.ui.EditTeamSpaceDialogCreator;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

public class TeamSpaceManagerPanel extends DwContentPanel {

	private final TeamSpaceDao teamSpaceDao;
	private final TeamSpaceUIService teamSpaceService;
	private final EditTeamSpaceDialogCreator editTeamSpaceDialogCreator;
	private final ToolbarService toolbarService;
	
	private ListStore<TeamSpaceDto> teamSpaceStore;

	private Grid<TeamSpaceDto> grid;



	@Inject
	public TeamSpaceManagerPanel(
		EditTeamSpaceDialogCreator editTeamSpaceDialogCreator,
		TeamSpaceDao teamSpaceDao,
		TeamSpaceUIService teamSpaceService,
		ToolbarService toolbarService
		) {

		this.editTeamSpaceDialogCreator = editTeamSpaceDialogCreator;
		this.teamSpaceDao = teamSpaceDao;
		this.teamSpaceService = teamSpaceService;
		this.toolbarService = toolbarService;
		
		
		init();
	}

	private void init() {
		setHeadingText(TeamSpaceMessages.INSTANCE.adminButton());
		setHeaderIcon(BaseIcon.USER_SECRET);
		
		/* init store */
		initStore();
		
		/* init grid */
		grid = loadGrid();
		
		/* init toolbar */
		DwToolBar toolbar = initToolbar();
		
		/* init wrapper */
		VerticalLayoutContainer wrapper = new VerticalLayoutContainer();
		add(wrapper);
		
		/* add components */
		wrapper.add(toolbar, new VerticalLayoutData(1,-1));
		wrapper.add(grid, new VerticalLayoutData(1,1));
		
		/* load data */
		mask(BaseMessages.INSTANCE.loadingMsg());
		teamSpaceDao.loadAllTeamSpaces(new RsAsyncCallback<ListLoadResult<TeamSpaceDto>>(){
			@Override
			public void onSuccess(ListLoadResult<TeamSpaceDto> result) {
				unmask();
				teamSpaceStore.addAll(result.getData());
			}
			@Override
			public void onFailure(Throwable caught) {
				new DetailErrorDialog(caught).show();
				unmask();
			}
		});
	}

	private void initStore() {
		/* create store */
		teamSpaceStore = new ListStore<TeamSpaceDto>(TeamSpaceDtoPA.INSTANCE.dtoId());
		teamSpaceStore.addSortInfo(new StoreSortInfo<TeamSpaceDto>(TeamSpaceDtoPA.INSTANCE.name(), SortDir.ASC));
	}
	
	
	private DwToolBar initToolbar() {
		DwToolBar toolbar = new DwToolBar();
		
		/* add new */
		DwTextButton addButton = new DwTextButton(TeamSpaceMessages.INSTANCE.createSpaceText(), BaseIcon.GROUP_ADD);
		addButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				teamSpaceService.displayAddSpaceDialog(new TeamSpaceOperationSuccessHandler() {
					@Override
					public void onSuccess(TeamSpaceDto teamSpace) {
						teamSpaceStore.add(teamSpace);
						teamSpaceService.notifyOfAddition(teamSpace);
					}
				});
			}
		});
		toolbar.add(addButton);
		
		/* remove */
		DwRemoveButton removeButton = new DwRemoveButton(){
			@Override
			protected boolean canRemoveSingle() {
				return null != grid.getSelectionModel().getSelectedItem();
			}
			
			@Override
			public String getRemoveConfirmMessage() {
				TeamSpaceDto selected = grid.getSelectionModel().getSelectedItem();
				if(null != selected)
					return TeamSpaceMessages.INSTANCE.deleteTeamSpaceConfirmMessage(selected.getName() + " (" + selected.getId() + ")" );
				throw new IllegalStateException();
			}
			
			@Override
			protected void onRemove() {
				final TeamSpaceDto selected = grid.getSelectionModel().getSelectedItem();
				if(null != selected){
					teamSpaceDao.removeTeamSpace(selected, new NotamCallback<Void>(TeamSpaceMessages.INSTANCE.teamSpaceRemoved()){
						@Override
						public void doOnSuccess(Void result) {
							teamSpaceStore.remove(selected);
							teamSpaceService.notifyOfDeletion(selected);
						}
					});
				}
			}
		};
		removeButton.setProtectAll(true);
		removeButton.setProtectSingleItem(true);
		removeButton.setMenu(null); // no remove all
		
		toolbar.add(removeButton);
		
		/* edit */
		toolbar.add(new SeparatorToolItem());
		DwTextButton editButton = new DwTextButton(BaseMessages.INSTANCE.edit(), BaseIcon.GROUP_EDIT);
		editButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				TeamSpaceDto selectedItem = grid.getSelectionModel().getSelectedItem();
				
				if(null != selectedItem)
					showEditDialog(selectedItem);
			}
		});
		toolbar.add(editButton);
		
		/* go to */
		toolbar.add(new SeparatorToolItem());
		DwTextButton gotoButton = new DwTextButton(BaseMessages.INSTANCE.gotoLabel(), BaseIcon.EXTERNAL_LINK);
		gotoButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				TeamSpaceDto selectedItem = grid.getSelectionModel().getSelectedItem();
				
				if(null != selectedItem){
					teamSpaceService.gotoTeamSpace(selectedItem);
				}
			}
		});
		toolbar.add(gotoButton);
		
		/* filter */
		toolbar.add(new SeparatorToolItem());
		
		/* toolbar */
		StoreFilterField<TeamSpaceDto> textFilter = new StoreFilterField<TeamSpaceDto>(){
			@Override
			protected boolean doSelect(Store<TeamSpaceDto> store, TeamSpaceDto parent, TeamSpaceDto item,
					String filter) {
				if(null == filter)
					return true;
				
				filter = filter.toLowerCase();
				
				return (null != item.getName() && item.getName().toLowerCase().contains(filter)) || 
					   (null != item.getDescription() && item.getDescription().toLowerCase().contains(filter));
			}
			
		   
	    };  
	    textFilter.bind(teamSpaceStore);

	    /* add items to toolbar */
	    toolbarService.addPlainToolbarItem(toolbar, BaseIcon.SEARCH);
	    toolbar.add(textFilter);
		
		
		return toolbar;
	}


	protected void showEditDialog(TeamSpaceDto selectedItem) {
		editTeamSpaceDialogCreator.displayDialog(selectedItem, new TeamSpaceOperationSuccessHandler() {
			@Override
			public void onSuccess(TeamSpaceDto teamSpace) {
				teamSpaceStore.update(teamSpace);
				teamSpaceService.notifyOfUpdate(teamSpace);
			}
		});
	}

	private Grid<TeamSpaceDto> loadGrid() {
		/* create columns */
		List<ColumnConfig<TeamSpaceDto, ?>> columns = new ArrayList<ColumnConfig<TeamSpaceDto, ?>>();
		
		ColumnConfig<TeamSpaceDto, Long> idColumn = new ColumnConfig<TeamSpaceDto, Long>(TeamSpaceDtoPA.INSTANCE.id(), 80, BaseMessages.INSTANCE.id());
		columns.add(idColumn);
		
		ColumnConfig<TeamSpaceDto, String> nameColumn = new ColumnConfig<TeamSpaceDto, String>(TeamSpaceDtoPA.INSTANCE.name(), 150, BaseMessages.INSTANCE.propertyName());
		columns.add(nameColumn);
		
		ColumnConfig<TeamSpaceDto, String> descriptionColumn = new ColumnConfig<TeamSpaceDto, String>(TeamSpaceDtoPA.INSTANCE.description(), 300, BaseMessages.INSTANCE.propertyDescription());
		columns.add(descriptionColumn);
		
		/* create grid */
		final Grid<TeamSpaceDto> grid = new Grid<TeamSpaceDto>(teamSpaceStore, new ColumnModel<TeamSpaceDto>(columns));
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		grid.addRowDoubleClickHandler(new RowDoubleClickHandler() {
			@Override
			public void onRowDoubleClick(RowDoubleClickEvent event) {
				TeamSpaceDto ts = teamSpaceStore.get(event.getRowIndex());
				if(null != ts)
					showEditDialog(ts);
			}
		});
		
		return grid;
	}
}