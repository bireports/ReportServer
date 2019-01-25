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
 
 
package net.datenwerke.rs.fileserver.service.fileserver.terminal.hookers;

import java.util.Scanner;

import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.terminal.service.terminal.basecommands.hooks.CatCommandHandlerHook;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;

public class CatCommandHandlerHooker implements CatCommandHandlerHook {

	@Override
	public boolean consumes(Object object,CommandParser parser) {
		return object instanceof FileServerFile;
	}

	@Override
	public CommandResult cat(Object object,CommandParser parser) {
		CommandResult result = new CommandResult();
		
		String data = new String(((FileServerFile)object).getData());
		
		try(Scanner scanner = new Scanner(data)){
			while (scanner.hasNextLine()) 
				result.addResultLine(scanner.nextLine());
		}
		 
		return result;
	}

}
