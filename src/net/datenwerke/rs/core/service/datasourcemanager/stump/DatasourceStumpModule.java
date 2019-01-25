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
 
 
package net.datenwerke.rs.core.service.datasourcemanager.stump;

import java.util.HashSet;
import java.util.Set;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.datasourcemanager.DatasourceService;
import net.datenwerke.rs.core.service.datasourcemanager.DatasourceServiceImpl;
import net.datenwerke.rs.core.service.datasourcemanager.DatasourceStartup;
import net.datenwerke.rs.core.service.datasourcemanager.annotations.DefaultDatasource;
import net.datenwerke.rs.core.service.datasourcemanager.annotations.ReportServerDatasourceDefinitions;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.hooks.DatasourceProviderHook;
import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Scopes;

public class DatasourceStumpModule extends AbstractReportServerModule {

		
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
	
	@Provides @DefaultDatasource
	public String provideDefaultDatasourceId(){
		return null;
	}
}
