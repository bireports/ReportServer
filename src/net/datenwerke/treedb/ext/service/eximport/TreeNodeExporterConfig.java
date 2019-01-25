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
 
 
package net.datenwerke.treedb.ext.service.eximport;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import net.datenwerke.eximport.ex.ExporterSpecificExportConfig;

/**
 * 
 *
 */
public class TreeNodeExporterConfig implements ExporterSpecificExportConfig {

	private Set<TreeNodeExImportOptions> exImporterOptions = new HashSet<TreeNodeExImportOptions>();

	public Set<TreeNodeExImportOptions> getExImporterOptions() {
		return exImporterOptions;
	}

	public void setExImporterOptions(Set<TreeNodeExImportOptions> exImporterOptions) {
		this.exImporterOptions = exImporterOptions;
	}
	
	public void addExImporterOptions(TreeNodeExImportOptions... option){
		this.exImporterOptions.addAll(Arrays.asList(option));
	}

	public boolean containsExImporterOption(TreeNodeExImportOptions options) {
		return exImporterOptions.contains(options);
	}
}

