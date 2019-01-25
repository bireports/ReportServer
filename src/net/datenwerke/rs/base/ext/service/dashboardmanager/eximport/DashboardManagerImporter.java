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
 
 
package net.datenwerke.rs.base.ext.service.dashboardmanager.eximport;

import com.google.inject.Inject;

import net.datenwerke.rs.dashboard.service.dashboard.DashboardService;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeImporter;
import net.datenwerke.treedb.service.treedb.TreeDBManager;

/**
 * 
 *
 */
public class DashboardManagerImporter extends TreeNodeImporter {

	public static final String IMPORTER_ID = "DashboardManagerImporter";
	
	private final DashboardService dashboardService;
	
	@Inject
	public DashboardManagerImporter(
		DashboardService dashboardService
		){
		
		/* store objects */
		this.dashboardService = dashboardService;
	}
	
	@Override
	public Class<?>[] getRecognizedExporters() {
		return new Class<?>[]{DashboardManagerExporter.class};
	}

	@Override
	protected TreeDBManager getTreeDBManager() {
		return dashboardService;
	}

	@Override
	public String getId() {
		return IMPORTER_ID;
	}
}
