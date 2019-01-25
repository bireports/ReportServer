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
 
 
package net.datenwerke.rs.eximport.service;

import net.datenwerke.gf.service.upload.hooks.FileUploadHandlerHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.eximport.service.eximport.hookers.ImportUploadHooker;
import net.datenwerke.rs.eximport.service.eximport.terminal.commands.ExportAllCommand;
import net.datenwerke.rs.eximport.service.eximport.terminal.commands.ExportCommand;
import net.datenwerke.rs.eximport.service.eximport.terminal.commands.ImportCommand;
import net.datenwerke.rs.eximport.service.eximport.terminal.hooks.ExportSubCommandHook;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class RsExImportStartup {

	@Inject
	public RsExImportStartup(
		HookHandlerService hookHandler,
		
		Provider<ExportCommand> exportCommand,
		Provider<ImportCommand> importCommand,
		
		Provider<ExportAllCommand> exportAllCommand,
		
		Provider<ImportUploadHooker> importUploader
		){
		
		hookHandler.attachHooker(TerminalCommandHook.class, exportCommand);
		hookHandler.attachHooker(TerminalCommandHook.class, importCommand);
		
		hookHandler.attachHooker(ExportSubCommandHook.class, exportAllCommand);
		
		hookHandler.attachHooker(FileUploadHandlerHook.class, importUploader);
	}
}
