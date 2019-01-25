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
 
 
package net.datenwerke.rs.core.server.contexthelp;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.core.client.contexthelp.dto.ContextHelpInfo;
import net.datenwerke.rs.core.client.contexthelp.rpc.ContextHelpRpcService;
import net.datenwerke.rs.core.service.contexthelp.ContextHelpService;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ContextHelpRpcServiceImpl extends SecuredRemoteServiceServlet implements ContextHelpRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8970533441561658684L;

	private final ContextHelpService contextHelpService;
	
	@Inject
	public ContextHelpRpcServiceImpl(ContextHelpService contextHelpService) {
		super();
		this.contextHelpService = contextHelpService;
	}



	@Override
	public String getContextHelp(ContextHelpInfo info) throws ServerCallFailedException {
		return contextHelpService.getContextHelp(info);
	}

}
