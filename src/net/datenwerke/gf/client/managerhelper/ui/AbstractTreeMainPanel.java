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
 
 
package net.datenwerke.gf.client.managerhelper.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewToolbarConfiguratorHook;
import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.DwTabPanel;
import net.datenwerke.gxtdto.client.theme.CssClassConstant;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.DwHookableToolbar;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.treedb.client.treedb.TreeDbManagerDao;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.widget.core.client.TabItemConfig;
import com.sencha.gxt.widget.core.client.TabPanel.TabPanelAppearance;
import com.sencha.gxt.widget.core.client.TabPanel.TabPanelBottomAppearance;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;
import com.sencha.gxt.widget.core.client.toolbar.FillToolItem;

/**
 * Base component, that renders the main field of a tree manager component.
 * 
 * 
 * 
 *
 */
abstract public class AbstractTreeMainPanel extends DwContentPanel {

	@CssClassConstant
	public static final String CSS_NAME = "rs-mngr-main";
	
	@CssClassConstant
	public static final String CSS_TOOLBAR_NAME = "rs-mngr-main-tb";
		
	@Inject
	private static HookHandlerService hookHandler;
	
	@Inject private Provider<DwHookableToolbar> toolbarProvider;
	
	/**
	 * Stores the tree manager that is invoked if forms are submitted.
	 */
	private final TreeDbManagerDao treeManager;
	
	final Map<MainPanelView, Widget> viewComponents = new HashMap<MainPanelView, Widget>();
	final Map<MainPanelView, DwContentPanel> mainPanelLookup = new HashMap<MainPanelView, DwContentPanel>();

	private AbstractTreeNavigationPanel treePanel;

	public AbstractTreeMainPanel(
		TreeDbManagerDao treeManager
		){
		
		/* store objects */
		this.treeManager = treeManager;
		
		initializeUI();
	}
	
	@Override
	public String getCssName() {
		return super.getCssName() + " " + CSS_NAME;
	}

	public String getCssToolbarName() {
		return CSS_TOOLBAR_NAME;
	}
	
	private void initializeUI() {
		setHeaderVisible(false);
	}
	
	public TreeDbManagerDao getTreeDbManager(){
		return treeManager;
	}

	public void displayTreeSelection(final List<MainPanelView> views, final AbstractNodeDto selectedNode, final UITree tree) {
		/* clear container */
		clear();

		/* build tab panel */
		final DwTabPanel tabPanel = new DwTabPanel(GWT.<TabPanelAppearance> create(TabPanelBottomAppearance.class));
		
		tabPanel.setBodyBorder(false);
		tabPanel.setBorders(false);
		
		/* add module's widget */
		boolean bFirst = true;
		for(final MainPanelView view : views){
			/* init */
			
			/* create inner tab panel */
			final VerticalLayoutContainer viewWrapper = new VerticalLayoutContainer();
			viewWrapper.setBorders(false);
			
			/* add toolbar */
			DwHookableToolbar toolbar = toolbarProvider.get();
			toolbar.getElement().addClassName(getCssToolbarName());
			toolbar.setContainerName(getToolbarName());
			viewWrapper.add(toolbar, new VerticalLayoutData(1,39));
			
			final DwContentPanel viewContentWrapper = DwContentPanel.newInlineInstance();
			viewWrapper.add(viewContentWrapper, new VerticalLayoutData(1,1));
			
			/* add to lookup table */
			mainPanelLookup.put(view, viewContentWrapper);
			
			/* configure dwToolbar */
			toolbar.getHookConfig().put("id", String.valueOf(selectedNode.getId()));
			toolbar.getHookConfig().put("classname", selectedNode.getClass().getName());
			toolbar.getHookConfig().put("path", treePanel.getPath(selectedNode));
			
			/* configure toolbar */
			Collection<MainPanelViewToolbarConfiguratorHook> toolbarConfigurators = hookHandler.getHookers(MainPanelViewToolbarConfiguratorHook.class);
			for(MainPanelViewToolbarConfiguratorHook configurator : toolbarConfigurators)
				configurator.mainPanelViewToolbarConfiguratorHook_addLeft(view, toolbar, selectedNode);
			
			toolbar.addBaseHookersLeft();
			
			toolbar.add(new FillToolItem());
			
			for(MainPanelViewToolbarConfiguratorHook configurator : toolbarConfigurators)
				configurator.mainPanelViewToolbarConfiguratorHook_addRight(view, toolbar, selectedNode);
			
			toolbar.addBaseHookersRight();
			
			/* initialize view */
			if(bFirst){
				bFirst = false;
				view.initializeView(selectedNode, tree, treeManager, this);
				Widget viewComponent = view.getViewComponent();
				viewComponents.put(view, viewComponent);
				view.viewAdded(viewContentWrapper);
				viewContentWrapper.setWidget(viewComponent);
			}
			
			/* add listener to tab selection */
			viewWrapper.addShowHandler(new ShowHandler() {
				@Override
				public void onShow(ShowEvent event) {
					if(! viewComponents.containsKey(view)){
						view.initializeView(selectedNode, tree, treeManager, AbstractTreeMainPanel.this);
						Widget viewComponent = view.getViewComponent();
						viewComponents.put(view, viewComponent);
						viewContentWrapper.add(viewComponent);
						view.viewAdded(viewContentWrapper);
					}
					
					viewWrapper.forceLayout();
				}
			});
			
			/* add tab to panel */
			TabItemConfig tabItemConfig = new TabItemConfig(view.getComponentHeader());
			if(null != view.getIcon())
				tabItemConfig.setIcon(view.getIcon());
			tabPanel.add(viewWrapper, tabItemConfig);
		}

		/* add tab panel */
		setWidget(tabPanel);
		
		/* rerender */
		forceLayout();
	}



	protected String getToolbarName() {
		return "AbstractTreeMainPanelToolbar";
	}

	public void reloadView(MainPanelView view) {
		if(viewComponents.containsKey(view)){
			Widget viewComponent = view.getViewComponent();
			viewComponents.put(view, viewComponent);
			
			DwContentPanel mainPanel = mainPanelLookup.get(view);

			mainPanel.clear();
			mainPanel.add(viewComponent);
			mainPanel.forceLayout();
		}
	}

	public void init(AbstractTreeManagerPanel managerPanel) {
	}

	public void setTree(AbstractTreeNavigationPanel treePanel) {
		this.treePanel = treePanel;
	}
}
