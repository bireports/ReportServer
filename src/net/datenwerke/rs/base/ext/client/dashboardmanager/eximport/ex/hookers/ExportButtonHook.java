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
 
 
package net.datenwerke.rs.base.ext.client.dashboardmanager.eximport.ex.hookers;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gxtdto.client.utilityservices.UtilsUIService;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.base.ext.client.dashboardmanager.eximport.ex.DashboardManagerExportDao;
import net.datenwerke.rs.core.client.datasourcemanager.dto.AbstractDatasourceManagerNodeDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.AbstractDashboardManagerNodeDto;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.ext.client.eximport.ex.QuickExportHookerBase;

/**
 * 
 *
 */
public class ExportButtonHook extends QuickExportHookerBase {
	
	private final DashboardManagerExportDao dashboardDao;

	@Inject
	public ExportButtonHook(
		DashboardManagerExportDao reDao,
		ToolbarService toolbarUIService,
		UtilsUIService utilsUIService
		) {
		super(toolbarUIService, utilsUIService);
		
		/* store objects */
		this.dashboardDao = reDao;
	}
	
	@Override
	protected boolean viewApplies(MainPanelView view,
			AbstractNodeDto selectedNode) {
		if(! (selectedNode instanceof AbstractDashboardManagerNodeDto) )
			return false;
		if(! MainPanelView.ID_MAIN_PROPERTIES_VIEW.equals(view.getViewId()))
			return false;
		
		return true;
	}

	@Override
	protected void quickExportClicked(AbstractNodeDto selectedNode) {
		startProgress();
		dashboardDao.quickExport((AbstractDashboardManagerNodeDto) selectedNode, getExportCallback());
	}

	@Override
	protected void loadAndDisplayResult(
			AsyncCallback<String> callback) {
		dashboardDao.loadResult(callback);
	}


	
}
