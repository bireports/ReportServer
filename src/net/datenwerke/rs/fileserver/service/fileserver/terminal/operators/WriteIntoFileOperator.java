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
 
 
package net.datenwerke.rs.fileserver.service.fileserver.terminal.operators;

import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFolder;
import net.datenwerke.rs.terminal.service.terminal.ExecuteCommandConfig;
import net.datenwerke.rs.terminal.service.terminal.ExecuteCommandConfigImpl;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.exceptions.TerminalException;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.operator.TerminalCommandOperator;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.exceptions.VFSException;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Write;

import com.google.inject.Inject;

public class WriteIntoFileOperator implements TerminalCommandOperator {

	private enum Mode{
		CREATE, APPEND
	}
	
	private final FileServerService fileService;
	private final SecurityService securityService;
	
	private Mode mode;
	
	@Inject
	public WriteIntoFileOperator(FileServerService fileService,
			SecurityService securityService) {
		this.fileService = fileService;
		this.securityService = securityService;
	}

	@Override
	public Integer consumes(String command, CommandParser parser, ExecuteCommandConfig config) {
		int maxPos = getMaxOpPos(command, parser);
		if(maxPos > 0)
			return maxPos;
		return null;
	}

	private int getMaxOpPos(String command, CommandParser parser) {
		int pos = 0;
		int maxPos = 0;
		char lastChar = '-';
		int seenQuotes = 0;
		for(char c : parser.getRawCommand().toCharArray()){
			pos++;
			if('\"' == c && lastChar != '\\')
				seenQuotes++;
			else if('>' == c && lastChar == '>' && seenQuotes % 2 == 0){
				mode = Mode.APPEND;
				maxPos = pos - 1;
			}
			else if('>' == c && seenQuotes % 2 == 0){
				mode = Mode.CREATE;
				maxPos = pos;
			}
			
			lastChar = c;
		}
		return maxPos;
	}

	@Override
	public CommandResult execute(String command, CommandParser parser,
			ExecuteCommandConfig config, TerminalSession session) throws TerminalException {
		int maxPos = getMaxOpPos(command, parser);
		
		String firstCommand = command.substring(0, maxPos - 1);
		try{
			String fileLocation = command.substring(mode == Mode.APPEND ? maxPos+1 : maxPos).trim();
			if(fileLocation.trim().equals(""))
				throw new TerminalException("Invalid file operator position");
			
			VFSLocation location = session.getFileSystem().getLocation(fileLocation);
			
			FileServerFile file = (FileServerFile) location.getObject();
			
			if(null == file){
				FileServerFolder folder = (FileServerFolder) location.getParentLocation().getObject();
				if(null == folder)
					throw new TerminalException("Could not open location");
				
				file = fileService.createFileAtLocation(location);
				fileService.persist(file);
			}

			/* check write rights */
			if(! securityService.checkRights(file, Write.class))
				throw new ViolatedSecurityException();
			
			CommandResult result = session.execute(firstCommand, new ExecuteCommandConfigImpl() {
				@Override
				public CommandResult execute(TerminalCommandHook commandHook,
						CommandParser parser, TerminalSession session)
						throws TerminalException {
					return commandHook.execute(parser, session);
				}
				
				@Override
				public boolean allowOperators() {
					return true;
				}
				
				@Override
				public boolean allowInteractive() {
					return false;
				}
				
				@Override
				public boolean allowHijackers() {
					return false;
				}
			});
			
			if(result.containsByteData() && mode != Mode.APPEND){
				file.setData(result.getByteData());
			} else {
				String data = null != file.getData() ? new String(file.getData()) : "";
	
				String resultString = null == result ? "" : result.toString();
				if(mode == Mode.APPEND)
					data += resultString;
				else
					data = resultString;
				
				file.setData(data.getBytes());
			}
			
			fileService.merge(file);
			
			CommandResult newResult = new CommandResult();
			newResult.setCommitTransaction(true);
			return newResult;
		} catch(IndexOutOfBoundsException e){
			throw new TerminalException("Invalid file operator position");
		} catch (VFSException e) {
			throw new TerminalException("Could not open location: ", e);
		}
	}


}
