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
 
 
package net.datenwerke.rs.configservice.service.configservice.hookers;

import java.io.File;

import javax.inject.Inject;

import net.datenwerke.rs.configservice.service.configservice.ConfigDirService;
import net.datenwerke.rs.configservice.service.configservice.LocaleAwareExpressionEngine;
import net.datenwerke.rs.utils.properties.ApplicationPropertiesProviderHook;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ExpressionEngine;

public class ConfigDirApplicationPropertiesProvider implements ApplicationPropertiesProviderHook {

	private static final String REPORTSERVER_PROPERTIES = "reportserver.properties";
	
	private ConfigDirService configDirService;

	@Inject
	public ConfigDirApplicationPropertiesProvider(ConfigDirService configDirService) {
		this.configDirService = configDirService;
	}
	
	@Override
	public Configuration getConfig() throws ConfigurationException {
		if(!configDirService.isEnabled())
			return null;
		
		File cfg = new File(configDirService.getConfigDir(), REPORTSERVER_PROPERTIES);
		if(cfg.exists()){
			PropertiesConfiguration config = new PropertiesConfiguration(cfg);
			
			return config;
		}
		
		return null;
	}
	
	protected HierarchicalConfiguration createBaseConfig(){
		XMLConfiguration config = new XMLConfiguration();

		/* initialize and use localeawareexpressionengine */
		ExpressionEngine originalExpressionEngine = config.getExpressionEngine();
		ExpressionEngine localeAwareExpressionEngine = new LocaleAwareExpressionEngine(originalExpressionEngine);
		config.setExpressionEngine(localeAwareExpressionEngine);
		
		/* don't interpret values as lists */
		config.setDelimiterParsingDisabled(true);
		
		return config;
	}


}
