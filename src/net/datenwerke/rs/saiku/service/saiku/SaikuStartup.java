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
 
 
package net.datenwerke.rs.saiku.service.saiku;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.server.reportexport.hooks.ReportExportViaSessionHook;
import net.datenwerke.rs.core.service.datasourcemanager.hooks.DatasourceProviderHook;
import net.datenwerke.rs.core.service.reportmanager.hooks.ReportEngineProviderHook;
import net.datenwerke.rs.core.service.reportmanager.hooks.ReportTypeProviderHook;
import net.datenwerke.rs.core.service.reportmanager.hooks.VariantCreatorHook;
import net.datenwerke.rs.core.service.reportmanager.hooks.VariantToBeStoredHook;
import net.datenwerke.rs.saiku.service.hooker.MondrianDatasourceProviderHooker;
import net.datenwerke.rs.saiku.service.hooker.ReportExportViaSessionHooker;
import net.datenwerke.rs.saiku.service.hooker.SaikuReportTypeProviderHooker;
import net.datenwerke.rs.saiku.service.hooker.VariantCreatedAdjustSaikuQueryHooker;
import net.datenwerke.rs.saiku.service.hooker.VariantStoreHooker;
import net.datenwerke.rs.saiku.service.saiku.reportengine.hookers.BaseSaikuOutputGeneratorProvider;
import net.datenwerke.rs.saiku.service.saiku.reportengine.hookers.SaikuReportEngineProviderHooker;
import net.datenwerke.rs.saiku.service.saiku.reportengine.hooks.SaikuOutputGeneratorProviderHook;

public class SaikuStartup {

	@Inject
	public SaikuStartup(
			HookHandlerService hookHandler, 
			MondrianDatasourceProviderHooker mondrianDatasourceProvider, 
			VariantStoreHooker variantStoreHooker, 
			VariantCreatedAdjustSaikuQueryHooker adjustSaikuQueryHooker,
			SaikuReportEngineProviderHooker saikuReportEngineProviderHooker, 
			ReportExportViaSessionHooker reportExportViaSessionHooker,
			
			SaikuReportTypeProviderHooker saikuReportTypeProviderHooker,
			
			Provider<BaseSaikuOutputGeneratorProvider> baseOutputGenerators
			){
		
		hookHandler.attachHooker(ReportEngineProviderHook.class, saikuReportEngineProviderHooker);
		hookHandler.attachHooker(DatasourceProviderHook.class, mondrianDatasourceProvider);
		hookHandler.attachHooker(VariantToBeStoredHook.class, variantStoreHooker);
		hookHandler.attachHooker(VariantCreatorHook.class, adjustSaikuQueryHooker);
		hookHandler.attachHooker(ReportExportViaSessionHook.class, reportExportViaSessionHooker);
		
		hookHandler.attachHooker(ReportTypeProviderHook.class, saikuReportTypeProviderHooker);
		
		/* base exporters */
		hookHandler.attachHooker(SaikuOutputGeneratorProviderHook.class, baseOutputGenerators, HookHandlerService.PRIORITY_LOW);
	}
}
