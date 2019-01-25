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

import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.terminal.client.terminal.TerminalUIService;
import net.datenwerke.rs.terminal.client.terminal.dto.CommandResultDto;

import com.google.inject.Inject;

public class ScriptingUiServiceImpl implements ScriptingUiService {

	private final ScriptingDao scriptingDao;
	private final TerminalUIService terminalService;
		
	@Inject
	public ScriptingUiServiceImpl(
		ScriptingDao scriptingDao,
		TerminalUIService terminalService	
		){
		
		/* store objects */
		this.terminalService = terminalService;
		this.scriptingDao = scriptingDao;
	}

	@Override
	public void executeScript(String scriptLocation, String args, HashMap<String, String> config) {
		scriptingDao.executeScript(scriptLocation, args, config, new RsAsyncCallback<CommandResultDto>(){
			@Override
			public void onSuccess(CommandResultDto result) {
				terminalService.processExternalResult(result);
			}
		});
	}
}
