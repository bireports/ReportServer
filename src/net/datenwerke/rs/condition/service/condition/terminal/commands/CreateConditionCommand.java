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
 
 
package net.datenwerke.rs.condition.service.condition.terminal.commands;

import java.util.List;

import com.google.inject.Inject;

import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.condition.service.condition.ConditionService;
import net.datenwerke.rs.condition.service.condition.entity.Condition;
import net.datenwerke.rs.condition.service.condition.locale.ConditionMessages;
import net.datenwerke.rs.condition.service.condition.terminal.commands.hooks.ConditionSubCommandHook;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.interfaces.ReportVariant;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.NonOptArgument;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.security.service.security.rights.Read;

public class CreateConditionCommand implements ConditionSubCommandHook {
	
	public static final String BASE_COMMAND = "create";
	
	private final ConditionService conditionService;

	@Inject
	public CreateConditionCommand(
		ConditionService conditionService
		){
		
		/* store objects */
		this.conditionService = conditionService;
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
		messageClass = ConditionMessages.class,
		name = BASE_COMMAND,
		description = "commandRcondition_sub_create_desc",
		nonOptArgs={
			@NonOptArgument(name="report", description="commandRcondition_sub_create_par_reportId", mandatory=true),
			@NonOptArgument(name="key", description="commandRcondition_sub_create_par_key", mandatory=true),
			@NonOptArgument(name="name", description="commandRcondition_sub_create_par_name", mandatory=true),
			@NonOptArgument(name="description", description="commandRcondition_sub_create_par_desc", mandatory=true)
		}
	)
	public CommandResult execute(CommandParser parser, TerminalSession session) throws ObjectResolverException {
		List<String> args = parser.getNonOptionArguments();
		if(args.size() < 2)
			throw new IllegalArgumentException("Expect at least two arguments");
		
		String reportRef = args.get(0);
		String name = args.get(1);
		String key = args.size() > 2 ? args.get(2) : "";
		String description = args.size() > 3 ? args.get(3) : "";
		
		
		try{
			Report report = (Report) session.getObjectResolver().getObject(reportRef,Read.class);
			if(null == report || !( report instanceof TableReport) )
				return new CommandResult("Could not find table report with id " + reportRef);
			
			if(! (report instanceof ReportVariant))
				return new CommandResult("Expected a report variant.");
			
			Condition cond = new Condition();
			cond.setName(name);
			cond.setKey(key);
			cond.setDescription(description);
			cond.setReport((TableReport) report);
			
			conditionService.persist(cond);
			
			return new CommandResult("Condition created");
		} catch(ClassCastException e){
			return new CommandResult("Could not find report");
		}
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
	}

}
