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
 
 
package net.datenwerke.rs.base.ext.service.datasourcemanager.eximport;

import net.datenwerke.rs.core.service.datasourcemanager.DatasourceService;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeImporter;
import net.datenwerke.treedb.service.treedb.TreeDBManager;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class DatasourceManagerImporter extends TreeNodeImporter {

	public static final String IMPORTER_ID = "DatasourceManagerImporter";
	
	private final DatasourceService datasourceService;
	
	@Inject
	public DatasourceManagerImporter(
		DatasourceService datasourceService
		){
		
		/* store objects */
		this.datasourceService = datasourceService;
	}
	
	@Override
	public Class<?>[] getRecognizedExporters() {
		return new Class<?>[]{DatasourceManagerExporter.class};
	}

	@Override
	protected TreeDBManager getTreeDBManager() {
		return datasourceService;
	}

	@Override
	public String getId() {
		return IMPORTER_ID;
	}
}
