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
 
 
package net.datenwerke.rs.base.service.reportengines.jasper;

import net.datenwerke.gf.service.download.hooks.FileDownloadHandlerHook;
import net.datenwerke.gf.service.fileselection.hooks.FileSelectionHandlerHook;
import net.datenwerke.gf.service.upload.hooks.FileUploadHandlerHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.reportengines.jasper.hookers.BaseJasperOutputGeneratorProvider;
import net.datenwerke.rs.base.service.reportengines.jasper.hookers.JasperReportJRXMLDownloadHooker;
import net.datenwerke.rs.base.service.reportengines.jasper.hookers.JasperReportMasterUploadHooker;
import net.datenwerke.rs.base.service.reportengines.jasper.hookers.JasperReportSubreportHandler;
import net.datenwerke.rs.base.service.reportengines.jasper.hooks.JasperOutputGeneratorProviderHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class JasperReportStartup {

	@Inject
	public JasperReportStartup(
		JasperReportMasterUploadHooker jasperReportMasterUploadHooker,
		JasperReportSubreportHandler subreportHandler,
		JasperReportJRXMLDownloadHooker jrxmlDownloadHandler,
		
		Provider<BaseJasperOutputGeneratorProvider> baseOutputGenerators,
		
		HookHandlerService hookHandlerService
		){
	
		hookHandlerService.attachHooker(FileUploadHandlerHook.class, jasperReportMasterUploadHooker);
		
		hookHandlerService.attachHooker(FileSelectionHandlerHook.class, subreportHandler);
		
		hookHandlerService.attachHooker(FileDownloadHandlerHook.class, jrxmlDownloadHandler);
		
		/* base exporters */
		hookHandlerService.attachHooker(JasperOutputGeneratorProviderHook.class, baseOutputGenerators, HookHandlerService.PRIORITY_LOW);
	}
}
