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
 
 
package net.datenwerke.rs.terminal.service.terminal.basecommands;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import net.datenwerke.rs.base.service.reportengines.table.output.object.RSStringTableRow;
import net.datenwerke.rs.base.service.reportengines.table.output.object.RSTableModel;
import net.datenwerke.rs.base.service.reportengines.table.output.object.TableDefinition;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.exceptions.TerminalException;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.helpmessenger.annotations.CliHelpMessage;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalCommandHook;
import net.datenwerke.rs.terminal.service.terminal.locale.TerminalMessages;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;

import com.google.inject.Inject;

public class MeminfoCommand implements TerminalCommandHook {
	
	public static final String BASE_COMMAND = "meminfo";
	
	@Inject
	public MeminfoCommand() {
	}

	@Override
	@CliHelpMessage(
		messageClass = TerminalMessages.class,
		name = BASE_COMMAND,
		description = "commandMeminfo_description"
	)
	public CommandResult execute(CommandParser parser, TerminalSession session) throws TerminalException {
		if(parser.hasOption("g","g?")){
			System.gc();
			System.gc();
		}
		
		RSTableModel table = new RSTableModel();
		TableDefinition td = new TableDefinition(Arrays.asList("Heap utilization statistics", "MB"), (List)Arrays.asList(String.class, String.class));
		table.setTableDefinition(td);

		Runtime runtime = Runtime.getRuntime();
		int mb = 1024*1024;
		
		table.addDataRow(new RSStringTableRow("Used Memory", NumberFormat.getIntegerInstance().format((runtime.totalMemory() - runtime.freeMemory()) / mb)));
		table.addDataRow(new RSStringTableRow("Free Memory", NumberFormat.getIntegerInstance().format(runtime.freeMemory() / mb)));
		table.addDataRow(new RSStringTableRow("Total Memory",NumberFormat.getIntegerInstance().format(runtime.totalMemory() / mb)));
		table.addDataRow(new RSStringTableRow("Max Memory",  NumberFormat.getIntegerInstance().format(runtime.maxMemory() / mb)));

		CommandResult result = new CommandResult();
		result.addResultTable(table);
		return result;
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
		autocompleteHelper.autocompleteBaseCommand(BASE_COMMAND);
	}

	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}
	

}
