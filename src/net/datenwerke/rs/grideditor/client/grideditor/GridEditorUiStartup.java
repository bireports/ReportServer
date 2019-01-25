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
 
 
package net.datenwerke.rs.grideditor.client.grideditor;

import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportexecutor.hooks.ReportViewHook;
import net.datenwerke.rs.core.client.reportexporter.hooks.ReportExporterExportReportHook;
import net.datenwerke.rs.core.client.reportmanager.hooks.ReportTypeConfigHook;
import net.datenwerke.rs.dashboard.client.dashboard.hooks.ReportDadgetExportHook;
import net.datenwerke.rs.dashboard.client.dashboard.security.DashboardViewGenericTargetIdentifier;
import net.datenwerke.rs.grideditor.client.grideditor.dashboard.ReportDadgetExporter;
import net.datenwerke.rs.grideditor.client.grideditor.execute.GridEditor2Excel;
import net.datenwerke.rs.grideditor.client.grideditor.hookers.GridEditorConfigHooker;
import net.datenwerke.rs.grideditor.client.grideditor.ui.GridEditorReportPreviewViewFactory;
import net.datenwerke.security.client.security.SecurityUIService;
import net.datenwerke.security.client.security.dto.ReadDto;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GridEditorUiStartup {

	@Inject
	public GridEditorUiStartup(
			final HookHandlerService hookHandler,
			final WaitOnEventUIService waitOnEventService,
			final SecurityUIService securityService,
			
			GridEditorConfigHooker gridEditorReportConfigHooker,
			GridEditorReportPreviewViewFactory gridEditorReportPreviewViewFactory,
			
			Provider<GridEditor2Excel> gridEditor2Excel,
			
			final Provider<ReportDadgetExporter> reportDadgetExporterProvider
			) {

			hookHandler.attachHooker(ReportTypeConfigHook.class, gridEditorReportConfigHooker,80);
			hookHandler.attachHooker(ReportViewHook.class, new ReportViewHook(gridEditorReportPreviewViewFactory), HookHandlerService.PRIORITY_LOW);

			hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(gridEditor2Excel));
			
			waitOnEventService.callbackOnEvent(SecurityUIService.REPORTSERVER_EVENT_GENERIC_RIGHTS_LOADED, new SynchronousCallbackOnEventTrigger() {
				
				public void execute(final WaitOnEventTicket ticket) {
					if(securityService.hasRight(DashboardViewGenericTargetIdentifier.class, ReadDto.class)){
						hookHandler.attachHooker(ReportDadgetExportHook.class, reportDadgetExporterProvider);
					}

					waitOnEventService.signalProcessingDone(ticket);
				}
			});
		}
}
