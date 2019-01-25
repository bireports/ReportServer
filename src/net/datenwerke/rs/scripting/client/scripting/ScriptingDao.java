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
 
 
package net.datenwerke.rs.scripting.client.scripting;

import java.util.HashMap;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.scripting.client.scripting.rpc.ScriptingRpcServiceAsync;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class ScriptingDao extends Dao {

	private final ScriptingRpcServiceAsync rpcService;

	@Inject
	public ScriptingDao(ScriptingRpcServiceAsync rpcService) {
		super();
		this.rpcService = rpcService;
	}
	
	public void executeScript(String scriptLocation, String args, HashMap<String, String> context,
			AsyncCallback<CommandResultDto> callback){
		rpcService.executeScript(scriptLocation, args, context, transformAndKeepCallback(callback));
	}
	
	public void executeLoginScript(AsyncCallback<List<CommandResultDto>> callback){
		rpcService.executeLoginScript(transformAndKeepCallback(callback));
	}
	
	public void getImportPathFor(String token, AsyncCallback<List<String>> callback){
		rpcService.getImportPathFor(token, transformAndKeepCallback(callback));
	}
}
