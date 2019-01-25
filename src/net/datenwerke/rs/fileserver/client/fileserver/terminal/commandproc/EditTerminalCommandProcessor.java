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
 
 
package net.datenwerke.rs.fileserver.client.fileserver.terminal.commandproc;

import com.google.inject.Inject;

import net.datenwerke.rs.fileserver.client.fileserver.FileSeverUiService;
import net.datenwerke.rs.fileserver.client.fileserver.dto.EditCommandResultExtensionDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFileDto;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultDto;
import net.datenwerke.rs.terminal.client.terminal.hooks.CommandResultProcessorHook;

public class EditTerminalCommandProcessor implements CommandResultProcessorHook {
	
	private final FileSeverUiService fileService;
	
	@Inject
	public EditTerminalCommandProcessor(
		FileSeverUiService fileService
			) {
		this.fileService = fileService;
	}

	@Override
	public void process(CommandResultDto result) {
		if(result.getExtensions().size() == 1 && result.getExtensions().get(0) instanceof EditCommandResultExtensionDto){
			final EditCommandResultExtensionDto editCmd = (EditCommandResultExtensionDto) result.getExtensions().get(0);
			final FileServerFileDto file = editCmd.getFile(); 
			
			fileService.editFileDirectly(file,editCmd.getData());
		}
	}

}
