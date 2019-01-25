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
 
 
package net.datenwerke.rs.base.client.datasources.statementmanager;

import javax.inject.Inject;

import net.datenwerke.gxtdto.client.servercommunication.callback.NotamCallback;
import net.datenwerke.rs.base.client.datasources.statementmanager.rpc.StatementManagerRpcServiceAsync;

public class StatementManagerDao {

	private final StatementManagerRpcServiceAsync rpcService;

	@Inject
	public StatementManagerDao(StatementManagerRpcServiceAsync rpcService) {
		this.rpcService = rpcService;
	}
	
	
	public void cancelStatement(String statementIdentifier){
		rpcService.cancelStatement(statementIdentifier, new NotamCallback<Void>(null));
	}
	
	
}
