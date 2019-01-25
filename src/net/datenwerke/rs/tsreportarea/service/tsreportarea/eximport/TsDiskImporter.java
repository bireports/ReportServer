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
 
 
package net.datenwerke.rs.tsreportarea.service.tsreportarea.eximport;

import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeImporter;
import net.datenwerke.treedb.service.treedb.TreeDBManager;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class TsDiskImporter extends TreeNodeImporter {

	public static final String IMPORTER_ID = "TsDiskImporter";
	
	private final TsDiskService diskService;
	
	@Inject
	public TsDiskImporter(
			TsDiskService diskService
		){
		
		/* store objects */
		this.diskService = diskService;
	}
	
	@Override
	public Class<?>[] getRecognizedExporters() {
		return new Class<?>[]{TsDiskExporter.class};
	}

	@Override
	protected TreeDBManager getTreeDBManager() {
		return diskService;
	}

	@Override
	public String getId() {
		return IMPORTER_ID;
	}

}
