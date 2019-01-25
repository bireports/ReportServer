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
 
 
package net.datenwerke.rs.jxlsreport.service.jxlsreport;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gf.service.upload.hooks.FileUploadHandlerHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.reportmanager.hooks.ReportEngineProviderHook;
import net.datenwerke.rs.core.service.reportmanager.hooks.ReportTypeProviderHook;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.hookers.BaseJxlsOutputGeneratorProvider;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.hookers.JxlsReportEngineProviderHooker;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.hookers.JxlsReportTypeProviderHooker;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.hookers.JxlsReportUploadHooker;
import net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.hooks.JxlsOutputGeneratorProviderHook;

public class JxlsReportStartup {

	@Inject
	public JxlsReportStartup(
		HookHandlerService hookHandlerService,
			
		JxlsReportEngineProviderHooker jxlsReportEngineProviderHooker,
		
		
		Provider<BaseJxlsOutputGeneratorProvider> baseOutputGenerators,
		
		JxlsReportUploadHooker jxlsReportUploadHooker
		) {
		
		hookHandlerService.attachHooker(ReportTypeProviderHook.class, new JxlsReportTypeProviderHooker());
		
		hookHandlerService.attachHooker(ReportEngineProviderHook.class, jxlsReportEngineProviderHooker);
		
		hookHandlerService.attachHooker(FileUploadHandlerHook.class, jxlsReportUploadHooker);
		
		/* base exporters */
		hookHandlerService.attachHooker(JxlsOutputGeneratorProviderHook.class, baseOutputGenerators, HookHandlerService.PRIORITY_LOW);
	}
	
}
