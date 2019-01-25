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
 
 
package net.datenwerke.rs.core.service.reportmanager;

import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gf.service.history.hooks.HistoryUrlBuilderHook;
import net.datenwerke.gf.service.lifecycle.hooks.ConfigDoneHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.reportmanager.annotations.ReportServerReportTypes;
import net.datenwerke.rs.core.service.reportmanager.entities.ReportFolder;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.eventhandler.HandleDatasourceRemoveEventHandler;
import net.datenwerke.rs.core.service.reportmanager.hookers.ConfigureBaseReportViaRequestHooker;
import net.datenwerke.rs.core.service.reportmanager.hookers.ReportManagerHistoryUrlBuilderHooker;
import net.datenwerke.rs.core.service.reportmanager.hooks.ConfigureReportViaHistoryLocationHook;
import net.datenwerke.rs.core.service.reportmanager.hooks.ConfigureReportViaHttpRequestHook;
import net.datenwerke.rs.core.service.reportmanager.hooks.VariantCreatorHook;
import net.datenwerke.rs.core.service.reportmanager.metadata.hookers.VariantCreatedAdjustMetadataHooker;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;
import net.datenwerke.security.service.security.SecurityService;

public class ReportManagerStartup {

	@Inject
	public ReportManagerStartup(
		HookHandlerService hookHandler,
		EventBus eventBus,
		final Provider<SecurityService> securityServiceProvider,
		final @ReportServerReportTypes Provider<Set<Class<? extends Report>>> installedReportTypes,
		
		Provider<ConfigureBaseReportViaRequestHooker> baseReportRequestConfiguration,
		
		Provider<ReportManagerHistoryUrlBuilderHooker> reportManagerUrlBuilder,
		
		HandleDatasourceRemoveEventHandler handleDatasourceRemoveHandler,
		
		VariantCreatedAdjustMetadataHooker adjustMetadataHooker
		){
		
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, DatasourceDefinition.class, handleDatasourceRemoveHandler);
		
		hookHandler.attachHooker(ConfigureReportViaHttpRequestHook.class, baseReportRequestConfiguration);
		hookHandler.attachHooker(ConfigureReportViaHistoryLocationHook.class, baseReportRequestConfiguration);
		hookHandler.attachHooker(VariantCreatorHook.class, adjustMetadataHooker);
		
		hookHandler.attachHooker(HistoryUrlBuilderHook.class, reportManagerUrlBuilder);

		/* register security targets */
		hookHandler.attachHooker(ConfigDoneHook.class, new ConfigDoneHook() {
			
			@Override
			public void configDone() {
				/* secure folder */
				securityServiceProvider.get().registerSecurityTarget(ReportFolder.class);

				/* secure report entities */
				for(Class<? extends Report> rClass : installedReportTypes.get())
					securityServiceProvider.get().registerSecurityTarget(rClass);
			}
		});
	}
	
}
