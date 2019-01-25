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
 
 
package net.datenwerke.rs.adminutils.server.suuser;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.adminutils.client.suuser.rpc.SuUserRpcService;
import net.datenwerke.rs.adminutils.service.su.genrights.SuSecurityTarget;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.annotation.GenericTargetVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Execute;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;
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
public class SuUserRpcServiceImpl extends SecuredRemoteServiceServlet implements
		SuUserRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7294232233898833127L;

	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final UserManagerService userManagerService;
	private final SecurityService securityService;
	
	
	@Inject
	public SuUserRpcServiceImpl(
			Provider<AuthenticatorService> authenticatorServiceProvider,
			UserManagerService userManagerService, 
			SecurityService securityService) {

		/* store objects */
		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.userManagerService = userManagerService;
		this.securityService = securityService;
	}


	@Override
	@SecurityChecked(
		genericTargetVerification = @GenericTargetVerification(
			target=SuSecurityTarget.class,
			verify=@RightsVerification(rights=Execute.class)
		)
	)
	@Transactional(rollbackOn={Exception.class})
	public void su(String username) throws ServerCallFailedException {
		User user = userManagerService.getUserByName(username);
		if(null == user)
			throw new ExpectedException("Could not find user: " + username);

		authenticatorServiceProvider.get().su(user);
	}


	@Override
	@SecurityChecked(
		genericTargetVerification = @GenericTargetVerification(
			target=SuSecurityTarget.class,
			verify=@RightsVerification(rights=Execute.class)
		)
	)
	@Transactional(rollbackOn={Exception.class})
	public void su(Long id) throws ExpectedException {
		AbstractUserManagerNode node = (AbstractUserManagerNode) userManagerService.getNodeById(id);
		if(null == node || ! (node instanceof AbstractUserManagerNode))
			throw new ExpectedException("Could not find user: " + id);

		if(!securityService.checkRights(node, Execute.class)){
			throw new ViolatedSecurityException("Insufficient priviledges to log in as " + node.getName());
		}
		
		authenticatorServiceProvider.get().su((User) node);
	}

}
