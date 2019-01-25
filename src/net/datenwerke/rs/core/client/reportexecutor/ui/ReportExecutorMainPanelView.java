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
 
 
package net.datenwerke.rs.core.client.reportexecutor.ui;


import java.util.ArrayList;
import java.util.List;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;

public abstract class ReportExecutorMainPanelView {

	private String executeReportToken;

	public abstract String getViewId();
	
	public boolean wantsToBeDefault(){
		return false;
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
	
	public ImageResource getIcon(){
		return null;
	}

	public void setExecuteReportToken(String executeReportToken) {
		this.executeReportToken = executeReportToken;
	}
	
	public String getExecuteReportToken(){
		return executeReportToken;
	}

	public boolean allowsDropOf(Object m) {
		return false;
	}

	public void objectDropped(Object m) {
	
	}

	public List<String> validateView() {
		return new ArrayList<String>();
	}

	public void cleanup() {
		
	}

}
