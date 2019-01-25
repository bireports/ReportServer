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
 
 
package net.datenwerke.rs.core.client.contexthelp;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.core.client.contexthelp.dto.ContextHelpInfo;
import net.datenwerke.rs.core.client.contexthelp.rpc.ContextHelpRpcServiceAsync;

import com.google.inject.Inject;

public class ContextHelpDao extends Dao {

	private final ContextHelpRpcServiceAsync asyncService;

	@Inject
	public ContextHelpDao(
		ContextHelpRpcServiceAsync asyncService
		) {
		this.asyncService = asyncService;
	}
	
	public void getContextHelp(
			ContextHelpInfo info, 
			RsAsyncCallback<String> callback){
		asyncService.getContextHelp(info, transformAndKeepCallback(callback));
	}
}
