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
 
 
package net.datenwerke.rs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.inject.Inject;

import net.datenwerke.gf.service.jpa.annotations.JpaUnit;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.configservice.service.configservice.ConfigDirService;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class ReportServerPUStartup {

	public static final String PERSISTENCE_PROP_NAME = "persistence.properties";

	@Inject
	public ReportServerPUStartup(
			HookHandlerService hookHandlerService,
			ConfigDirService configDirService,
			@JpaUnit Properties jpaProperties
			) {

		loadPersistenceProperties(configDirService, jpaProperties);
		
		for(JpaPropertyConfiguratorHook hook : hookHandlerService.getHookers(JpaPropertyConfiguratorHook.class)){
			hook.configureProperties(jpaProperties);
		}
		
	}

	
	public static void loadPersistenceProperties(ConfigDirService configDirService, Properties jpaProperties){
		/* webapp config */
		try {
			PropertiesConfiguration peProps = new PropertiesConfiguration(PERSISTENCE_PROP_NAME);
			Properties props = new Properties();
			props.load(peProps.getURL().openStream());

			jpaProperties.putAll(props);
		} catch (ConfigurationException e) {
		} catch (IOException e) {
		}
		
		
		/* query config dir */
		if(configDirService.isEnabled()){
			try {
				File configDir = configDirService.getConfigDir();
				Properties props = new Properties();
				props.load(new FileInputStream(new File(configDir, PERSISTENCE_PROP_NAME)));
				
				jpaProperties.putAll(props);
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
		}
	}
}
