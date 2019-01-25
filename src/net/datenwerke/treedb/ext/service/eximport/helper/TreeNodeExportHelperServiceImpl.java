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
 
 
package net.datenwerke.treedb.ext.service.eximport.helper;

import net.datenwerke.eximport.ExportService;
import net.datenwerke.eximport.ex.ExportConfig;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeExportItemConfig;
import net.datenwerke.treedb.service.treedb.AbstractNode;

import com.google.inject.Inject;

public class TreeNodeExportHelperServiceImpl {

	private final ExportService exportService;
	
	@Inject
	public TreeNodeExportHelperServiceImpl(ExportService exportService) {
		this.exportService = exportService;
	}

	public String export(AbstractNode node, boolean addChildren,
			String name) {
		/* export report */
		ExportConfig exportConfig = new ExportConfig();
		exportConfig.setName(name);
		exportConfig.addItemConfig(new TreeNodeExportItemConfig(node));
		
		if(addChildren)
			addChildren(exportConfig, node);
		
		return exportService.exportIndent(exportConfig);
	}

	private void addChildren(ExportConfig exportConfig, AbstractNode node) {
		for(Object o : node.getChildren()){
			AbstractNode childNode = (AbstractNode) o;
			exportConfig.addItemConfig(new TreeNodeExportItemConfig(childNode));
			addChildren(exportConfig, childNode);
		}
	}

}
