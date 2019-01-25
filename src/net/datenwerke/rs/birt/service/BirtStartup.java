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
 
 
package net.datenwerke.rs.birt.service;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gf.service.upload.hooks.FileUploadHandlerHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.birt.service.datasources.birtreport.hookers.BirtReportDatasourceProviderHooker;
import net.datenwerke.rs.birt.service.reportengine.hookers.BaseBirtOutputGeneratorProvider;
import net.datenwerke.rs.birt.service.reportengine.hookers.BirtReportEngineProviderHooker;
import net.datenwerke.rs.birt.service.reportengine.hookers.BirtReportTypeProviderHooker;
import net.datenwerke.rs.birt.service.reportengine.hookers.BirtReportUploadHooker;
import net.datenwerke.rs.birt.service.reportengine.hooks.BirtOutputGeneratorProviderHook;
import net.datenwerke.rs.birt.service.reportengine.terminal.BirtCommand;
import net.datenwerke.rs.birt.service.reportengine.terminal.BirtShutdownCommand;
import net.datenwerke.rs.birt.service.reportengine.terminal.BirtSubCommandHook;
import net.datenwerke.rs.core.service.datasourcemanager.hooks.DatasourceProviderHook;
import net.datenwerke.rs.core.service.reportmanager.hooks.ReportEngineProviderHook;
import net.datenwerke.rs.core.service.reportmanager.hooks.ReportTypeProviderHook;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;

public class BirtStartup {
	
	@Inject
	public BirtStartup(
			HookHandlerService hookHandlerService,
			BirtReportUploadHooker birtReportUploadHooker, 
			BirtReportEngineProviderHooker birtReportEngineProviderHooker, 
			
			Provider<BaseBirtOutputGeneratorProvider> baseOutputGenerators,
			
			Provider<BirtCommand> birtCommand,
			Provider<BirtShutdownCommand> birtShutdownCommand
			
	){
		hookHandlerService.attachHooker(FileUploadHandlerHook.class ,birtReportUploadHooker);
		hookHandlerService.attachHooker(ReportEngineProviderHook.class, birtReportEngineProviderHooker);
		
		hookHandlerService.attachHooker(ReportTypeProviderHook.class, new BirtReportTypeProviderHooker());
		hookHandlerService.attachHooker(DatasourceProviderHook.class, new BirtReportDatasourceProviderHooker());
		
		hookHandlerService.attachHooker(TerminalCommandHook.class, birtCommand);
		hookHandlerService.attachHooker(BirtSubCommandHook.class, birtShutdownCommand);
		
		/* base exporters */
		hookHandlerService.attachHooker(BirtOutputGeneratorProviderHook.class, baseOutputGenerators, HookHandlerService.PRIORITY_LOW);
	}


}
