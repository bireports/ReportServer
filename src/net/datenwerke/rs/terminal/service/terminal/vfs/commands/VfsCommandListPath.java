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
 
 
package net.datenwerke.rs.terminal.service.terminal.vfs.commands;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.Argument;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.NonOptArgument;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResultList;
import net.datenwerke.rs.terminal.service.terminal.objresolver.ObjectResolverDeamon;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.VirtualFileSystemDeamon;
import net.datenwerke.rs.terminal.service.terminal.vfs.locale.VfsMessages;
import net.datenwerke.security.service.security.rights.Read;

public class VfsCommandListPath implements TerminalCommandHook  {

	public static final String BASE_COMMAND = "listpath";
	
	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}

	@CliHelpMessage(
		messageClass = VfsMessages.class,
		name = BASE_COMMAND,
		description = "commandListpath_description",
		args = {
			@Argument(flag="i", description="commandListpath_iflag")
		},
		nonOptArgs = {
			@NonOptArgument(name="arguments", description="commandListpath_objectarg")
		}
	)
	@Override
	public CommandResult execute(CommandParser parser, TerminalSession session) {
		VirtualFileSystemDeamon vfs = session.getFileSystem();
		ObjectResolverDeamon objectResolver = session.getObjectResolver();
		
		List<String> arguments = parser.getNonOptionArguments();
		if(arguments.isEmpty())
			throw new IllegalArgumentException();
		String locationStr = arguments.get(0);
		
		Collection<Object> objects;
		try {
			objects = objectResolver.getObjects(locationStr, Read.class);
		} catch (ObjectResolverException e) {
			throw new IllegalArgumentException(e);
		}
		
		boolean iFlag = parser.hasOption("i");
		
		List<String> paths = new ArrayList<String>();
		for(Object obj : objects){
			VFSLocation location = vfs.getLocationFor(obj);
			if(iFlag)
				paths.add(location.getAbsolutePath());
			else
				paths.add(location.prettyPrint());
		}
		
		CommandResultList list = new CommandResultList(paths);
		list.setDenyBreakUp(true);
		return new CommandResult(list);
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
		autocompleteHelper.autocompleteBaseCommand(BASE_COMMAND);
	}

}
