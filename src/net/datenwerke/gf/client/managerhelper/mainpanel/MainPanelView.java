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
 
 
package net.datenwerke.gf.client.managerhelper.mainpanel;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.Container;

import net.datenwerke.gf.client.managerhelper.ui.AbstractTreeMainPanel;
import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.treedb.client.treedb.TreeDbManagerDao;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

/**
 * The base class of a specific view for a node in a manager component.
 * 
 *
 */
public abstract class MainPanelView {

	public static final String ID_MAIN_PROPERTIES_VIEW = "MainPropertiesView";
	
	protected AbstractNodeDto selectedNode;
	protected UITree tree;
	protected TreeDbManagerDao treeManager;
	protected AbstractTreeMainPanel mainPanel;

	
	public void initializeView(AbstractNodeDto selectedNode, UITree tree, TreeDbManagerDao treeManager, AbstractTreeMainPanel mainPanel){
		this.selectedNode = selectedNode;
		this.tree = tree;
		this.treeManager = treeManager;
		this.mainPanel = mainPanel;
	}
	
	
	protected void mask(String msg) {
		mainPanel.mask(msg);
	}
	
	protected void enable() {
		mainPanel.enable();
	}
	
	protected void disable() {
		mainPanel.disable();
	}
	
	protected void unmask() {
		mainPanel.unmask();
	}
	
	public void reloadView(){
		mainPanel.reloadView(this);
	}
	
	public void reloadNodeAndView(){
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				treeManager.refreshNode(getSelectedNode(), new RsAsyncCallback<AbstractNodeDto>(){
					@Override
					public void onSuccess(AbstractNodeDto result) {
						unmask();
						reloadView();
					}
				});	
			}
		});
	}
	
	public void reloadNode(final AsyncCallback<AbstractNodeDto> callback){
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				treeManager.refreshNode(getSelectedNode(), callback);	
			}
		});
	}
	
	public String getViewId() {
		return MainPanelView.ID_MAIN_PROPERTIES_VIEW;
	}

	public boolean appliesTo(AbstractNodeDto item){
		return true;
	}
	
	public AbstractNodeDto getSelectedNode() {
		return selectedNode;
	}
	
	public UITree getTree() {
		return tree;
	}

	public void setTree(UITree tree) {
		this.tree = tree;
	}

	public TreeDbManagerDao getTreeManager() {
		return treeManager;
	}

	public void setTreeManager(TreeDbManagerDao treeManager) {
		this.treeManager = treeManager;
	}

	public abstract String getComponentHeader();
	
	/**
	 * 
	 * @param selectedNode
	 * @param tree
	 * @param treeManager
	 * @return
	 */
	public abstract Widget getViewComponent();


	public void viewAdded(Container viewWrapper) {
	}

	public ImageResource getIcon() {
		return null;
	}
	
	/**
	 * Is the view sticky, i.e. once it is selected, should it try to be autoselected if the user clicks on another tree entry ?
	 * @return
	 */
	public boolean isSticky() {
		return false;
	}

}
