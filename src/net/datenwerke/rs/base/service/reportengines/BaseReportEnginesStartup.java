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
 
 
package net.datenwerke.rs.base.service.reportengines;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.reportengines.hookers.AdjustBaseReportForExecutionHooker;
import net.datenwerke.rs.base.service.reportengines.hookers.BaseReportEngineProviderHooker;
import net.datenwerke.rs.base.service.reportengines.hookers.BaseReportTypeProviderHooker;
import net.datenwerke.rs.base.service.reportengines.hookers.ConfigureBaseReportViaRequestHooker;
import net.datenwerke.rs.core.service.reportmanager.hooks.AdjustReportForExecutionHook;
import net.datenwerke.rs.core.service.reportmanager.hooks.ConfigureReportViaHistoryLocationHook;
import net.datenwerke.rs.core.service.reportmanager.hooks.ConfigureReportViaHttpRequestHook;
import net.datenwerke.rs.core.service.reportmanager.hooks.ReportEngineProviderHook;
import net.datenwerke.rs.core.service.reportmanager.hooks.ReportTypeProviderHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BaseReportEnginesStartup {

	@Inject
	public BaseReportEnginesStartup(
		HookHandlerService hookHandler,
		
		Provider<AdjustBaseReportForExecutionHooker> adjustReportForExecutionProvider,
		Provider<ConfigureBaseReportViaRequestHooker> adjustReportViaRequestProvider,
		Provider<BaseReportEngineProviderHooker> reportEngineProvider,
		Provider<BaseReportTypeProviderHooker> reportTypeProvider
		){
		
		hookHandler.attachHooker(AdjustReportForExecutionHook.class, adjustReportForExecutionProvider);
		hookHandler.attachHooker(ConfigureReportViaHttpRequestHook.class, adjustReportViaRequestProvider);
		hookHandler.attachHooker(ConfigureReportViaHistoryLocationHook.class, adjustReportViaRequestProvider);
		hookHandler.attachHooker(ReportEngineProviderHook.class, reportEngineProvider);
		hookHandler.attachHooker(ReportTypeProviderHook.class, reportTypeProvider);
	}
}
