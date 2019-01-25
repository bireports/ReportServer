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
 
 
package net.datenwerke.rs.incubator.service.aliascmd.terminal.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.datenwerke.rs.configservice.service.configservice.hooks.ReloadConfigNotificationHook;
import net.datenwerke.rs.incubator.service.aliascmd.AliasCmdModule;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.exceptions.TerminalException;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.utils.config.ConfigService;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class AliasCommand implements TerminalCommandHook, ReloadConfigNotificationHook {

	public static final String ENTRY_PROPERTY_COMMAND = "command";
	public static final String ENTRY_PROPERTY_ALIAS = "alias";
	public static final String ENTRY_PROPERTY = "cmdaliases.entry";
	
	private final ConfigService configService;
	private final Map<String, String> aliasTable = new HashMap<String, String>();

	@Inject
	public AliasCommand(
		ConfigService configService	
		){
		
		this.configService = configService;
		
		updateConfiguration();
	}
	
	private String updateConfiguration() {
		try{
			XMLConfiguration config = (XMLConfiguration) configService.getConfig(AliasCmdModule.CONFIG_FILE);
			aliasTable.clear();
			
			List<HierarchicalConfiguration> fields = config.configurationsAt(ENTRY_PROPERTY);
			for(HierarchicalConfiguration sub : fields){
				String alias = sub.getString(ENTRY_PROPERTY_ALIAS);
				String cmd = sub.getString(ENTRY_PROPERTY_COMMAND);
				aliasTable.put(alias, cmd);
			}
			return null;
		} catch(Exception e){
			return e.getMessage();
		}
	}

	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		String cmd = parser.getBaseCommand();
		return aliasTable.containsKey(cmd);
	}

	@Override
	public CommandResult execute(CommandParser parser, TerminalSession session) throws TerminalException {
		String alias = aliasTable.get(parser.getBaseCommand());
		String newCommand = alias + " " + parser.getArgumentString();
		
		return session.execute(newCommand);
	}
	


	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
		for(String alias : aliasTable.keySet())
			autocompleteHelper.autocompleteBaseCommand(alias);
	}

	@Override
	public void reloadConfig() {
		updateConfiguration();
	}

	@Override
	public void reloadConfig(String identifier) {
		if(AliasCmdModule.CONFIG_FILE.equals(identifier))
			updateConfiguration();
	}

}
