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
 
 
package net.datenwerke.gf.client.juel;

import net.datenwerke.gf.client.juel.dto.JuelResultDto;
import net.datenwerke.gf.client.juel.rpc.JuelRpcServiceAsync;
import net.datenwerke.gxtdto.client.dtomanager.Dao;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class JuelDao extends Dao {


	private final JuelRpcServiceAsync rpcService;

	@Inject
	public JuelDao(JuelRpcServiceAsync rpcService) {
		this.rpcService = rpcService;
	}
	
	
	public void evaluateExpression(String expression, AsyncCallback<JuelResultDto> callback){
		rpcService.evaluateExpression(expression, callback);
	}

}
