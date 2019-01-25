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
 
 
package net.datenwerke.rs.enterprise.server;

import com.google.inject.Singleton;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.enterprise.client.EnterpriseInformationDto;
import net.datenwerke.rs.enterprise.client.rpc.EnterpriseCheckRpcService;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.annotation.SecurityChecked;

@Singleton
public class CommunityEnterpriseCheckRpcServiceImpl extends SecuredRemoteServiceServlet implements EnterpriseCheckRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	@SecurityChecked(bypass=true)
	public EnterpriseInformationDto getEnterpriseInfos() throws ServerCallFailedException {
		EnterpriseInformationDto info = new EnterpriseInformationDto();
		return info;
	}

}
