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
import java.util.List;

import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser.CurrentArgument;

public class LfsAutoCompleteHelper {
	
	public static void configureAutoComplete(int tokenNum, AutocompleteHelper autocompleteHelper){
		try{
			CommandParser scp = autocompleteHelper.getParser().getSubCommandParser();
			List<String> noa = scp.getNonOptionArguments();

			CurrentArgument arg = autocompleteHelper.getParser().getCurrentArgument(autocompleteHelper.getCursorPosition());
			String fullcmd = arg.getArgument();
			String cmd = fullcmd;
			if(cmd.contains("/")){
				cmd = cmd.substring(cmd.lastIndexOf("/") + 1);
			}

			String basePath = "";
			if(noa.size() > 0){
				basePath = scp.getNonOptionArguments().get(noa.size() - 1);
			}
			File baseDir = new File(basePath).getCanonicalFile();
			if(!baseDir.exists() || !baseDir.isDirectory()){
				baseDir = baseDir.getParentFile();
			}

			for(File f : baseDir.listFiles()){
				if(f.isDirectory()){
					String name = f.getName();
					if(name.startsWith(cmd)){
						if(name.length() > cmd.length()){
							name = name.substring(cmd.length()	);
						}

						name = fullcmd + name;
						autocompleteHelper.addAutocompleteNamesForToken(tokenNum, name +"/");
					}
				}
			}
		}catch(Exception e){
		}
	}

}
