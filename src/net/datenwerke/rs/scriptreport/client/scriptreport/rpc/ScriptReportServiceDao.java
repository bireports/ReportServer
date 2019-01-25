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
 
 
package net.datenwerke.rs.scriptreport.client.scriptreport.rpc;

import java.util.Collection;

import javax.inject.Inject;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;
import net.datenwerke.rs.scriptreport.client.scriptreport.parameters.dto.ScriptParameterDefinitionDto;

public class ScriptReportServiceDao extends Dao {

	private final ScriptReportRpcServiceAsync rpcService;
	
	@Inject
	public ScriptReportServiceDao(ScriptReportRpcServiceAsync scriptReportRpcServiceAsync) {
		this.rpcService =  scriptReportRpcServiceAsync;
	}
	
	
	public void getScriptParameterContents(ScriptParameterDefinitionDto definition, Collection<ParameterInstanceDto> relevantInstances, RsAsyncCallback<String> callback) {
		rpcService.getScriptParameterContents(definition, unproxy(relevantInstances), callback);
	}
}
