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
 
 
package net.datenwerke.rs.jxlsreport.client.jxlsreport;

import net.datenwerke.gf.client.managerhelper.hooks.MainPanelViewToolbarConfiguratorHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportexecutor.hooks.ReportViewHook;
import net.datenwerke.rs.core.client.reportexporter.hooks.ReportExporterExportReportHook;
import net.datenwerke.rs.core.client.reportmanager.hooks.ReportTypeConfigHook;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.execute.Jxls2Excel;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.execute.Jxls2Html;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.hookers.JxlsReportConfigHooker;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.hookers.JxlsReportFileDownloadToolbarConfiguratorHooker;
import net.datenwerke.rs.jxlsreport.client.jxlsreport.ui.JxlsReportPreviewViewFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class JxlsReportUiStartup {

	@Inject
	public JxlsReportUiStartup(HookHandlerService hookHandler,
		JxlsReportConfigHooker jxlsReportConfigHooker,
		JxlsReportPreviewViewFactory jxlsReportPreviewViewFactory,
		
		JxlsReportFileDownloadToolbarConfiguratorHooker jxlsReportFileDownloadToolbarConfiguratorHooker,
		
		Provider<Jxls2Excel> jxls2Excel,
		Provider<Jxls2Html> jxls2Html
		) {

		hookHandler.attachHooker(ReportTypeConfigHook.class, jxlsReportConfigHooker,50);
		hookHandler.attachHooker(ReportViewHook.class, new ReportViewHook(jxlsReportPreviewViewFactory), HookHandlerService.PRIORITY_LOW);

		hookHandler.attachHooker(MainPanelViewToolbarConfiguratorHook.class, jxlsReportFileDownloadToolbarConfiguratorHooker);

		hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(jxls2Excel));
		hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(jxls2Html), HookHandlerService.PRIORITY_LOWER);
		
		//			hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(crystal2Text), HookHandlerService.PRIORITY_LOW);
		//			hookHandler.attachHooker(ReportExporterExportReportHook.class, new ReportExporterExportReportHook(crystal2XML), HookHandlerService.PRIORITY_LOW);

	}
}
