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
 
 
package net.datenwerke.rs.incubator.service.misc.terminal.commands;

import net.datenwerke.eximport.ExportDataProviderImpl;
import net.datenwerke.eximport.ImportService;
import net.datenwerke.eximport.im.ImportConfig;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.eximport.service.eximport.hooks.ImportAllHook;
import net.datenwerke.rs.eximport.service.eximport.terminal.hooks.ImportSubCommandHook;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.security.service.security.rights.Read;

import com.google.inject.Inject;

public class ImportAllCommand implements ImportSubCommandHook {

	public static final String BASE_COMMAND = "all";
	
	private final ImportService importService;
	private final HookHandlerService hookHandler;

	@Inject
	public ImportAllCommand(
		ImportService importService,
		HookHandlerService hookHandler
		){
		
		/* store objects */
		this.importService = importService;
		this.hookHandler = hookHandler;
	}
	
	@Override
	public String getBaseCommand() {
		return BASE_COMMAND;
	}
	
	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}

	@Override
	public CommandResult execute(CommandParser parser, TerminalSession session) throws ObjectResolverException {
		String args = parser.getArgumentString();
		final FileServerFile file = (FileServerFile) session.getObjectResolver().getObject(args.trim(), Read.class);
		
		ImportConfig config;
		try {
			config = new ImportConfig(new ExportDataProviderImpl(file.getData()));
		} catch(Exception e){
			throw new IllegalArgumentException(e);
		}
		
		for(ImportAllHook exporter : hookHandler.getHookers(ImportAllHook.class)){
			exporter.configure(config);
		}
		
		importService.importData(config);
		
		return new CommandResult();
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper,
			TerminalSession session) {
		
	}


}
