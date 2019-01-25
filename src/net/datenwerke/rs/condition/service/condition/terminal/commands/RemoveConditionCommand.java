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
 
 
package net.datenwerke.rs.condition.service.condition.terminal.commands;

import java.util.List;

import javax.persistence.NoResultException;

import net.datenwerke.rs.condition.service.condition.ConditionService;
import net.datenwerke.rs.condition.service.condition.entity.Condition;
import net.datenwerke.rs.condition.service.condition.locale.ConditionMessages;
import net.datenwerke.rs.condition.service.condition.terminal.commands.hooks.ConditionSubCommandHook;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.NonOptArgument;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;

import com.google.inject.Inject;

public class RemoveConditionCommand implements ConditionSubCommandHook {
	
	public static final String BASE_COMMAND = "remove";
	
	private final ConditionService conditionService;

	@Inject
	public RemoveConditionCommand(
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
		description = "commandRcondition_sub_remove_desc",
		nonOptArgs={
			@NonOptArgument(name="reportId", description="commandRcondition_sub_remove_par_reportId", mandatory=true)
		}
	)
	public CommandResult execute(CommandParser parser, TerminalSession session) throws ObjectResolverException {
		List<String> args = parser.getNonOptionArguments();
		if(args.size() != 1)
			throw new IllegalArgumentException("Expect one argument");
		
		Long id = Long.valueOf(args.get(0));
		
		try{
			Condition cond = conditionService.getConditionById(id);
			conditionService.remove(cond);
		} catch(NoResultException e){
			return new CommandResult("No condition with id " + id + " found.");
		}
		
		return new CommandResult("Condition removed.");
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
	}

}
