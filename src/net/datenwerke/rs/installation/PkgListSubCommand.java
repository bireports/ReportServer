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
 
 
package net.datenwerke.rs.installation;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Provider;

import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.exceptions.TerminalException;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.hooks.SubCommand;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;

public class PkgListSubCommand implements SubCommand {

	private static final String BASE_COMMAND = "list";
	
	private Provider<PackagedScriptHelper> packageScriptHelper;
	
	@Inject
	public PkgListSubCommand(Provider<PackagedScriptHelper> packageScriptHelper) {
		this.packageScriptHelper = packageScriptHelper;
	}

	@Override
	public boolean consumes(CommandParser parser, TerminalSession session) {
		return BASE_COMMAND.equals(parser.getBaseCommand());
	}

	@Override
	public CommandResult execute(CommandParser parser, TerminalSession session)	throws TerminalException {
		CommandResult cr = new CommandResult();
		for(File f : packageScriptHelper.get().listPackages()){
			cr.addResultLine(f.getName());
		}
		return cr;
	}

	@Override
	public void addAutoCompletEntries(AutocompleteHelper autocompleteHelper, TerminalSession session) {
		
	}

	@Override
	public String getBaseCommand() {
		return BASE_COMMAND;
	}

}
