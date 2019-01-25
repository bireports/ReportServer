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
 
 
package net.datenwerke.rs.reportdoc.client;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportexecutor.hooks.ReportExecutorViewToolbarHook;
import net.datenwerke.rs.core.client.urlview.hooks.UrlViewSpecialViewHandler;
import net.datenwerke.rs.reportdoc.client.hooker.ReportDocumentationObjectInfo;
import net.datenwerke.rs.reportdoc.client.hooker.ReportViewDocumentationHooker;

/**
 * 
 *
 */
public class ReportDocumentationUIStartup {

	@Inject
	public ReportDocumentationUIStartup(
		HookHandlerService hookHandler,
		ReportViewDocumentationHooker reportViewDocumentationHooker,
		
		Provider<ReportDocumentationObjectInfo> repordDocumentationObjectInfo 
		
		){
		
		hookHandler.attachHooker(ReportExecutorViewToolbarHook.class, reportViewDocumentationHooker, HookHandlerService.PRIORITY_HIGH);
		
		/* attach object info hooks */
		hookHandler.attachHooker(
			UrlViewSpecialViewHandler.class,
			repordDocumentationObjectInfo
			);
	}
}
