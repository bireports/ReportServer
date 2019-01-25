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
 
 
package net.datenwerke.rs.authenticator.cr.server;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.authenticator.client.login.dto.ChallengeResponseContainer;
import net.datenwerke.rs.authenticator.cr.client.ChallengeResponseRpcService;
import net.datenwerke.rs.authenticator.cr.service.ChallengeResponseService;
import net.datenwerke.rs.utils.crypto.PasswordHasher;
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
public class ChallengeResponseRpcServiceImpl extends SecuredRemoteServiceServlet implements ChallengeResponseRpcService {

	private static final long serialVersionUID = -5730511102140345917L;
	private final ChallengeResponseService challengeResponseService;
	private final PasswordHasher passwordHasher;
	
	@Inject
	public ChallengeResponseRpcServiceImpl(
			ChallengeResponseService challengeResponseService,
			PasswordHasher passwordHasher
	) {
		this.challengeResponseService = challengeResponseService;
		this.passwordHasher = passwordHasher;
	}
	
	@Override
	@SecurityChecked(bypass=true)
	@Transactional(rollbackOn={Exception.class})
	public ChallengeResponseContainer requestChallenge() throws ServerCallFailedException {
		return challengeResponseService.requestChallenge();
	}
	
	
	@Override
	@SecurityChecked(bypass=true)
	@Transactional(rollbackOn={Exception.class})
	public String getHmacPassphrase() {
		return passwordHasher.getHmacPassphrase();
	}
	
}
