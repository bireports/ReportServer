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
 
 
package net.datenwerke.rs.terminal.service.terminal.hijacker.data;

import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalSessionHijackHook;
import net.datenwerke.rs.terminal.service.terminal.obj.AutocompleteResult;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;

public class SplitTableResultHijacker implements TerminalSessionHijackHook {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7084191861602287621L;
	private SplitTableResult result;

	@Override
	public AutocompleteResult autocomplete(TerminalSession terminalSession,
			String command, int cursorPosition, boolean forceResult) {
		return new AutocompleteResult();
	}

	@Override
	public CommandResult execute(TerminalSession terminalSession, String command) {
		return getNext(terminalSession);
	}

	private CommandResult getNext(TerminalSession terminalSession) {
		CommandResult newResult = this.result.getNext();
		if(! result.hasNext())
			terminalSession.stopHijacking();
		newResult.setModifiers(result.getModifiers());
		return newResult;
	}

	@Override
	public boolean consumes(TerminalSession terminalSession,
			CommandResult result) {
		return result instanceof SplitTableResult;
	}

	@Override
	public CommandResult adapt(TerminalSession terminalSession,
			CommandResult result) {
		this.result = (SplitTableResult) result;
		return getNext(terminalSession);
	}

	@Override
	public CommandResult ctrlC(TerminalSession terminalSession) {
		terminalSession.stopHijacking();
		
		/* create new result */
		CommandResult result = new CommandResult();
		result.setModifiers(this.result.getModifiers());
		
		this.result = null;
		return result;
	}

	@Override
	public boolean wantsToContinue(TerminalSession terminalSession,
			String command) {
		boolean cont = null == command || "".equals(command.trim());
		if(! cont)
			this.result = null;
		
		return cont;
	}


}
