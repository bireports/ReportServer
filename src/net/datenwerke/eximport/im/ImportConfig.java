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
 
 
package net.datenwerke.eximport.im;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.datenwerke.eximport.ExportDataProvider;

/**
 * 
 *
 */
public class ImportConfig {
	
	private List<ImporterSpecificConfig> specificImporterConfigs = new ArrayList<ImporterSpecificConfig>();
	
	private final ExportDataProvider exportDataProvider;
	private Map<String, ImportItemConfig> itemConfigMap = new HashMap<String, ImportItemConfig>();
	
	public ImportConfig(ExportDataProvider exportDataProvider){
		this.exportDataProvider = exportDataProvider;
	}
	
	public void setItemConfigs(Set<ImportItemConfig> itemConfigs) {
		itemConfigMap.clear();
		for(ImportItemConfig conf : itemConfigs)
			itemConfigMap.put(conf.getId(), conf);
	}
	
	public void addItemConfig(ImportItemConfig... configs){
		for(ImportItemConfig conf : configs)
			itemConfigMap.put(conf.getId(), conf);
	}
	
	public void addItemConfig(Collection<ImportItemConfig> configs){
		for(ImportItemConfig conf : configs)
			itemConfigMap.put(conf.getId(), conf);
	}

	public boolean containsItemConfig(ImportItemConfig config){
		return itemConfigMap.containsKey(config.getId());
	}
	
	public Collection<ImportItemConfig> getItemConfigs() {
		return itemConfigMap.values();
	}
	
	public ExportDataProvider getExportDataProvider() {
		return exportDataProvider;
	}
	
	public void setSpecificImporterConfigs(List<ImporterSpecificConfig> specificImporterConfigs) {
		this.specificImporterConfigs = specificImporterConfigs;
	}

	public List<ImporterSpecificConfig> getSpecificImporterConfigs() {
		return specificImporterConfigs;
	}
	
	public void addSpecificImporterConfigs(ImporterSpecificConfig... config){
		specificImporterConfigs.addAll(Arrays.asList(config));
	}

	public ImportItemConfig getItemConfigFor(String id) {
		return itemConfigMap.get(id);
	}

}
