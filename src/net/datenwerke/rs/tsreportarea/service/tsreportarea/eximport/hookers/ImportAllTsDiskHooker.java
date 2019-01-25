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
 
 
package net.datenwerke.rs.tsreportarea.service.tsreportarea.eximport.hookers;

import net.datenwerke.eximport.ExportDataAnalyzerService;
import net.datenwerke.eximport.im.ImportConfig;
import net.datenwerke.eximport.obj.ExportedItem;
import net.datenwerke.rs.eximport.service.eximport.hooks.ImportAllHook;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.eximport.TsDiskExporter;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeImportItemConfig;

import com.google.inject.Inject;

public class ImportAllTsDiskHooker implements ImportAllHook {

	private final TsDiskService tsDiskService;
	private final ExportDataAnalyzerService analizerService;
	
	@Inject
	public ImportAllTsDiskHooker(
		TsDiskService tsDiskService,
		ExportDataAnalyzerService analizerService) {
		this.tsDiskService = tsDiskService;
		this.analizerService = analizerService;
	}



	@Override
	public void configure(ImportConfig config) {
		try {
			for(ExportedItem item : analizerService.getExportedItemsFor(config.getExportDataProvider(), TsDiskExporter.class)){
				TreeNodeImportItemConfig itemConfig = new TreeNodeImportItemConfig(item.getId());
				config.addItemConfig(itemConfig);
			}
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
