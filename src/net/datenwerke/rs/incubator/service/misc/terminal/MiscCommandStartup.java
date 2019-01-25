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
 
 
package net.datenwerke.rs.incubator.service.misc.terminal;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.eximport.service.eximport.terminal.hooks.ImportSubCommandHook;
import net.datenwerke.rs.incubator.service.misc.terminal.commands.ImportAllCommand;
import net.datenwerke.rs.incubator.service.misc.terminal.commands.SqlTerminalCommand;
import net.datenwerke.rs.incubator.service.misc.terminal.internaldb.ClearInternalDbCommand;
import net.datenwerke.rs.incubator.service.misc.terminal.virtualjrxmlprovider.MasterJrxmlContentProvider;
import net.datenwerke.rs.incubator.service.misc.terminal.virtualjrxmlprovider.SubreportJrxmlContentProvider;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;
import net.datenwerke.rs.terminal.service.terminal.vfs.hooks.VirtualContentProviderHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class MiscCommandStartup {

	@Inject
	public MiscCommandStartup(
		HookHandlerService hookHandler,
		Provider<SqlTerminalCommand> sqlCommand,
		
		Provider<ImportAllCommand> importAllCommand,
		
		Provider<MasterJrxmlContentProvider> masterJrxmlProvider,
		Provider<SubreportJrxmlContentProvider> subreportsJrxmlProvider,
		
		Provider<ClearInternalDbCommand> clearInternalDbCommand
		
//		Provider<TerminalExportContentProvider> exportContentProvider
		){
		
		hookHandler.attachHooker(TerminalCommandHook.class, sqlCommand);
		
		hookHandler.attachHooker(ImportSubCommandHook.class, importAllCommand);
		
		hookHandler.attachHooker(TerminalCommandHook.class, clearInternalDbCommand);
		
		
		hookHandler.attachHooker(VirtualContentProviderHook.class, masterJrxmlProvider);
		hookHandler.attachHooker(VirtualContentProviderHook.class, subreportsJrxmlProvider);
		
//		hookHandler.attachHooker(VirtualContentProviderHook.class, exportContentProvider);
	}
}
