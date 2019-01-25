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
 
 
package net.datenwerke.rs.birt.service.reportengine.terminal;

import net.datenwerke.rs.birt.service.reportengine.BirtReportService;
import net.datenwerke.rs.birt.service.reportengine.locale.BirtEngineMessages;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;

import com.google.inject.Inject;

public class BirtShutdownCommand implements BirtSubCommandHook {

	public static final String BASE_COMMAND = "shutdown";
	
	private final BirtReportService birtService;
	
	@Inject
	public BirtShutdownCommand(BirtReportService birtService){
		this.birtService = birtService;
		
	}
	
	@Override
	public String getBaseCommand() {
		return BASE_COMMAND;
	}
	
	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}

	@Override
	@CliHelpMessage(
		messageClass = BirtEngineMessages.class,
		name = BASE_COMMAND,
		description = "commandBirt_sub_shutdown_description"
	)
	public CommandResult execute(CommandParser parser, TerminalSession session) throws ObjectResolverException {
		birtService.shutdown();
		
		return new CommandResult(BirtEngineMessages.INSTANCE.shutdownComplete());
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
	}
}
