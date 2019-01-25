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
 
 
package net.datenwerke.gf.client.managerhelper.ui;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.theme.CssClassConstant;
import net.datenwerke.treedb.client.treedb.TreeDbManagerDao;

import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;


/**
 * Allows for easily setting up administration consoles with trees.
 * 
 *
 */
public abstract class AbstractTreeManagerPanel extends DwContentPanel {

	@CssClassConstant
	public static final String CSS_NAME = "rs-mngr";
	
	protected final AbstractTreeMainPanel mainPanel;
	protected final AbstractTreeNavigationPanel navPanel;
	
	public AbstractTreeManagerPanel(
		AbstractTreeMainPanel mainPanel,
		AbstractTreeNavigationPanel treePanel
		){
		
		this.mainPanel = mainPanel;
		this.navPanel = treePanel;
		mainPanel.setTree(navPanel);
		
		mainPanel.init(this);
		navPanel.init(this);
		
		initializeUI();
	}
	
	@Override
	public String getCssName() {
		return super.getCssName() + " " + CSS_NAME;
	}
	
	protected void initializeUI() {
		setHeadingText(getHeadingText());
		
		BorderLayoutContainer container = new BorderLayoutContainer();
		setWidget(container);
		
		BorderLayoutData westData = new BorderLayoutData(300);
		westData.setSplit(true);
		westData.setFloatable(true);
		
		container.setWestWidget(navPanel, westData );
		container.setCenterWidget(mainPanel);
	}

	abstract protected String getHeadingText();

	public AbstractTreeMainPanel getMainPanel() {
		return mainPanel;
	}

	public AbstractTreeNavigationPanel getNavPanel() {
		return navPanel;
	}

	public TreeDbManagerDao getTreeDbManager(){
		return mainPanel.getTreeDbManager();
	}

}
