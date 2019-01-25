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
 
 
package net.datenwerke.rs.fileserver.service.fileserver.eximport.hookers;

import net.datenwerke.eximport.ex.ExportConfig;
import net.datenwerke.rs.eximport.service.eximport.hooks.ExportAllHook;
import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeExImportOptions;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeExportItemConfig;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeExporterConfig;
import net.datenwerke.treedb.service.treedb.AbstractNode;

import com.google.inject.Inject;

public class ExportAllFilesHooker implements ExportAllHook {

	private final FileServerService fileService;
	
	@Inject
	public ExportAllFilesHooker(FileServerService fileService) {
		this.fileService = fileService;
	}

	@Override
	public void configure(ExportConfig config) {
		TreeNodeExporterConfig specConfig = new TreeNodeExporterConfig();
		specConfig.addExImporterOptions(TreeNodeExImportOptions.INCLUDE_OWNER, TreeNodeExImportOptions.INCLUDE_SECURITY);
		config.addSpecificExporterConfigs(specConfig);
		
		for(AbstractNode<?> node : fileService.getAllNodes())
			config.addItemConfig(new TreeNodeExportItemConfig(node));
	}

}
