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
 
 
package net.datenwerke.rs.scheduleasfile.client.scheduleasfile;

import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectInfoKeyInfoProvider;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportexporter.hooks.ExportExternalEntryProviderHook;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.filter.TeamspaceFilter;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.hookers.ExportSnippetProviderHook;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.hookers.ExportToTeamSpaceHooker;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.hookers.HandleExecutedReportFileHooker;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.hookers.TSMenuDownloadHooker;
import net.datenwerke.rs.scheduleasfile.client.scheduleasfile.objectinfo.ExecutedReportObjectInfoHooker;
import net.datenwerke.rs.scheduler.client.scheduler.hooks.ScheduleExportSnippetProviderHook;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hooks.ScheduledReportListFilter;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.hooks.GeneralReferenceHandlerHook;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.hooks.TsFavoriteMenuHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ScheduleAsFileUiStartup {

	@Inject
	public ScheduleAsFileUiStartup(
		HookHandlerService hookHandler,
		
		Provider<ExportSnippetProviderHook> exportSnippetProvider,
		Provider<HandleExecutedReportFileHooker> handleExecutedFileProvider,
		
		Provider<ExecutedReportObjectInfoHooker> executedReportObjectInfo,
		Provider<TSMenuDownloadHooker> tsMenuDownloadHooker, 
		
		Provider<TeamspaceFilter> teamSpaceFilter,
		
		final Provider<ExportToTeamSpaceHooker> exportToTeamSpaceHooker
		){
		
		hookHandler.attachHooker(ScheduledReportListFilter.class, teamSpaceFilter);
		
		/* export */
		hookHandler.attachHooker(ExportExternalEntryProviderHook.class, exportToTeamSpaceHooker);

		hookHandler.attachHooker(ScheduleExportSnippetProviderHook.class, exportSnippetProvider, HookHandlerService.PRIORITY_LOW);
		
		hookHandler.attachHooker(GeneralReferenceHandlerHook.class, handleExecutedFileProvider);
		
		hookHandler.attachHooker(TsFavoriteMenuHook.class, tsMenuDownloadHooker);
		
		/* object info */
		hookHandler.attachHooker(ObjectInfoKeyInfoProvider.class, executedReportObjectInfo);


	}
}
