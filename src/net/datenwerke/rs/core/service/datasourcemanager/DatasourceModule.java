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
 
 
package net.datenwerke.rs.core.service.datasourcemanager;

import java.util.HashSet;
import java.util.Set;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.datasourcemanager.annotations.DatasourceModuleProperties;
import net.datenwerke.rs.core.service.datasourcemanager.annotations.DefaultDatasource;
import net.datenwerke.rs.core.service.datasourcemanager.annotations.ReportServerDatasourceDefinitions;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.hooks.DatasourceProviderHook;
import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;
import net.datenwerke.rs.utils.config.ConfigService;

import org.apache.commons.configuration.Configuration;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Scopes;

public class DatasourceModule extends AbstractReportServerModule {

	public static final String CONFIG_FILE = "datasources/datasources.cf";
	public static final String PROPERTY_DEFAULT_DATASOURCE_ID = "datasource.defaultDatasource";
	public static final String PROPERTY_DEFAULT_DATASOURCE_NAME = "datasource.defaultDatasourceName";
		
	@Override
	protected void configure() {
		bind(DatasourceService.class).to(DatasourceServiceImpl.class).in(Scopes.SINGLETON);
		
		/* startup */
		bind(DatasourceStartup.class).asEagerSingleton();
	}


	/**
	 * Register DatasourceDefinitions
	 * 
	 * @return
	 */
	@Provides @ReportServerDatasourceDefinitions @Inject
	public Set<Class<? extends DatasourceDefinition>> provideDataSourceDefinitions(
		HookHandlerService hookHandler	
		){
		Set<Class<? extends DatasourceDefinition>> definitions = new HashSet<Class<? extends DatasourceDefinition>>();
		
		for(DatasourceProviderHook dsProvider : hookHandler.getHookers(DatasourceProviderHook.class))
			definitions.addAll(dsProvider.getDatasources());
		
		return definitions;
	}
	
	@Provides @Inject @DatasourceModuleProperties
	public Configuration provideConfig(ConfigService configService){
		return configService.getConfig(CONFIG_FILE);
	}
	
	@Provides @Inject @DefaultDatasource
	public String provideDefaultDatasourceId(@DatasourceModuleProperties Configuration config, DatasourceService dsService){
		String id = config.getString(DatasourceModule.PROPERTY_DEFAULT_DATASOURCE_ID);
		if(null == id){
			String name = config.getString(DatasourceModule.PROPERTY_DEFAULT_DATASOURCE_NAME);
			if(null != name){
				try {
					DatasourceDefinition ds = dsService.getDatasourceByName(name);
					if(null != ds)
						id = String.valueOf(ds.getId());
				} catch(Exception e){
				}
			}
		}
		return id;
	}
}
