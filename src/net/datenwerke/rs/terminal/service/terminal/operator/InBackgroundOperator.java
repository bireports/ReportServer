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
 
 
package net.datenwerke.rs.terminal.service.terminal.operator;

import net.datenwerke.async.DwAsyncService;
import net.datenwerke.rs.terminal.service.terminal.ExecuteCommandConfig;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.exceptions.TerminalException;
import net.datenwerke.rs.terminal.service.terminal.helpers.CommandParser;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;

import com.google.inject.Inject;

public class InBackgroundOperator implements TerminalCommandOperator {

	final private DwAsyncService asyncService;
	
	@Inject
	public InBackgroundOperator(
		DwAsyncService asyncService) {
		this.asyncService = asyncService;
	}

	@Override
	public Integer consumes(String command, CommandParser parser,
			ExecuteCommandConfig config) {
		if(null == command)
			return null;
		
		if(command.trim().endsWith("&"))
			return command.lastIndexOf("&");
		
		return null;
	}

	@Override
	public CommandResult execute(String command, CommandParser parser,
			ExecuteCommandConfig config, final TerminalSession session) {
		final String newCommand = command.substring(0, command.lastIndexOf("&") - 1);
		asyncService.submit(new Runnable() {
			
			@Override
			public void run() {
				try {
					session.execute(newCommand);
				} catch (TerminalException e) {
				}
			}
		});
		return new CommandResult();
	}

}
