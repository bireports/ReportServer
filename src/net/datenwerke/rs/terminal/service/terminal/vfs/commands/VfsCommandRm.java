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
 
 
package net.datenwerke.rs.terminal.service.terminal.vfs.commands;

import java.util.Iterator;
import java.util.List;

import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.Argument;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.NonOptArgument;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.VirtualFileSystemDeamon;
import net.datenwerke.rs.terminal.service.terminal.vfs.exceptions.VFSException;
import net.datenwerke.rs.terminal.service.terminal.vfs.locale.VfsMessages;

public class VfsCommandRm implements TerminalCommandHook  {

	public static final String BASE_COMMAND = "rm";
	
	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}

	@CliHelpMessage(
		messageClass = VfsMessages.class,
		name = BASE_COMMAND,
		description = "commandRm_description",
		args = {
			@Argument(flag="r", description="commandRm_rArgument"),
			@Argument(flag="f", description="commandRm_fArgument")
		},
		nonOptArgs = {
			@NonOptArgument(name="dir", description="commandRm_dirArgument")
		}
	)
	@Override
	public CommandResult execute(CommandParser parser, TerminalSession session) {
		VirtualFileSystemDeamon vfs = session.getFileSystem();
		
		try {
			List<String> arguments = parser.getNonOptionArguments();
			if(arguments.size() < 1)
				throw new IllegalArgumentException();
			
			boolean recursive = parser.hasOption("r", "r?f?");
			boolean force = parser.hasOption("f", "r?f?");
			
			for(String arg : arguments){
				VFSLocation locations = vfs.getLocation(arg);
				
				if(locations.isVirtualLocation())
					throw new IllegalArgumentException("location is virtual");
				
				Iterator<VFSLocation> locationIt = locations.resolveWildcards(vfs).iterator();
				while(locationIt.hasNext())
					vfs.remove(locationIt.next(), recursive, force);
			}

			CommandResult result = new CommandResult();
			result.setCommitTransaction(true);
			return result;
		} catch (VFSException e) {
			return new CommandResult(e.getMessage());
		}
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
		autocompleteHelper.autocompleteBaseCommand(BASE_COMMAND);
	}

}
