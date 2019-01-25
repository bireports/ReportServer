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
 
 
package net.datenwerke.eximport;

import net.datenwerke.eximport.ex.enclosed.EnclosedEntityExporter;
import net.datenwerke.eximport.ex.enclosed.EnclosedObjectExporter;
import net.datenwerke.eximport.hooker.ByteArrayExporterHelperHooker;
import net.datenwerke.eximport.hooker.DateExporterHelperHooker;
import net.datenwerke.eximport.hooker.EnumExporterHelperHooker;
import net.datenwerke.eximport.hooker.StringExporterHelperHooker;
import net.datenwerke.eximport.hooks.BasicObjectExImporterHelperHook;
import net.datenwerke.eximport.hooks.ExImportIdProviderHook;
import net.datenwerke.eximport.hooks.ExporterProviderHook;
import net.datenwerke.eximport.hooks.ImporterProviderHook;
import net.datenwerke.eximport.ids.EntityIdProviderHooker;
import net.datenwerke.eximport.im.enclosed.EnclosedEntityImporter;
import net.datenwerke.eximport.im.enclosed.EnclosedObjectImporter;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ExImportStartup {

	@Inject
	public ExImportStartup(
		ByteArrayExporterHelperHooker byteArrayExporterHooker,
		EnumExporterHelperHooker enumExporterHooker,
		DateExporterHelperHooker dateExporterHooker,
		StringExporterHelperHooker stringExporterHooker,
		
		HookHandlerService hookHandler,
		Provider<EnclosedEntityExporter> enclosedEntityExporterProvider,
		Provider<EnclosedObjectExporter> enclosedObjectExporterProvider,
		
		Provider<EnclosedEntityImporter> enclosedEntityImporterProvider,
		Provider<EnclosedObjectImporter> enclosedObjectImporterProvider,
		
		Provider<EntityIdProviderHooker> entityIdProvider
		){
		hookHandler.attachHooker(BasicObjectExImporterHelperHook.class, byteArrayExporterHooker, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(BasicObjectExImporterHelperHook.class, enumExporterHooker, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(BasicObjectExImporterHelperHook.class, stringExporterHooker, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(BasicObjectExImporterHelperHook.class, dateExporterHooker, HookHandlerService.PRIORITY_LOW);
		
		hookHandler.attachHooker(ExporterProviderHook.class, new ExporterProviderHook(enclosedEntityExporterProvider), HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(ExporterProviderHook.class, new ExporterProviderHook(enclosedObjectExporterProvider), HookHandlerService.PRIORITY_LOWER);

		hookHandler.attachHooker(ImporterProviderHook.class, new ImporterProviderHook(enclosedEntityImporterProvider), HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(ImporterProviderHook.class, new ImporterProviderHook(enclosedObjectImporterProvider), HookHandlerService.PRIORITY_LOWER);

		
		hookHandler.attachHooker(ExImportIdProviderHook.class, entityIdProvider, HookHandlerService.PRIORITY_LOW);
	}
}
