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
 
 
package net.datenwerke.rs.base.client.reportengines.table.columnfilter;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.hookers.PreviewEnhancerHook;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.hookers.ToolbarEnhancerEditFilter;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.hooks.FilterViewEnhanceToolbarHook;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.propertywidgets.FilterViewFactory;
import net.datenwerke.rs.base.client.reportengines.table.cubeconfig.CubeConfigViewFactory;
import net.datenwerke.rs.base.client.reportengines.table.hooks.TableReportPreviewCellEnhancerHook;
import net.datenwerke.rs.core.client.reportexecutor.hooks.ReportViewHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 
 *
 */
public class FilterUIStartup {
	
	@Inject
	public FilterUIStartup(
		HookHandlerService hookHandler,
		FilterViewFactory filterWidgetFactory,
		CubeConfigViewFactory cubeViewFactory,
		PreviewEnhancerHook previewEnhancer,
		
		Provider<ToolbarEnhancerEditFilter> toolbarEnhancerEditFilterProvider
		){
		
		/* attach hooks */
		hookHandler.attachHooker(
				ReportViewHook.class,
				new ReportViewHook(filterWidgetFactory),
				HookHandlerService.PRIORITY_MEDIUM);
		
		hookHandler.attachHooker(
				ReportViewHook.class,
				new ReportViewHook(cubeViewFactory),
				HookHandlerService.PRIORITY_MEDIUM);
		
		/* preview */
		hookHandler.attachHooker(TableReportPreviewCellEnhancerHook.class, previewEnhancer);
		
		/* toolbar */
		hookHandler.attachHooker(FilterViewEnhanceToolbarHook.class, toolbarEnhancerEditFilterProvider);
	}

}
