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
 
 
package net.datenwerke.rs.core.service.login;

import net.datenwerke.rs.utils.properties.ApplicationPropertiesService;
import net.datenwerke.security.client.login.resultinfos.AccountInhibitionAuthenticateResultInfo;
import net.datenwerke.security.service.authenticator.AuthenticationResult;
import net.datenwerke.security.service.authenticator.hooks.PostAuthenticateHook;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;

public class BlockSuperUserOnLoginHooker implements PostAuthenticateHook {

	public static final String BLOCK_ROOT_ACCOUNTS = "rs.authenticator.blockroot";
	
	private boolean blockRoot;
	
	@Inject
	public BlockSuperUserOnLoginHooker(ApplicationPropertiesService propService) {
		blockRoot = propService.getBoolean("rs.authenticator.blockroot", true);
	}
	
	@Override
	public void authenticated(AuthenticationResult authRes) {
		User user = authRes.getUser();
		if(null != user && user.isSuperUser() && blockRoot){
			authRes.setAllowed(false);
			authRes.addInfo(new AccountInhibitionAuthenticateResultInfo());
		}
	}

}
