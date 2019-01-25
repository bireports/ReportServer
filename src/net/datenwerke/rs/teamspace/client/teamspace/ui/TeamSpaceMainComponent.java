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
 
 
package net.datenwerke.rs.teamspace.client.teamspace.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.form.DwComboBox;
import net.datenwerke.gxtdto.client.baseex.widget.layout.DwCardContainer;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwConfirmMessageBox;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.eventbus.events.SubmoduleDisplayRequest;
import net.datenwerke.gxtdto.client.eventbus.handlers.SubmoduleDisplayRequestHandler;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCAllowBlank;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.impl.SFFCTextAreaImpl;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.servercommunication.callback.NotamCallback;
import net.datenwerke.gxtdto.client.theme.CssClassConstant;
import net.datenwerke.gxtdto.client.ui.helper.wrapper.HeadDescMainWrapper;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.DwToolBar;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.gxtdto.client.utils.labelprovider.DisplayTitleLabelProvider;
import net.datenwerke.gxtdto.client.xtemplates.NullSafeFormatter;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceApp;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceDao;
import net.datenwerke.rs.teamspace.client.teamspace.TeamSpaceUIService;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.decorator.TeamSpaceDtoDec;
import net.datenwerke.rs.teamspace.client.teamspace.dto.pa.TeamSpaceDtoPA;
import net.datenwerke.rs.teamspace.client.teamspace.hooks.TeamSpaceAppProviderHook;
import net.datenwerke.rs.teamspace.client.teamspace.locale.TeamSpaceMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonArrowAlign;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style.SelectionMode;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.XTemplates.FormatterFactories;
import com.sencha.gxt.core.client.XTemplates.FormatterFactory;
import com.sencha.gxt.core.client.XTemplates.FormatterFactoryMethod;
import com.sencha.gxt.core.client.util.DelayedTask;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.SortDir;
import com.sencha.gxt.data.shared.Store.StoreSortInfo;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.ListView;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwAlertMessageBox;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.RowDoubleClickEvent;
import com.sencha.gxt.widget.core.client.event.RowDoubleClickEvent.RowDoubleClickHandler;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.menu.SeparatorMenuItem;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;

/**
 * 
 *
 */
@Singleton
public class TeamSpaceMainComponent extends VerticalLayoutContainer {

	@CssClassConstant
	public static final String CSS_NAME = "rs-teamspace";
	
	private static TeamSpaceDtoPA tsPA = GWT.create(TeamSpaceDtoPA.class);
	
	
	@FormatterFactories(@FormatterFactory(factory=NullSafeFormatter.class,methods=@FormatterFactoryMethod(name="nullsafe")))
	public interface TeamSpaceTemplates extends XTemplates {
		@XTemplate("<div class=\"rs-lview-title\">{entry.name:nullsafe}</div>" + 
			    "<div class=\"rs-lview-desc\">{entry.description:nullsafe}&nbsp;</div>"
				)
	    public SafeHtml render(TeamSpaceDto entry); 
	}
	
	private final EditTeamSpaceDialogCreator editTeamSpaceDialogCreator;
	private final HookHandlerService hookHandler;
	private final TeamSpaceDao tsDao;
	private final ToolbarService toolbarService;
	private final TeamSpaceUIService teamSpaceService;
	
	private DwToolBar appToolbar;
	private DwCardContainer mainComponent;
	private DwComboBox<TeamSpaceDto> teamSpaceSelector;
	
	private TeamSpaceDto currentSpace; 
	private Map<TeamSpaceApp, Boolean> renderedApps = new HashMap<TeamSpaceApp, Boolean>();
	private ListStore<TeamSpaceDto> spaceStore;
	private Map<Long, LinkedHashMap<TeamSpaceAppProviderHook, TeamSpaceApp>> teamspaceToAppMap = new HashMap<Long, LinkedHashMap<TeamSpaceAppProviderHook,TeamSpaceApp>>();
	
	private Map<TeamSpaceAppProviderHook, TeamSpaceApp> currentAppMap = new HashMap<TeamSpaceAppProviderHook, TeamSpaceApp>();
	
	@Inject
	public TeamSpaceMainComponent(
		EditTeamSpaceDialogCreator editTeamSpaceDialogCreator,
		HookHandlerService hookHandler,
		TeamSpaceDao tsDao,
		ToolbarService toolbarService,
		TeamSpaceUIService teamSpaceService, 
		final EventBus eventBus
		){
		
		/* store objects */
		this.editTeamSpaceDialogCreator = editTeamSpaceDialogCreator;
		this.hookHandler = hookHandler;
		this.tsDao = tsDao;
		this.toolbarService = toolbarService;
		this.teamSpaceService = teamSpaceService;
		
		/* attach event bus listener */
		eventBus.addHandler(SubmoduleDisplayRequest.TYPE, new SubmoduleDisplayRequestHandler() {
			
			@Override
			public void onSubmoduleDisplayRequest(SubmoduleDisplayRequest event) {
				buildCompleteAppMap();
				for(Long tsid : teamspaceToAppMap.keySet()){
					LinkedHashMap<TeamSpaceAppProviderHook, TeamSpaceApp> appMap = teamspaceToAppMap.get(tsid);
					for(final TeamSpaceApp tsapp : appMap.values()){
						if(tsapp.getAppComponent() == event.getSubmodule()){
							final TeamSpaceDto findModel = teamSpaceSelector.getStore().findModelWithKey(String.valueOf(tsid));
							
							DelayedTask dt = new DelayedTask() {
								
								@Override
								public void onExecute() {
									teamSpaceSelector.select(findModel);
									teamSpaceSelector.setValue(findModel);
									showApp(tsapp);
								}
							};
							
							dt.delay(200);
							
							eventBus.fireEvent(new SubmoduleDisplayRequest(TeamSpaceMainComponent.this, null));
						}
					}
				}
			}
		});
		
		/* init */
		initializeUI();
	}
	
	private void initializeUI(){
		getElement().addClassName(CSS_NAME);
		
		/* initialize store */
		initializeModelStore();
		
		/* create top toolbar component */
		appToolbar = new DwToolBar();
		appToolbar.addClassName("rs-teamspace-tb");
		
		/* crete main component */
		mainComponent = new DwCardContainer();
		
		/* init space selector component */
		initSpaceSelector();
		
		/* add containers */
		add(appToolbar, new VerticalLayoutData(1,48,new Margins(0,0,5,0)));
		
		add(mainComponent, new VerticalLayoutData(1,1));
	}
	
	/**
	 * Creates the combo box which allows the user to select the current space
	 */
	private void initSpaceSelector() {
		final TeamSpaceTemplates template = GWT.create(TeamSpaceTemplates.class);

		ListView<TeamSpaceDto, TeamSpaceDto> view = new ListView<TeamSpaceDto, TeamSpaceDto>(spaceStore, new IdentityValueProvider<TeamSpaceDto>());
		
		view.setCell(new AbstractCell<TeamSpaceDto>() {
			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					TeamSpaceDto value, SafeHtmlBuilder sb) {
				sb.append(template.render(value));
			}
		});
		
		
		teamSpaceSelector = new DwComboBox<TeamSpaceDto>(new ComboBoxCell<TeamSpaceDto>(spaceStore, new DisplayTitleLabelProvider<TeamSpaceDto>(), view));
		teamSpaceSelector.plainAppearance();
		teamSpaceSelector.setTriggerIcon(BaseIcon.CARET_DOWN);
		
		/* configure box */
		teamSpaceSelector.setForceSelection(true);
		teamSpaceSelector.setAllowBlank(false);
		teamSpaceSelector.setEditable(false);
		teamSpaceSelector.setWidth(350);
		teamSpaceSelector.setMinListWidth(350);
		teamSpaceSelector.setTypeAhead(true);
		teamSpaceSelector.setTriggerAction(TriggerAction.ALL);
		
		teamSpaceSelector.addSelectionHandler(new SelectionHandler<TeamSpaceDto>() {
			@Override
			public void onSelection(SelectionEvent<TeamSpaceDto> event) {
				if(null == event.getSelectedItem())
					return;
				currentSpace = event.getSelectedItem();
				initCurrentSpace();
			}
		});
	}

	/**
	 * Initializes the store that holds the BaseModel counterparts to the TeamSpaceDtos
	 */
	private void initializeModelStore() {
		RpcProxy<ListLoadConfig, ListLoadResult<TeamSpaceDto>> proxy = new RpcProxy<ListLoadConfig, ListLoadResult<TeamSpaceDto>>() {
			@Override
			public void load(ListLoadConfig loadConfig, final AsyncCallback<ListLoadResult<TeamSpaceDto>> callback) {
				tsDao.loadTeamSpaces(callback);	
			}
		};
		ListLoader<ListLoadConfig, ListLoadResult<TeamSpaceDto>> loader = new ListLoader<ListLoadConfig, ListLoadResult<TeamSpaceDto>>(proxy);
		spaceStore = new ListStore<TeamSpaceDto>(tsPA.dtoId());
		spaceStore.addSortInfo(new StoreSortInfo<TeamSpaceDto>(tsPA.name(), SortDir.ASC));
		
		loader.addLoadHandler(new LoadResultListStoreBinding<ListLoadConfig, TeamSpaceDto, ListLoadResult<TeamSpaceDto>>(spaceStore));
		loader.load();
	}

	public void notifyOfSelection() {
		if(null == currentSpace)
			loadPrimaryTeamSpace();
	}
	
	/**
	 * Loads the primary team space that is displayed at startup.
	 */
	private void loadPrimaryTeamSpace(){
		mainComponent.mask(TeamSpaceMessages.INSTANCE.loadPrimarySpaceMessage());
		tsDao.getPrimarySpace(new RsAsyncCallback<TeamSpaceDto>(){
			@Override
			public void onSuccess(TeamSpaceDto result) {
				mainComponent.unmask();
				
				if(null == result) {
					appToolbar.clear();
					mainComponent.clear();
					displayCreateInitialSpaceDialog();
				} else
					loadSpace(result);
			}
		});
	}

	public void loadSpace(TeamSpaceDto teamSpace) {
		this.currentSpace = teamSpace;
		if(null == teamSpace)
			return;
		if(null != spaceStore.findModel(teamSpace))
			spaceStore.update(teamSpace);
		else
			spaceStore.add(currentSpace);
		initCurrentSpace();
	}
	
	/**
	 * initializes a space 
	 */
	protected void initCurrentSpace() {
		appToolbar.clear();
		mainComponent.clear();
		renderedApps.clear();
		
		initApps();
		addSpaceConfigurationToToolbar();
		
		appToolbar.forceLayout();
		mainComponent.forceLayout();
	}
	
	private void buildCompleteAppMap(){
		for(TeamSpaceDto ts: spaceStore.getAll())
			buildAppMapforTs(ts);
	}
	
	private Map<TeamSpaceAppProviderHook, TeamSpaceApp> buildAppMapforTs(TeamSpaceDto space){
		List<TeamSpaceAppProviderHook> appProviders = hookHandler.getHookers(TeamSpaceAppProviderHook.class);
		
		if(!teamspaceToAppMap.containsKey(space.getId()))
			teamspaceToAppMap.put(space.getId(), new LinkedHashMap<TeamSpaceAppProviderHook, TeamSpaceApp>());

		Map<TeamSpaceAppProviderHook, TeamSpaceApp> appMap = teamspaceToAppMap.get(space.getId());
		for(TeamSpaceAppProviderHook appProvider : appProviders){
			if(appMap.containsKey(appProvider)){
				if(! ((TeamSpaceDtoDec)space).isAppInstalled(appMap.get(appProvider))){
					appMap.remove(appProvider);
				}
			}else{
				TeamSpaceApp app = appProvider.getObject();
				if(((TeamSpaceDtoDec)space).isAppInstalled(app)){
					appMap.put(appProvider, app);
				}
			}
		}
		
		return appMap;
	}

	private void initApps() {
		if( ((TeamSpaceDtoDec)currentSpace).getInstalledApps().size() == 0) {
			Widget comp = new HeadDescMainWrapper(TeamSpaceMessages.INSTANCE.noAppInstalled(), 
				TeamSpaceMessages.INSTANCE.noAppInstalledMsg(), 
				new Label());
			mainComponent.add(comp);
			
			mainComponent.setActiveWidget(comp);
		} else {
			
			/* Refresh the appMap */
			currentAppMap  = buildAppMapforTs(currentSpace);
			
			boolean bFirst = true;
			for(final TeamSpaceApp app : currentAppMap.values()){
				
				/* create toolbar button */
				DwTextButton btn = toolbarService.createSmallButtonLeft(app.getName(), app.getIcon());
				//appToolbar.add(btn); // only a single app, no need for a button
				
				/* show on selection */
				btn.addSelectHandler(new SelectHandler() {
					
					@Override
					public void onSelect(SelectEvent event) {
						showApp(app);
					}
				});
				
				/* if first app .. load it */
				if(bFirst){
					bFirst = false;
					showApp(app);
				}
			}
		}
	}
	
	public Collection<TeamSpaceApp> getCurrentApps(){
		return currentAppMap.values();
	}

	private void refreshInTeamspaceAppMap(TeamSpaceDto teamSpace, TeamSpaceAppProviderHook provider, TeamSpaceApp app){
		
	}
	
	protected void showApp(TeamSpaceApp app) {
		if(! Boolean.TRUE.equals(renderedApps.get(app))){
			mainComponent.add(app.getAppComponent());
			renderedApps.put(app, Boolean.TRUE);
		}
		mainComponent.setActiveWidget(app.getAppComponent());
		app.displaySpace(currentSpace);
		mainComponent.forceLayout();
	}

	/**
	 * Adds possibilities for space configuration.
	 * 
	 * <p>These include, changing the current space, editing
	 * properties, adding new spaces, etc.</p>
	 */
	private void addSpaceConfigurationToToolbar() {
		/* set the current space in space selector */
		teamSpaceSelector.disableEvents();
		teamSpaceSelector.setValue(spaceStore.findModel(currentSpace));
		teamSpaceSelector.enableEvents();

		appToolbar.add(teamSpaceSelector);
		appToolbar.add(new FillToolItem());
		
		/* create manage spaces button */ 
		if(teamSpaceService.isManager(currentSpace) || teamSpaceService.hasTeamSpaceCreateRight() ){
			DwTextButton manageSpacesBtn = initManageSpacesBtn();
			appToolbar.add(manageSpacesBtn);
		}

		/* create admin button */
		if(teamSpaceService.isGlobalTsAdmin()){
			DwTextButton adminTeamSpaceBtn = initAdminTeamSpaceBtn();
			appToolbar.add(adminTeamSpaceBtn);
		}
	}
	
	
	private DwTextButton initAdminTeamSpaceBtn() {
		DwTextButton btn = new DwTextButton(BaseIcon.USER_SECRET);
		btn.setToolTip(TeamSpaceMessages.INSTANCE.adminButton());
		
		btn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				displayAdminSelectSpaceDialog();
			}
		});
		
		return btn;
	}

	private DwTextButton initManageSpacesBtn() {
		Menu manageSpaceMenu = new DwMenu();
		DwTextButton manageSpacesBtn = toolbarService.createSmallButtonLeft(TeamSpaceMessages.INSTANCE.manageSpacesText(), BaseIcon.GROUP_PROPERTIES);
		manageSpacesBtn.setMenu(manageSpaceMenu);
		manageSpacesBtn.setArrowAlign(ButtonArrowAlign.RIGHT);
		
		/* config */
		if(teamSpaceService.isManager(currentSpace)){
			MenuItem configItem = new DwMenuItem(TeamSpaceMessages.INSTANCE.configureCurrentSpaceText(), BaseIcon.COG);
			configItem.addSelectionHandler(new SelectionHandler<Item>() {
				
				@Override
				public void onSelection(SelectionEvent<Item> event) {
					displayConfigureCurrentSpaceDialog();
				}
			});
			manageSpaceMenu.add(configItem);
			manageSpaceMenu.add(new SeparatorMenuItem());
		}
		
		/* add */
		if(teamSpaceService.hasTeamSpaceCreateRight()){
			MenuItem addItem = new DwMenuItem(TeamSpaceMessages.INSTANCE.createSpaceText(), BaseIcon.GROUP_ADD);
			addItem.addSelectionHandler(new SelectionHandler<Item>() {
				@Override
				public void onSelection(SelectionEvent<Item> event) {
					displayAddSpaceDialog();
				}
			});
			manageSpaceMenu.add(addItem);
		}

		/* remove */
		if(teamSpaceService.isAdmin(currentSpace)){
			MenuItem removeItem = new DwMenuItem(TeamSpaceMessages.INSTANCE.removeCurrentSpaceText(), BaseIcon.DELETE);
			removeItem.addSelectionHandler(new SelectionHandler<Item>() {

				@Override
				public void onSelection(SelectionEvent<Item> event) {
					displayRemoveSpaceDialog();
				}
			});
			manageSpaceMenu.add(removeItem);
		}
		
		return manageSpacesBtn;
	}


	protected void displayRemoveSpaceDialog() {
		ConfirmMessageBox cmb = new DwConfirmMessageBox(TeamSpaceMessages.INSTANCE.deleteTeamSpaceConfirmTitle(), TeamSpaceMessages.INSTANCE.deleteTeamSpaceConfirmMessage(currentSpace.getName()));
		cmb.addDialogHideHandler(new DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				if (event.getHideButton() == PredefinedButton.YES)
					removeCurrentSpace();	
			}
		});
		cmb.show();
	}

	protected void removeFromStore(TeamSpaceDto space) {
		spaceStore.remove(space);
	}


	protected void displayAddSpaceDialog() {
		final DwWindow window = new DwWindow();
		window.setSize(430, 340);
		window.setHeadingText(TeamSpaceMessages.INSTANCE.newTeamSpaceHeading());
		window.setHeaderIcon(BaseIcon.GROUP_ADD);
		
		/* create form */
		final SimpleForm form = SimpleForm.getInlineInstance();
		window.add(form, new MarginData(10));
		
		form.addField(String.class, 
				TeamSpaceDtoPA.INSTANCE.name(), 
				TeamSpaceMessages.INSTANCE.newTeamSpaceNameLabel(),
				new SFFCAllowBlank() {
					@Override
					public boolean allowBlank() {
						return false;
					}
				});
		
		form.addField(String.class, 
				TeamSpaceDtoPA.INSTANCE.description(), 
				TeamSpaceMessages.INSTANCE.newTeamSpaceDescriptionLabel(),
				new SFFCTextAreaImpl());
		
		/* create dummy teamspace and bind it to form */
		final TeamSpaceDto dummy = new TeamSpaceDtoDec();
		form.bind(dummy);
		
		/* create buttons */
		window.addCancelButton();
		
		DwTextButton submitBtn = new DwTextButton(TeamSpaceMessages.INSTANCE.submitCreateTeamSpace());
		window.addButton(submitBtn);
		submitBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if(! form.isValid())
					return;
				window.hide();
				tsDao.createNewTeamSpace(dummy, new NotamCallback<TeamSpaceDto>(TeamSpaceMessages.INSTANCE.newTeamSpaceCreatedMessage()){
					@Override
					public void doOnSuccess(TeamSpaceDto space) {
						loadSpace(space);
					}
				});
			}
		});
		
		window.show();
	}
	
	protected void removeCurrentSpace() {
		tsDao.removeTeamSpace(currentSpace, new NotamCallback<Void>(TeamSpaceMessages.INSTANCE.teamSpaceRemoved()){
			@Override
			public void doOnSuccess(Void result) {
				removeFromStore(currentSpace);
				currentSpace = null;
				loadPrimaryTeamSpace();
			}
		});
	}


	protected void displayConfigureCurrentSpaceDialog() {
		editTeamSpaceDialogCreator.displayDialog(this, currentSpace);
	}

	protected void displayCreateInitialSpaceDialog() {
		boolean admin = teamSpaceService.isGlobalTsAdmin();
		boolean create = teamSpaceService.hasTeamSpaceCreateRight();
		
		if(! admin && ! create){
			new DwAlertMessageBox(BaseMessages.INSTANCE.error(), TeamSpaceMessages.INSTANCE.noAccess()).show();
			return;
		}
		
		final DwWindow window = new DwWindow();
		window.setModal(true);
		window.setWidth(300);
		window.setHeight(130);
		window.setHeaderIcon(BaseIcon.COG);
		
		window.add(new Label(TeamSpaceMessages.INSTANCE.noSpaceExists()), new MarginData(10));
		
		DwTextButton no = new DwTextButton(BaseMessages.INSTANCE.cancel());
		no.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				window.hide();
			}
		});
		window.addButton(no);
		
		/* ok button */
		if(create){
			DwTextButton yes = new DwTextButton(TeamSpaceMessages.INSTANCE.createNewSpace());
			yes.addSelectHandler(new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					window.hide();
					displayAddSpaceDialog();
				}
			});
			window.addButton(yes);
		}
		
		if(admin){
			DwTextButton adminBtn = new DwTextButton(TeamSpaceMessages.INSTANCE.adminButton());
			adminBtn.addSelectHandler(new SelectHandler() {
				
				@Override
				public void onSelect(SelectEvent event) {
					window.hide();
					displayAdminSelectSpaceDialog();
				}
			});
			window.addButton(adminBtn);
		}
		
		window.show();
	}

	protected void displayAdminSelectSpaceDialog() {
		/* create window */
		final DwWindow window = new DwWindow();
		window.setHeadingText(TeamSpaceMessages.INSTANCE.adminButton());
		window.setSize(640, 480);
		window.setHeaderIcon(BaseIcon.USER_SECRET);

		/* create store */
		final ListStore<TeamSpaceDto> adminSpaceStore = new ListStore<TeamSpaceDto>(tsPA.dtoId());
		adminSpaceStore.addSortInfo(new StoreSortInfo<TeamSpaceDto>(tsPA.name(), SortDir.ASC));
		
		/* create columns */
		List<ColumnConfig<TeamSpaceDto, ?>> columns = new ArrayList<ColumnConfig<TeamSpaceDto, ?>>();
		
		/* icon */
		ColumnConfig<TeamSpaceDto,TeamSpaceDto> iconColumn = new ColumnConfig<TeamSpaceDto, TeamSpaceDto>(new IdentityValueProvider<TeamSpaceDto>(), 25);
		iconColumn.setCell(new AbstractCell<TeamSpaceDto>() {

			@Override
			public void render(com.google.gwt.cell.client.Cell.Context context,
					TeamSpaceDto value, SafeHtmlBuilder sb) {
				sb.append(BaseIcon.GROUP_EDIT.toSafeHtml());
			}
		});
		columns.add(iconColumn);
		
		ColumnConfig<TeamSpaceDto, String> nameColumn = new ColumnConfig<TeamSpaceDto, String>(tsPA.name(), 150, BaseMessages.INSTANCE.propertyName());
		columns.add(nameColumn);
		
		ColumnConfig<TeamSpaceDto, String> descriptionColumn = new ColumnConfig<TeamSpaceDto, String>(tsPA.description(), 300, BaseMessages.INSTANCE.propertyDescription());
		columns.add(descriptionColumn);
		
		/* create grid */
		final Grid<TeamSpaceDto> grid = new Grid<TeamSpaceDto>(adminSpaceStore, new ColumnModel<TeamSpaceDto>(columns));
		grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		grid.mask(BaseMessages.INSTANCE.loadingMsg());
		window.add(grid);
		
		grid.addRowDoubleClickHandler(new RowDoubleClickHandler() {
			@Override
			public void onRowDoubleClick(RowDoubleClickEvent event) {
				TeamSpaceDto ts = adminSpaceStore.get(event.getRowIndex());
				if(null != ts){
					window.mask(BaseMessages.INSTANCE.loadingMsg());
					tsDao.reloadTeamSpace(ts, new RsAsyncCallback<TeamSpaceDto>(){
						@Override
						public void onSuccess(TeamSpaceDto result) {
							window.hide();
							loadSpace(result);
						}
					});
				}
			}
		});
		
		tsDao.loadAllTeamSpaces(new RsAsyncCallback<ListLoadResult<TeamSpaceDto>>(){
			@Override
			public void onSuccess(ListLoadResult<TeamSpaceDto> result) {
				adminSpaceStore.addAll(result.getData());
				grid.unmask();
			}
		});
				
		/* add buttons */
		window.addCancelButton();
		
		DwTextButton submit = new DwTextButton(BaseMessages.INSTANCE.ok());
		submit.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				TeamSpaceDto ts = grid.getSelectionModel().getSelectedItem();
				if(null != ts){
					window.mask(BaseMessages.INSTANCE.loadingMsg());
					tsDao.reloadTeamSpace(ts, new RsAsyncCallback<TeamSpaceDto>(){
						@Override
						public void onSuccess(TeamSpaceDto result) {
							window.hide();
							loadSpace(result);
						}
					});
				}
			}
		});
		window.addButton(submit);

		/* display window */
		window.show();
	}


	
}
