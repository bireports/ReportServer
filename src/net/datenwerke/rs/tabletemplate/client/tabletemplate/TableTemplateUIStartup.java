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
 
 
package net.datenwerke.rs.tabletemplate.client.tabletemplate;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.hooks.FilterViewEnhanceToolbarHook;
import net.datenwerke.rs.core.client.reportexporter.hooks.ReportExporterExportReportHook;
import net.datenwerke.rs.enterprise.client.EnterpriseCheckUiModule;
import net.datenwerke.rs.enterprise.client.EnterpriseUiService;
import net.datenwerke.rs.tabletemplate.client.tabletemplate.hookers.EnhanceFilterToolbarHooker;
import net.datenwerke.rs.tabletemplate.client.tabletemplate.hookers.ReportExportToTemplateHooker;

public class TableTemplateUIStartup {

	@Inject
	public TableTemplateUIStartup(
		final HookHandlerService hookHandler,
		final WaitOnEventUIService waitOnEventService,
		final EnterpriseUiService enterpriseUiService,
		
		final Provider<ReportExportToTemplateHooker> exportToTemplateHooker,
		final Provider<EnhanceFilterToolbarHooker> enhanceFilterToolbarProvider
		){
		
		
		waitOnEventService.callbackOnEvent(EnterpriseCheckUiModule.REPORTSERVER_ENTERPRISE_DETERMINED_AFTER_LOGIN, new SynchronousCallbackOnEventTrigger(){
			public void execute(final WaitOnEventTicket ticket) {
				if(enterpriseUiService.isEnterprise()){
					hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(exportToTemplateHooker), HookHandlerService.PRIORITY_LOWER);
					hookHandler.attachHooker(FilterViewEnhanceToolbarHook.class, enhanceFilterToolbarProvider, HookHandlerService.PRIORITY_LOW);
				}
				waitOnEventService.signalProcessingDone(ticket);
			}
		});
	}
}
