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
 
 
package net.datenwerke.rs.base.ext.service.reportmanager.terminal.commands;

import java.util.List;

import net.datenwerke.rs.base.ext.service.reportmanager.hooks.ReportModSubCommandHook;
import net.datenwerke.rs.base.ext.service.reportmanager.locale.ReportManagerExtMessages;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportProperty;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportStringProperty;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.NonOptArgument;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.security.service.security.rights.Read;

import com.google.inject.Inject;

public class SetPropertyCommand implements ReportModSubCommandHook {

	public static final String BASE_COMMAND = "setProperty";
	
	private final ReportService reportService;

	@Inject
	public SetPropertyCommand(
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
		description = "commandReportmod_sub_setProperty_description",
		nonOptArgs = {
			@NonOptArgument(name="identifier", description="commandReportmod_sub_setProperty_arg1", mandatory=true),
			@NonOptArgument(name="property", description="commandReportmod_sub_setProperty_arg2", mandatory=true),
			@NonOptArgument(name="value", description="commandReportmod_sub_setProperty_arg3", mandatory=true, varArgs=true)
		}
	)
	public CommandResult execute(CommandParser parser, TerminalSession session) throws ObjectResolverException {
		List<String> arguments = parser.getNonOptionArguments();
		if(3 > arguments.size())
			throw new IllegalArgumentException();
		
		String reportRef = arguments.get(0);
		String propertyName = arguments.get(1);
		String value = arguments.get(2);
		
		try{
			Report report = (Report) session.getObjectResolver().getObject(reportRef,Read.class);
			if(null == report)
				return new CommandResult("Coud not find report for " + reportRef);
			
			ReportProperty property = report.getReportProperty(propertyName);
			if(property instanceof ReportStringProperty)
				((ReportStringProperty)property).setStrValue(value);
			else if(null == property){
				property = new ReportStringProperty();
				property.setName(propertyName);
				((ReportStringProperty)property).setStrValue(value);
				
				report.addReportProperty(property);
			} else {
				return new CommandResult("Specified property is not a simple property");
			}
			
			reportService.merge(report);
			
			return new CommandResult("Property changed");
		} catch(Exception e){
			return new CommandResult("Coud not find report for " + reportRef);
		}
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
	}
}
