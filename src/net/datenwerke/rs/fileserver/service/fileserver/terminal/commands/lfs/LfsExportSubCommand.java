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
 
 
package net.datenwerke.rs.fileserver.service.fileserver.terminal.commands.lfs;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFolder;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.exceptions.TerminalException;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser.CurrentArgument;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;

public class LfsExportSubCommand implements LfsSubCommandHook {

	public static final String BASE_COMMAND = "export";

	@Override
	public String getBaseCommand() {
		return BASE_COMMAND;
	}

	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}

	@Override
	public CommandResult execute(CommandParser parser, TerminalSession session) throws TerminalException {
		List<String> noa = parser.getNonOptionArguments();
		if(noa.size() == 2){
			String path = noa.get(1);
			File baseDir = new File(path);
			if(!baseDir.exists() || ! baseDir.isDirectory())
				return new CommandResult(baseDir.getAbsolutePath() + " is not a directory");
			
			Object object = session.getObjectResolver().getObject(noa.get(0));
			try {
				copy((AbstractFileServerNode) object, baseDir);
			} catch (IOException e) {
				e.printStackTrace();
				return new CommandResult(e);
			}
			
			return new CommandResult("ok");
		}
		return new CommandResult("Missing argument");
	}
	
	private void copy(AbstractFileServerNode node, File folder) throws IOException {
		if(node instanceof FileServerFile){
			FileUtils.writeByteArrayToFile(new File(folder, node.getName()), ((FileServerFile) node).getData()); 
		}else if(node instanceof FileServerFolder){
			File nextDir = new File(folder, node.getName());
			nextDir.mkdir();
			
			for(AbstractFileServerNode child : node.getChildren()){
				copy(child, nextDir);
			}
		}
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
		LfsAutoCompleteHelper.configureAutoComplete(4, autocompleteHelper);
	}
}
