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
 
 
package net.datenwerke.rs.core.client.reportvariants.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.NorthSouthContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent;
import com.sencha.gxt.widget.core.client.event.CellDoubleClickEvent.CellDoubleClickHandler;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent;
import com.sencha.gxt.widget.core.client.event.CompleteEditEvent.CompleteEditHandler;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.form.StoreFilterField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
import com.sencha.gxt.widget.core.client.grid.editing.GridEditing;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.info.Info;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.menu.SeparatorMenuItem;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.btn.impl.DwRemoveButton;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwConfirmMessageBox;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCTeamSpace;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.objectinformation.ObjectInfoPanelService;
import net.datenwerke.gxtdto.client.servercommunication.callback.NotamCallback;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.DwToolBar;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.gxtdto.client.utils.modelkeyprovider.DtoIdModelKeyProvider;
import net.datenwerke.rs.core.client.reportexecutor.ReportExecutorUIService;
import net.datenwerke.rs.core.client.reportmanager.ReportManagerTreeLoaderDao;
import net.datenwerke.rs.core.client.reportmanager.ReportManagerTreeManagerDao;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.ReportVariantDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.pa.ReportDtoPA;
import net.datenwerke.rs.core.client.reportmanager.locale.ReportmanagerMessages;
import net.datenwerke.rs.core.client.reportvariants.locale.ReportVariantsMessages;
import net.datenwerke.rs.reportdoc.client.ReportDocumentationUiService;
import net.datenwerke.rs.reportdoc.client.locale.ReportDocumentationMessages;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.TsDiskDao;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.locale.TsFavoriteMessages;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.client.treedb.locale.TreedbMessages;

public class ReportVariantsView extends MainPanelView {

	public static final String VIEW_ID = "ReportVariantsView";
	
	private final ReportManagerTreeLoaderDao treeLoader;
	private final ReportManagerTreeManagerDao treeManager;
	private final ToolbarService toolbarService;
	private final ReportExecutorUIService reportExecutorService;
	private final ReportDocumentationUiService reportDocService;
	private final TsDiskDao diskDao;
	
	private final ObjectInfoPanelService objectInfoService;
	
	private ListStore<ReportDto> store;
	private Grid<ReportDto> grid;
	
	@Inject
	public ReportVariantsView(
		ReportManagerTreeLoaderDao treeLoader,
		ReportManagerTreeManagerDao treeManager,
		ToolbarService toolbarService,
		ReportExecutorUIService reportExecutorService,
		ReportDocumentationUiService reportDocService,
		TsDiskDao diskDao,
		ObjectInfoPanelService objectInfoService
		){
		this.treeLoader = treeLoader;
		this.treeManager = treeManager;
		this.toolbarService = toolbarService;
		this.reportExecutorService = reportExecutorService;
		this.reportDocService = reportDocService;
		this.diskDao = diskDao;
		this.objectInfoService = objectInfoService;
	}
	
	@Override
	public String getViewId() {
		return VIEW_ID;
	}
	
	@Override
	public boolean isSticky() {
		return true;
	}
	
	@Override
	public ImageResource getIcon() {
		return BaseIcon.REPORT.toImageResource();
	}
	
	@Override
	public String getComponentHeader() {
		return ReportVariantsMessages.INSTANCE.header();
	}

	@Override
	public Widget getViewComponent() {
		NorthSouthContainer nsContainer = new NorthSouthContainer();
		
		/* prepare store */
		store = new ListStore<ReportDto>(new DtoIdModelKeyProvider());
		store.setAutoCommit(true);
		store.addSortInfo(new StoreSortInfo<ReportDto>(ReportDtoPA.INSTANCE.name(), SortDir.ASC));
		
		mask(BaseMessages.INSTANCE.loadingMsg());
		treeLoader.getChildren(getSelectedNode(), false, new RsAsyncCallback<List<AbstractNodeDto>>(){
			@Override
			public void onSuccess(List<AbstractNodeDto> result) {
				unmask();
				if(null != result){
					for(AbstractNodeDto node : result)
						if(node instanceof ReportVariantDto)
							store.add((ReportDto) node);
				}
			}
		});
		
		
		/* create grid */
		createGrid();
		nsContainer.setWidget(grid);
		
		/* create toolbar */
		ToolBar toolbar = createToolbar();
		nsContainer.setNorthWidget(toolbar);
		
		DwContentPanel main = new DwContentPanel();
		main.setLightHeader();
		main.setHeadingText(ReportVariantsMessages.INSTANCE.header());
		main.add(nsContainer);
		main.setInfoText(ReportVariantsMessages.INSTANCE.description());
		
		VerticalLayoutContainer wrapper = new VerticalLayoutContainer();
		wrapper.add(main, new VerticalLayoutData(1,1, new Margins(10)));
		
		return wrapper;
	}
	
	private ToolBar createToolbar() {
		ToolBar toolbar = new DwToolBar();

		/* filter */
		StoreFilterField<ReportDto> textFilter = new StoreFilterField<ReportDto>(){
			@Override
			protected boolean doSelect(Store<ReportDto> store, ReportDto parent, ReportDto item,
					String filter) {
				if(null == filter)
					return true;
				
				filter = filter.toLowerCase();
				
				return (null != item.getName() && item.getName().toLowerCase().contains(filter)) || 
				  	 (null != item.getId() && String.valueOf(item.getId()).startsWith(filter)) || 
					   (null != item.getKey() && item.getKey().toLowerCase().contains(filter)) || 
					   (null != item.getDescription() && item.getDescription().toLowerCase().contains(filter));
			}
			
		   
	    };  
	    textFilter.bind(store);

	    /* add items to toolbar */
	    toolbarService.addPlainToolbarItem(toolbar, BaseIcon.SEARCH);
	    toolbar.add(textFilter);
	    
		toolbar.add(new SeparatorToolItem());
		
		/* open */
		DwTextButton execBtn = new DwTextButton(ReportmanagerMessages.INSTANCE.execute(), BaseIcon.EXECUTE);
		toolbar.add(execBtn);
		execBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if(null != grid.getSelectionModel().getSelectedItem())
					reportExecutorService.executeReport(grid.getSelectionModel().getSelectedItem());
			}
		});
		
		/* documentation */
		DwTextButton showDocumentationBtn = new DwTextButton(ReportDocumentationMessages.INSTANCE.berichtsdokuHelpText(), BaseIcon.BOOK);
		toolbar.add(showDocumentationBtn);
		showDocumentationBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if(null != grid.getSelectionModel().getSelectedItem())
					reportDocService.openDocumentationForopen(grid.getSelectionModel().getSelectedItem());
			}
		});
		
		toolbar.add(new SeparatorToolItem());
		
		DwTextButton importTsBtn = new DwTextButton(TsFavoriteMessages.INSTANCE.importVariantIntoTeamSpaceLabel(), BaseIcon.GROUP);
		importTsBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				if(null != grid.getSelectionModel().getSelectedItem())
					importVariantIntoTeamSpace(grid.getSelectionModel().getSelectedItem());
			}
		});
		toolbar.add(importTsBtn);
		
		
		toolbar.add(new FillToolItem());
		
		DwRemoveButton rmvBtn = new DwRemoveButton(){
			@Override
			protected void onRemove() {
				doDeleteSelectedVariant();
			}
			@Override
			protected boolean canRemoveSingle() {
				return grid.getSelectionModel().getSelectedItem() != null;
			}
			@Override
			public String getRemoveConfirmMessage() {
				ReportDto selected = grid.getSelectionModel().getSelectedItem();
				if(null != selected)
					return ReportmanagerMessages.INSTANCE.deleteVariantConfirmMessage(selected.getName() + " (" + selected.getId() + ")" );
				throw new IllegalStateException();
			}
		};
		rmvBtn.disableRemoveAll();
		rmvBtn.setConfirmSingleItem(true);
		rmvBtn.setProtectSingleItem(true);
		
		toolbar.add(rmvBtn);
		
		
		return toolbar;
	}
	
	private void doDeleteSelectedVariant() {
		final ReportDto selected = grid.getSelectionModel().getSelectedItem();
		
		ReportVariantsView.this.mask(BaseMessages.INSTANCE.storingMsg());
		treeManager.deleteNodeAndAskForMoreForce(selected, new RsAsyncCallback<Boolean>(){
			@Override
			public void onSuccess(Boolean result) {
				ReportVariantsView.this.unmask();
				if(Boolean.TRUE.equals(result)){
					store.remove(selected);
					Info.display(BaseMessages.INSTANCE.changesApplied(), TreedbMessages.INSTANCE.deleted());
				}
			}
			@Override
			public void onFailure(Throwable caught) {
				super.onFailure(caught);
				ReportVariantsView.this.unmask();
			}
		});
	}

	protected void importVariantIntoTeamSpace(final ReportDto report) {
		final DwWindow window = DwWindow.newAutoSizeDialog(340);
		window.setHeadingText(TsFavoriteMessages.INSTANCE.importVariantHookName());
		window.setModal(true);
		window.setWidth(340);
		
		final SimpleForm form = SimpleForm.getInlineInstance();
		
		final String teamSpaceKey = form.addField(
				TeamSpaceDto.class, TsFavoriteMessages.INSTANCE.selectTeamspaceLabel(),
				new SFFCTeamSpace(){
					@Override
					public boolean isMulti() {
						return false;
					}

					@Override
					public boolean isLoadAll() {
						return true;
					}
				});
		
		final String duplicateVariant = form.addField(Boolean.class, TsFavoriteMessages.INSTANCE.duplicateLabel());
				
		form.loadFields();
		
		window.add(form, new MarginData(10));
		
		DwTextButton submit = new DwTextButton(BaseMessages.INSTANCE.submit());
		window.addButton(submit);
		submit.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				TeamSpaceDto teamSpace = (TeamSpaceDto) form.getValue(teamSpaceKey);
				if(null == teamSpace)
					return;
				
				final boolean duplicate = (Boolean) form.getValue(duplicateVariant);
				
				window.mask(BaseMessages.INSTANCE.storingMsg());
				diskDao.importReport(teamSpace, null, report, duplicate, false, new RsAsyncCallback<List<TsDiskReportReferenceDto>>(){
					@Override
					public void onSuccess(List<TsDiskReportReferenceDto> result) {
						window.hide();
						if(duplicate && ! result.isEmpty())
							store.add(result.get(0).getReport());
					}
					@Override
					public void onFailure(Throwable caught) {
						window.unmask();
					}
				});
			}
		});
		
		window.show();
	}

	private void createGrid() {
		/* configure columns */
		List<ColumnConfig<ReportDto,?>> columns = new ArrayList<ColumnConfig<ReportDto,?>>();
		
		/* id column */
		ColumnConfig<ReportDto,Long> idConfig = new ColumnConfig<ReportDto,Long>(ReportDtoPA.INSTANCE.id(), 80, BaseMessages.INSTANCE.id()); 
		idConfig.setMenuDisabled(true);
		
		columns.add(idConfig);
		
		/* name column */
		ColumnConfig<ReportDto,String> nameConfig = new ColumnConfig<ReportDto,String>(ReportDtoPA.INSTANCE.name(), 200, BaseMessages.INSTANCE.name()); 
		nameConfig.setMenuDisabled(true);
		
		columns.add(nameConfig);
		
		/* key column */
		ColumnConfig<ReportDto,String> keyConfig = new ColumnConfig<ReportDto,String>(ReportDtoPA.INSTANCE.key(), 150, BaseMessages.INSTANCE.key()); 
		keyConfig.setMenuDisabled(true);
		
		columns.add(keyConfig);
		
		/* description column */
		ColumnConfig<ReportDto,String> descConfig = new ColumnConfig<ReportDto,String>(ReportDtoPA.INSTANCE.description(), 200, BaseMessages.INSTANCE.description()); 
		descConfig.setMenuDisabled(true);
		columns.add(descConfig);
		
		Menu contextMenu = generateContextMenu();
		
		/* create grid */
		grid = new Grid<ReportDto>(store, new ColumnModel<ReportDto>(columns));
		grid.getView().setAutoExpandColumn(descConfig);
		grid.setSelectionModel(new GridSelectionModel<ReportDto>());
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.getView().setShowDirtyCells(true);
		grid.setContextMenu(contextMenu);
		
		/* double click handler */
		grid.addCellDoubleClickHandler(new CellDoubleClickHandler() {
			@Override
			public void onCellClick(CellDoubleClickEvent event) {
				ReportDto reportDto = store.get(event.getRowIndex());
				if(null != reportDto)
					reportExecutorService.executeReport(reportDto);
			}
		});
		
		GridEditing<ReportDto> editing = new GridInlineEditing<ReportDto>(grid);
		
		editing.addEditor(nameConfig, new TextField());
		editing.addEditor(keyConfig, new TextField());
		editing.addEditor(descConfig, new TextField());
		
		editing.addCompleteEditHandler(new CompleteEditHandler<ReportDto>() {
			@Override
			public void onCompleteEdit(CompleteEditEvent<ReportDto> event) {
				ReportDto report = store.get(event.getEditCell().getRow());
				if(report.isNameModified() || report.isKeyModified() || report.isDescriptionModified()){
					mask(BaseMessages.INSTANCE.storingMsg());
					treeManager.updateNode(report, new NotamCallback<AbstractNodeDto>(BaseMessages.INSTANCE.changesApplied()){
						public void doOnSuccess(AbstractNodeDto result) {
							unmask();
							store.update((ReportDto)result);
						};
						public void doOnFailure(Throwable caught) {
							super.onFailure(caught);
							unmask();
						};
					});
				}
			}
		});
	}

	private Menu generateContextMenu() {
		Menu contextMenu = new Menu();
		
		contextMenu.add(generateExecuteReportItem());
		contextMenu.add(new SeparatorMenuItem());
		contextMenu.add(generateDeleteReportMenuItem());
		contextMenu.add(new SeparatorMenuItem());
		contextMenu.add(generateInfoMenuItem());
		contextMenu.add(generateImportIntoTsMenuItem());
		
		return contextMenu;
	}
	
	private MenuItem generateDeleteReportMenuItem() {
		MenuItem deleteReportItem = new MenuItem();
		deleteReportItem.setText(BaseMessages.INSTANCE.remove());
		deleteReportItem.setIcon(BaseIcon.DELETE.toImageResource());
		deleteReportItem.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				ReportDto node = grid.getSelectionModel().getSelectedItem();
				/* confirm delete */
				ConfirmMessageBox cmb = new DwConfirmMessageBox(BaseMessages.INSTANCE.confirmDeleteTitle(),
						BaseMessages.INSTANCE.confirmDeleteMsg(node.toDisplayTitle()));

				cmb.addDialogHideHandler(new DialogHideHandler() {

					@Override
					public void onDialogHide(DialogHideEvent event) {
						if (event.getHideButton() == PredefinedButton.YES) {
							/* delete */
							doDeleteSelectedVariant();
						}
					}
				});

				cmb.show();
			}
		});

		return deleteReportItem;
	}
	
	private MenuItem generateImportIntoTsMenuItem() {
		MenuItem importReportItem = new MenuItem();
		importReportItem.setText(TsFavoriteMessages.INSTANCE.importVariantIntoTeamSpaceLabel());
		importReportItem.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				ReportDto node = grid.getSelectionModel().getSelectedItem();
				if (null != node)
					importVariantIntoTeamSpace(node);
			}
		});

		return importReportItem;
	}

	private MenuItem generateExecuteReportItem() {
		MenuItem executeReportItem = new MenuItem();
		executeReportItem.setText(ReportmanagerMessages.INSTANCE.execute());
		executeReportItem.setIcon(BaseIcon.EXECUTE.toImageResource());
		
		executeReportItem.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				ReportDto node = grid.getSelectionModel().getSelectedItem();
				if(node instanceof ReportDto){
					reportExecutorService.executeReport((ReportDto)node);
				}
			}
		});
		
		return executeReportItem;
	}

	private MenuItem generateInfoMenuItem() {
		MenuItem infoMenuItem = new MenuItem();
		infoMenuItem.setText(TreedbMessages.INSTANCE.infoMenuLabel());
		infoMenuItem.setIcon(BaseIcon.INFO.toImageResource());
		infoMenuItem.addSelectionHandler(new SelectionHandler<Item>() {
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				ReportDto node = grid.getSelectionModel().getSelectedItem();
				objectInfoService.displayInformationOn(node);
			}
		});
		
		return infoMenuItem;
	}

}