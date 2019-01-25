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
 
 
package net.datenwerke.rs.tsreportarea.service.tsreportarea;

import net.datenwerke.eximport.hooks.ExporterProviderHook;
import net.datenwerke.eximport.hooks.ImporterProviderHook;
import net.datenwerke.gf.service.history.hooks.HistoryUrlBuilderHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.eximport.service.eximport.hooks.ExportAllHook;
import net.datenwerke.rs.eximport.service.eximport.hooks.ImportAllHook;
import net.datenwerke.rs.eximport.service.eximport.im.http.hooks.HttpImportPostProcessProviderHook;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.teamspace.service.teamspace.hooks.TeamSpaceAppDefinitionProviderHook;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.eventhandler.HandleReportForceRemoveEvent;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.eventhandler.HandleReportRemoveEvent;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.eventhandler.TeamSpaceRemovedCallback;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.eximport.TsDiskExporter;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.eximport.TsDiskImporter;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.eximport.hookers.ExportAllTsDiskHooker;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.eximport.hookers.ImportAllTsDiskHooker;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.eximport.hookers.ImportPostProcessorHooker;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.hookers.TsFavoriteHistoryUrlBuilderForReportsHooker;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.hookers.TsFavoriteHistoryUrlBuilderHooker;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.security.service.eventlogger.jpa.ForceRemoveEntityEvent;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TsDiskStartup {

	@Inject
	public TsDiskStartup(
		HookHandlerService hookHandler,
		Provider<TsDiskTeamSpaceAppDefinition> tsDiskAppDefinition,
		ImportPostProcessorHooker importPostProcessor,
		TsFavoriteHistoryUrlBuilderHooker urlBuilder,
		TsFavoriteHistoryUrlBuilderForReportsHooker urlBuilderForReports,
		
		Provider<TsDiskExporter> exporterProvider,
		Provider<TsDiskImporter> importerProvider,
		Provider<ExportAllTsDiskHooker> exportAllHooker,
		Provider<ImportAllTsDiskHooker> importAllHooker,
		
		EventBus eventBus,
		TeamSpaceRemovedCallback removedTeamSpaceCallback,
		HandleReportRemoveEvent reportRemoveEventHandler,
		HandleReportForceRemoveEvent reportForceRemoveEventHandler
		){
		
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, TeamSpace.class, removedTeamSpaceCallback);
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, Report.class, reportRemoveEventHandler);
		eventBus.attachObjectEventHandler(ForceRemoveEntityEvent.class, Report.class, reportForceRemoveEventHandler);
		
		
		hookHandler.attachHooker(TeamSpaceAppDefinitionProviderHook.class, new TeamSpaceAppDefinitionProviderHook(tsDiskAppDefinition));
		
		hookHandler.attachHooker(HttpImportPostProcessProviderHook.class, importPostProcessor);
		
		hookHandler.attachHooker(HistoryUrlBuilderHook.class, urlBuilder, HookHandlerService.PRIORITY_HIGH);
		hookHandler.attachHooker(HistoryUrlBuilderHook.class, urlBuilderForReports, HookHandlerService.PRIORITY_LOW);
		
		/* eximport */
		hookHandler.attachHooker(ExporterProviderHook.class, new ExporterProviderHook(exporterProvider));
		hookHandler.attachHooker(ImporterProviderHook.class, new ImporterProviderHook(importerProvider));
		hookHandler.attachHooker(ExportAllHook.class, exportAllHooker);
		hookHandler.attachHooker(ImportAllHook.class, importAllHooker);
	}
}
