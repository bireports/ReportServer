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
 
 
package net.datenwerke.security.ext.server.crypto;

import net.datenwerke.rs.utils.crypto.PasswordHasher;
import net.datenwerke.security.ext.client.crypto.rpc.CryptoRpcService;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.crypto.pbe.PbeService;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

/**
 * 
 *
 */
@Singleton
public class CryptoRpcServiceImpl extends SecuredRemoteServiceServlet implements CryptoRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 636443582869558906L;

	private final PasswordHasher passwordHasher;
	private final UserManagerService userManagerService;
	private final PbeService pbeService;
	private final Provider<AuthenticatorService> authenticatorService;

	@Inject
	public CryptoRpcServiceImpl(
			PasswordHasher passwordHasher,
			UserManagerService userManagerService,
			PbeService pbeService, 
			Provider<AuthenticatorService> authenticatorService
			) {

		this.userManagerService = userManagerService;
		this.passwordHasher = passwordHasher;
		this.pbeService = pbeService;
		this.authenticatorService = authenticatorService;
	}





	@SecurityChecked(bypass=true)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public String getHmacPassphrase() {
		return passwordHasher.getHmacPassphrase();
	}

	@SecurityChecked(bypass=true)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public String getSalt() {
		return pbeService.getSalt();
	}

	@SecurityChecked(bypass=true)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public String getUserSalt(String username){
		User user = userManagerService.getUserByName(username);
		return getUserSalt(user);
	}

	@SecurityChecked(bypass=true)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public String getUserSalt() {
		User user = authenticatorService.get().getCurrentUser();
		return getUserSalt(user);
	}

	private String getUserSalt(User user){
		String hashedPassword = user.getPassword();

		if(hashedPassword.length() == 40){
			return hashedPassword.substring(0, 12);
		}

		return "";
	}

	@SecurityChecked(bypass=true)
	@Override
	@Transactional(rollbackOn={Exception.class})
	public int getKeyLength() {
		return pbeService.getKeyLength();
	}

}
