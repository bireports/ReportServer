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
 
 
package net.datenwerke.rs.base.ext.service.reportmanager.terminal.commands;

import java.util.List;

import net.datenwerke.rs.base.ext.service.reportmanager.hooks.ReportModSubCommandHook;
import net.datenwerke.rs.base.ext.service.reportmanager.locale.ReportManagerExtMessages;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.NonOptArgument;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.security.service.security.rights.Read;

import com.google.inject.Inject;

public class SetUuidCommand implements ReportModSubCommandHook {

	public static final String BASE_COMMAND = "setUUID";
	
	private final ReportService reportService;

	@Inject
	public SetUuidCommand(
			ReportService reportService
		){
		
		/* store objects */
		this.reportService = reportService;
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
		messageClass = ReportManagerExtMessages.class,
		name = BASE_COMMAND,
		description = "commandReportmod_sub_setUUID_description",
		nonOptArgs = {
			@NonOptArgument(name="UUID", description="commandReportmod_sub_setUUID_arg1", mandatory=true)
		}
	)
	public CommandResult execute(CommandParser parser, TerminalSession session) throws ObjectResolverException {
		List<String> arguments = parser.getNonOptionArguments();
		if(2 > arguments.size())
			throw new IllegalArgumentException();
		
		String reportRef = arguments.get(0);
		String uuid = arguments.get(1);
		
		try{
			Report report = (Report) session.getObjectResolver().getObject(reportRef,Read.class);
			if(null == report)
				return new CommandResult("Coud not find report for " + reportRef);
			
			report.setUuid(uuid);
			reportService.merge(report);
			
			return new CommandResult("Set uuid to " + uuid);
		} catch(Exception e){
			return new CommandResult("Coud not find report for " + reportRef);
		}
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
	}
}
