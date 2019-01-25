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

import java.util.List;

import com.google.inject.Inject;

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
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.SecurityTarget;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.security.rights.Write;

public class VfsCommandCp implements TerminalCommandHook{
	
	public static final String BASE_COMMAND = "cp";
	
	private final SecurityService securityService;
	
	@Inject
	public VfsCommandCp(
		SecurityService securityService
		){
		
		/* store objects */
		this.securityService = securityService;
	}
	
	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}

	@CliHelpMessage(
		messageClass = VfsMessages.class,
		name = BASE_COMMAND,
		description = "commandCp_description",
		args = {
			@Argument(flag="r", description="commandCp_rFlag")
		},
		nonOptArgs = {
			@NonOptArgument(name="sourcefile", description="commandCp_source"),
			@NonOptArgument(name="targetfile", description="commandCp_target")
		}
	)
	@Override
	public CommandResult execute(CommandParser parser, TerminalSession session) {
		VirtualFileSystemDeamon vfs = session.getFileSystem();
		
		VFSLocation workingDirectory = vfs.getCurrentLocation();
		
		List<String> arguments = parser.getNonOptionArguments();
		if(arguments.size() != 2)
			throw new IllegalArgumentException();
		
		String sourceStr = arguments.get(0);
		String targetStr = arguments.get(1);

		try{
			/* load source */
			VFSLocation source = vfs.getLocation(sourceStr);
			if(source.isVirtualLocation())
				throw new IllegalArgumentException("Source is virtual location");
			if(! source.exists() && ! source.isWildcardLocation())
				throw new IllegalArgumentException("Could not find " + sourceStr);
			
			String targetFileName = null;
			
			VFSLocation target = vfs.getLocation(targetStr);
			
			if(target.exists() && ! target.isFolder())
				throw new IllegalArgumentException("Target file already exists.");
			
			if(! target.exists()){
				targetFileName = target.getPathHelper().getLastPathway();
				target = target.getParentLocation();
			}
			
			if(! target.isFolder() || ! target.exists())
				throw new IllegalArgumentException("Target folder does not exist.");
			if(target.isVirtualLocation())
				throw new IllegalArgumentException("Target is virtual location");

			/* load target object */
			Object targetFolder = target.getObject();
			if(targetFolder instanceof SecurityService)
				if(! securityService.checkRights((SecurityTarget)targetFolder, Read.class, Write.class))
					throw new ViolatedSecurityException();
			
			boolean deepCopy = parser.hasOption("r");
			
			/* perform copy */
			List<VFSLocation> copiedFileLocations = target.getFilesystemManager().copyFilesTo(source, target, deepCopy);
			
			if(null != targetFileName && copiedFileLocations.size() != 1)
				throw new IllegalArgumentException("Cannot copy multiple files into one file.");
			
			if(null != targetFileName){
				VFSLocation copiedFile = copiedFileLocations.get(0);
				copiedFile.rename(targetFileName);
			} else {
				if(target.equals(workingDirectory)){
					for(VFSLocation loc : copiedFileLocations){
						String name = loc.getFilesystemManager().getNameFor(loc);
						loc.rename(name+ " (copy)");
					}
				}
			}
		
			return new CommandResult();
		} catch(VFSException e){
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
		autocompleteHelper.autocompleteBaseCommand(BASE_COMMAND);
	}
}
