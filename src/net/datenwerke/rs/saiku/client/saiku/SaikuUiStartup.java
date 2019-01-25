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
 
 
package net.datenwerke.rs.saiku.client.saiku;


import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.datasourcemanager.hooks.DatasourceDefinitionConfigProviderHook;
import net.datenwerke.rs.core.client.reportexecutor.hooks.ReportViewHook;
import net.datenwerke.rs.core.client.reportexporter.hooks.ReportExporterExportReportHook;
import net.datenwerke.rs.core.client.reportmanager.hooks.ReportTypeConfigHook;
import net.datenwerke.rs.dashboard.client.dashboard.hooks.ReportDadgetExportHook;
import net.datenwerke.rs.dashboard.client.dashboard.security.DashboardViewGenericTargetIdentifier;
import net.datenwerke.rs.saiku.client.datasource.MondrianDatasourceConfigProviderHooker;
import net.datenwerke.rs.saiku.client.saiku.hookers.ReportDadgetSaikuExportHooker;
import net.datenwerke.rs.saiku.client.saiku.hookers.SaikuReportConfigHooker;
import net.datenwerke.rs.saiku.client.saiku.reportengines.Saiku2CSV;
import net.datenwerke.rs.saiku.client.saiku.reportengines.Saiku2ChartHTML;
import net.datenwerke.rs.saiku.client.saiku.reportengines.Saiku2Excel;
import net.datenwerke.rs.saiku.client.saiku.reportengines.Saiku2HTML;
import net.datenwerke.rs.saiku.client.saiku.reportengines.Saiku2PDF;
import net.datenwerke.rs.saiku.client.saiku.ui.SaikuReportPreviewViewFactory;
import net.datenwerke.rs.saikupivot.client.table.SaikuTableReportPreviewViewFactory;
import net.datenwerke.security.client.security.SecurityUIService;
import net.datenwerke.security.client.security.dto.ReadDto;

public class SaikuUiStartup {
	
	@Inject
	public SaikuUiStartup(
		final HookHandlerService hookHandler,
		final WaitOnEventUIService waitOnEventService,
		final SecurityUIService securityService,
		
		SaikuReportConfigHooker saikuReportConfigHooker,
		SaikuReportPreviewViewFactory saikuReportPreviewViewFactory,
		
		MondrianDatasourceConfigProviderHooker configProvider,
		
		Provider<Saiku2Excel> saiku2Excel,
		Provider<Saiku2PDF> saiku2PDF,
		Provider<Saiku2CSV> saiku2CSV, 
		Provider<Saiku2HTML> saiku2HTML,
		Provider<Saiku2ChartHTML> saiku2ChartHTML,
		
		final Provider<ReportDadgetSaikuExportHooker> reportDadgetSaikuExporterProvider
		
		) {
		
		hookHandler.attachHooker(ReportTypeConfigHook.class, saikuReportConfigHooker, 60);
		hookHandler.attachHooker(ReportViewHook.class, new ReportViewHook(saikuReportPreviewViewFactory), HookHandlerService.PRIORITY_LOW);
		
		hookHandler.attachHooker(DatasourceDefinitionConfigProviderHook.class, configProvider, 30);
		
		hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(saiku2Excel));
		hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(saiku2PDF), HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(saiku2CSV), HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(saiku2HTML), HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(saiku2ChartHTML), HookHandlerService.PRIORITY_LOW);
		
		/* request callback after login and check for rights */
		waitOnEventService.callbackOnEvent(SecurityUIService.REPORTSERVER_EVENT_GENERIC_RIGHTS_LOADED, new SynchronousCallbackOnEventTrigger() {
			
			public void execute(final WaitOnEventTicket ticket) {
				if(securityService.hasRight(DashboardViewGenericTargetIdentifier.class, ReadDto.class)){
					hookHandler.attachHooker(ReportDadgetExportHook.class, reportDadgetSaikuExporterProvider);
				}

				waitOnEventService.signalProcessingDone(ticket);
			}
		});
	}

}
