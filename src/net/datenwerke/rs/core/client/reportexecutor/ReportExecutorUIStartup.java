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
 
 
package net.datenwerke.rs.core.client.reportexecutor;

import net.datenwerke.gf.client.dispatcher.hooks.DispatcherTakeOverHook;
import net.datenwerke.gf.client.history.HistoryUiService;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.helper.simpleform.ExportTypeSelectionProvider;
import net.datenwerke.rs.core.client.i18tools.hookers.LocalDecimalFormatUserProfileViewContentHooker;
import net.datenwerke.rs.core.client.reportexecutor.history.ReportExecutorHistoryCallback;
import net.datenwerke.rs.core.client.reportexecutor.hooks.ReportExecutorViewToolbarHook;
import net.datenwerke.rs.core.client.reportexecutor.objectinfo.ReportPreviewObjectInfo;
import net.datenwerke.rs.core.client.reportexecutor.reportdispatcher.ReportExecutorInlineDispatcher;
import net.datenwerke.rs.core.client.reportexecutor.ui.preview.hookers.PdfPreviewUserProfileViewContentHooker;
import net.datenwerke.rs.core.client.reportexecutor.variantstorer.ReportViewVariantStorerHooker;
import net.datenwerke.rs.core.client.reportexecutor.variantstorer.VariantStorerHook;
import net.datenwerke.rs.core.client.reportexporter.hookers.ExportExternalButtonHooker;
import net.datenwerke.rs.core.client.reportexporter.hookers.ExportViaMailHooker;
import net.datenwerke.rs.core.client.reportexporter.hookers.ReportViewExportButtonHooker;
import net.datenwerke.rs.core.client.reportexporter.hooks.ExportExternalEntryProviderHook;
import net.datenwerke.rs.core.client.urlview.hooks.UrlViewSpecialViewHandler;
import net.datenwerke.rs.core.client.userprofile.UserProfileViewContentHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 
 *
 */
public class ReportExecutorUIStartup {

	@Inject
	public ReportExecutorUIStartup(
		HookHandlerService hookHandler,
		
		HistoryUiService historyService,
		ReportExecutorHistoryCallback executorHistoryCallbackProvider,
		
		ReportViewExportButtonHooker exportButtonHooker,
		ExportExternalButtonHooker exportViaMailButtonHooker,
		Provider<ReportViewVariantStorerHooker> variantStorerHooker,
		
		Provider<ExportViaMailHooker> exportViaMailHooker,
		
		Provider<ExportTypeSelectionProvider> exportTypeSimpleFormProvider, 
		
		Provider<ReportPreviewObjectInfo> reportPreviewObjectInfo, 
		
		Provider<PdfPreviewUserProfileViewContentHooker> pdfPreviewUserProfileViewHooker, 
		Provider<LocalDecimalFormatUserProfileViewContentHooker> decimalFormatUserProfileViewHooker,
		
		ReportExecutorInlineDispatcher reportExecutorDispatcher
		){
		
		/* simpleform */
		hookHandler.attachHooker(FormFieldProviderHook.class, exportTypeSimpleFormProvider);
		
		/* dispatch */
		hookHandler.attachHooker(DispatcherTakeOverHook.class, reportExecutorDispatcher);
		
		/* attach object info hooks */
		hookHandler.attachHooker(
			UrlViewSpecialViewHandler.class,
			reportPreviewObjectInfo
			);
		
		/* report view toolbar */
		hookHandler.attachHooker(ReportExecutorViewToolbarHook.class, exportButtonHooker, HookHandlerService.PRIORITY_HIGH);
		hookHandler.attachHooker(ReportExecutorViewToolbarHook.class, exportViaMailButtonHooker, HookHandlerService.PRIORITY_HIGH + 2);
		hookHandler.attachHooker(ExportExternalEntryProviderHook.class, exportViaMailHooker);
		
		/* hook into user profile view card */
		hookHandler.attachHooker(UserProfileViewContentHook.class, pdfPreviewUserProfileViewHooker);
		hookHandler.attachHooker(UserProfileViewContentHook.class, decimalFormatUserProfileViewHooker);
		
		/* variant storer */
		hookHandler.attachHooker(VariantStorerHook.class, variantStorerHooker);
		hookHandler.getConfig(VariantStorerHook.class).setSingleton(true);
		
		/* configureHistory */
		historyService.addHistoryCallback(ReportExecutorUIModule.REPORT_EXECUTOR_HISTORY_TOKEN, executorHistoryCallbackProvider);
	}
}
