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
 
 
package net.datenwerke.rs.terminal.client.terminal.rpc;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.terminal.client.terminal.dto.AutocompleteResultDto;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("terminal")
public interface TerminalRpcService extends RemoteService {
	
	public void closeSession(String sessionId) throws ServerCallFailedException;
	
	public String initSession() throws ServerCallFailedException;
	
	public AutocompleteResultDto autocomplete(String sessionId, String command, int cursorPosition) throws ServerCallFailedException, ExpectedException;
	
	public AutocompleteResultDto autocomplete(String sessionId, String command, int cursorPosition, boolean forceResult) throws ServerCallFailedException, ExpectedException;
	
	public CommandResultDto execute(String sessionId, String command) throws ServerCallFailedException, ExpectedException;
	
	public CommandResultDto ctrlCPressed(String sessionId) throws ServerCallFailedException, ExpectedException;
	
	
}
