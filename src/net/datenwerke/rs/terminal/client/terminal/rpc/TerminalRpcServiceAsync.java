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
 
 
package net.datenwerke.rs.terminal.client.terminal.rpc;

import net.datenwerke.rs.terminal.client.terminal.dto.AutocompleteResultDto;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TerminalRpcServiceAsync {

	void autocomplete(String sessionId, String command, int cursorPosition,
			AsyncCallback<AutocompleteResultDto> callback);

	void autocomplete(String sessionId, String command, int cursorPosition, boolean forceResult,
			AsyncCallback<AutocompleteResultDto> callback);

	void execute(String sessionId, String command, AsyncCallback<CommandResultDto> callback);

	void initSession(AsyncCallback<String> callback);

	void closeSession(String sessionId, AsyncCallback<Void> callback);

	void ctrlCPressed(String sessionId, AsyncCallback<CommandResultDto> callback);

}
