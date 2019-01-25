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
 
 
package net.datenwerke.rs.fileserver.service.fileserver.terminal.commands;

import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.fileserver.service.fileserver.vfs.FileServerVfs;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.exceptions.TerminalException;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.exceptions.VFSException;
import net.datenwerke.rs.terminal.service.terminal.vfs.helper.PathHelper;

import com.google.inject.Inject;

public class CreateTextFileCommand implements TerminalCommandHook {


	private static final String BASE_COMMAND = "createTextFile";

	private final FileServerService fileService;
	
	@Inject
	public CreateTextFileCommand(
		FileServerService fileService	
		){
	
		/* store objects */
		this.fileService = fileService;
	}


	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}

	@Override
	public CommandResult execute(CommandParser parser, TerminalSession session) throws TerminalException {
		String completePath = parser.getArgumentNr(1);
		
		PathHelper helper = new PathHelper(completePath);
		String path = helper.getPath();
		String fileName = helper.getLastPathway();

		VFSLocation location;
		if("".equals(path))
			location = session.getFileSystem().getCurrentLocation();
		else {
			try {
				location = session.getFileSystem().getLocation(path);
			} catch (VFSException e1) {
				throw new IllegalArgumentException("illegal path: " + path);
			}
		}
		
		if(! (location.getFilesystemManager() instanceof FileServerVfs))
			return new CommandResult("wrong filesystem");
		
		AbstractFileServerNode parent = null;
		try {
			parent = (AbstractFileServerNode) location.getObject();
		} catch (VFSException e) {
			return new CommandResult(e.getMessage());
		}
	
		FileServerFile textFile = new FileServerFile();
		textFile.setName(fileName);
		if(null != parent)
			parent.addChild(textFile);
		
		fileService.persist(textFile);
		if(null != parent)
			fileService.merge(parent);
		
		CommandResult result = new CommandResult("file created");
		EditCommandResultExtension ext = new EditCommandResultExtension();
		ext.setFile(textFile);
		ext.setData("");
		result.addExtension(ext);
		
		return result;
	}
	


	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
		autocompleteHelper.autocompleteBaseCommand(BASE_COMMAND);
	}

}