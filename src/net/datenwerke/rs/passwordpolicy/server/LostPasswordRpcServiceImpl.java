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
 
 
package net.datenwerke.rs.passwordpolicy.server;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.passwordpolicy.service.lostpassword.LostPasswordService;
import net.datenwerke.security.client.security.lostpassword.rpc.LostPasswordRpcService;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.annotation.SecurityChecked;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

/**
 * 
 *
 */
@Singleton
public class LostPasswordRpcServiceImpl extends SecuredRemoteServiceServlet implements LostPasswordRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5238457329967237907L;
	
	private final LostPasswordService lostPasswordService;

	@Inject
	public LostPasswordRpcServiceImpl(
		LostPasswordService lostPasswordService
		) {
		
		/* storeo objects */
		this.lostPasswordService = lostPasswordService;
	}
	
	@Override
	@SecurityChecked(bypass=true)
	@Transactional(rollbackOn={Exception.class})
	public void requestNewPassword(String username) throws ServerCallFailedException {
		lostPasswordService.requestNewPassword(username);
	}
}
