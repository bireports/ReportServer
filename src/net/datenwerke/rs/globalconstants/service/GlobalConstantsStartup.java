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
 
 
package net.datenwerke.rs.globalconstants.service;

import net.datenwerke.eximport.hooks.ExporterProviderHook;
import net.datenwerke.eximport.hooks.ImporterProviderHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.reportmanager.hooks.ParameterSetReplacementProviderHook;
import net.datenwerke.rs.eximport.service.eximport.hooks.ExportAllHook;
import net.datenwerke.rs.eximport.service.eximport.hooks.ImportAllHook;
import net.datenwerke.rs.globalconstants.service.globalconstants.eximport.GlobalConstantExporter;
import net.datenwerke.rs.globalconstants.service.globalconstants.eximport.GlobalConstantImporter;
import net.datenwerke.rs.globalconstants.service.globalconstants.eximport.hookers.ExportAllGlobalConstantsHooker;
import net.datenwerke.rs.globalconstants.service.globalconstants.eximport.hookers.ImportAllGlobalConstantsHooker;
import net.datenwerke.rs.globalconstants.service.globalconstants.hookers.ParameterSetReplacementProviderHooker;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class GlobalConstantsStartup {

	@Inject
	public GlobalConstantsStartup(
		HookHandlerService hookHandler,
		Provider<ParameterSetReplacementProviderHooker> replacementProvider,
		
		Provider<GlobalConstantExporter> exporterProvider,
		Provider<GlobalConstantImporter> importerProvider,
		
		Provider<ExportAllGlobalConstantsHooker> exportAllGlobalConstants,
		Provider<ImportAllGlobalConstantsHooker> importAllGlobalConstants
		
		){
		
		hookHandler.attachHooker(ParameterSetReplacementProviderHook.class, replacementProvider);
		
		hookHandler.attachHooker(ExporterProviderHook.class, new ExporterProviderHook(exporterProvider));
		hookHandler.attachHooker(ImporterProviderHook.class, new ImporterProviderHook(importerProvider));
		hookHandler.attachHooker(ExportAllHook.class, exportAllGlobalConstants);
		hookHandler.attachHooker(ImportAllHook.class, importAllGlobalConstants);
	}
}
