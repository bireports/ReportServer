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
 
 
package net.datenwerke.rs.terminal.service.terminal.hooks;

import java.io.Serializable;

import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.obj.AutocompleteResult;
import net.datenwerke.rs.terminal.service.terminal.obj.CommandResult;

public interface TerminalSessionHijackHook extends Hook, Serializable {

	AutocompleteResult autocomplete(TerminalSession terminalSession,
			String command, int cursorPosition, boolean forceResult);

	CommandResult execute(TerminalSession terminalSession, String command);

	boolean consumes(TerminalSession terminalSession, CommandResult result);

	CommandResult adapt(TerminalSession terminalSession, CommandResult result);

	CommandResult ctrlC(TerminalSession terminalSession);

	boolean wantsToContinue(TerminalSession terminalSession, String command);


}
