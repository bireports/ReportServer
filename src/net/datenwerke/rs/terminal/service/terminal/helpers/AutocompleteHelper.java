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
 
 
package net.datenwerke.rs.terminal.service.terminal.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser.CurrentArgument;
import net.datenwerke.rs.terminal.service.terminal.obj.AutocompleteResult;

import com.google.inject.Inject;

public class AutocompleteHelper {

	private CommandParser parser;
	private int cursorPosition;
	
	private List<String> baseCommandCompletion = new ArrayList<String>();
	private Map<Integer, List<String>> autocompleteNamesByToken = new HashMap<Integer, List<String>>();

	private final AutocompleteResult result = new AutocompleteResult();
	
	@Inject
	public AutocompleteHelper(){
		
	}

	public void init(CommandParser parser, int cursorPosition) {
		this.parser = parser;
		this.cursorPosition = cursorPosition;
	}
	
	public CommandParser getParser() {
		return parser;
	}
	
	public int getCursorPosition() {
		return cursorPosition;
	}
	
	public void addResultEntry(String entry){
		result.addEntry(entry);
	}
	
	public void addObjectResultEntry(String entry){
		result.addObjectEntry(entry);
	}
	
	public void addCmdResultEntry(String entry){
		result.addCmdEntry(entry);
	}
	
	public AutocompleteResult getResult() {
		if(parser.isBeforeFirstToken(cursorPosition)){
			for(String cmd : baseCommandCompletion)
				result.addCmdEntry(cmd);
		} else if(parser.isInBaseCommand(cursorPosition)){
			String base = parser.getBaseCommand();
			
			for(String cmd : baseCommandCompletion){
				if(cmd.startsWith(base))
					result.addCmdEntry(cmd);
			}
		}
		
		int cmdNr = parser.getTokenNrBy(cursorPosition);
		if(autocompleteNamesByToken.containsKey(cmdNr)){
			CurrentArgument arg = parser.getCurrentArgument(cursorPosition);
			String cmd = arg.getArgument();
			if(null == cmd || "".equals(cmd))
				result.addEntries(autocompleteNamesByToken.get(cmdNr));
			else {
				for(String autoCmd : autocompleteNamesByToken.get(cmdNr))
					if(autoCmd.startsWith(cmd))
						result.addEntry(autoCmd);
			}
		}
		
		String currentArgument = parser.getTokenNr(cmdNr);
		
		/* finalize */
		result.processCompleteStump(currentArgument);
		result.sort();
		
		return result;
	}
	
	public void autocompleteBaseCommand(String baseCommand) {
		baseCommandCompletion.add(baseCommand);
	}

	public void addAutocompleteNamesForToken(int tokendNr, String autocompleteName) {
		if(! autocompleteNamesByToken.containsKey(tokendNr))
			autocompleteNamesByToken.put(tokendNr, new ArrayList<String>());
		
		autocompleteNamesByToken.get(tokendNr).add(autocompleteName);
	}
	
	public void addAutocompleteNamesForToken(int tokendNr, List<String> autocompleteNames) {
		if(! autocompleteNamesByToken.containsKey(tokendNr))
			autocompleteNamesByToken.put(tokendNr, new ArrayList<String>());
		
		autocompleteNamesByToken.get(tokendNr).addAll(autocompleteNames);
	}
}
