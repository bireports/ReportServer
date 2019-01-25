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
 
 
package net.datenwerke.rs.scripting.client.scripting.rpc;

import java.util.HashMap;
import java.util.List;

import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultDto;

import com.google.gwt.user.client.rpc.AsyncCallback;


public interface ScriptingRpcServiceAsync {

	void executeScript(String scriptLocation, String args, HashMap<String, String> context,
			AsyncCallback<CommandResultDto> callback);

	void executeLoginScript(AsyncCallback<List<CommandResultDto>> callback);

	void getImportPathFor(String line, AsyncCallback<List<String>> callback);


}
