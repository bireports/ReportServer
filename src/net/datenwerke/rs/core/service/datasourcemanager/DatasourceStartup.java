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

import java.util.Set;

import net.datenwerke.gf.service.history.hooks.HistoryUrlBuilderHook;
import net.datenwerke.gf.service.lifecycle.hooks.ConfigDoneHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.datasourcemanager.annotations.ReportServerDatasourceDefinitions;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceFolder;
import net.datenwerke.rs.core.service.datasourcemanager.eventhandlers.HandleDatasourceForceRemoveEventHandler;
import net.datenwerke.rs.core.service.datasourcemanager.history.DatasourceManagerHistoryUrlBuilderHooker;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.security.service.eventlogger.jpa.ForceRemoveEntityEvent;
import net.datenwerke.security.service.security.SecurityService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DatasourceStartup {

	@Inject
	public DatasourceStartup(
		HookHandlerService hookHandler,
		EventBus eventBus,
		final Provider<SecurityService> securityServiceProvider,
		final @ReportServerDatasourceDefinitions Provider<Set<Class<? extends DatasourceDefinition>>> installedDataSourceDefinitions,
		
		Provider<DatasourceManagerHistoryUrlBuilderHooker> datasourceManagerUrlBuilder,
		HandleDatasourceForceRemoveEventHandler handleDatasourceForceRemoveHandler
		){
		
		eventBus.attachObjectEventHandler(ForceRemoveEntityEvent.class, DatasourceDefinition.class, handleDatasourceForceRemoveHandler);
		
		/* history */
		hookHandler.attachHooker(HistoryUrlBuilderHook.class, datasourceManagerUrlBuilder);
		
		/* register security targets */
		hookHandler.attachHooker(ConfigDoneHook.class, new ConfigDoneHook() {
			
			@Override
			public void configDone() {
				/* secure folder */
				securityServiceProvider.get().registerSecurityTarget(DatasourceFolder.class);

				
				/* secure datasource definition entities */
				for(Class<? extends DatasourceDefinition> dClass : installedDataSourceDefinitions.get())
					securityServiceProvider.get().registerSecurityTarget(dClass);
			}
		});
	}
}
