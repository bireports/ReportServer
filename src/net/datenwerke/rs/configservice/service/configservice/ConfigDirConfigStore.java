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
 
 
package net.datenwerke.rs.configservice.service.configservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.inject.Inject;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.HierarchicalConfiguration;

public class ConfigDirConfigStore extends AbstractConfigStore {
	
	private final static String CONFIG_FOLDER = "config";

	private ConfigDirService configDirService;

	@Inject
	public ConfigDirConfigStore(ConfigDirService configDirService) {
		this.configDirService = configDirService;
	}
	
	@Override
	public HierarchicalConfiguration loadConfig(String identifier) throws ConfigurationException {
		if(!configDirService.isEnabled())
			return null;
		
		File cfg = new File(configDirService.getConfigDir(), CONFIG_FOLDER + "/" + identifier);
		if(cfg.exists()){
			HierarchicalConfiguration config = createBaseConfig();
			try {
				((FileConfiguration)config).load(new FileInputStream(cfg));
			} catch (FileNotFoundException e) {
				return null;
			}
			return config;
		}
		
		return null;
	}

}
