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
 
 
package net.datenwerke.security.service.authenticator;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.security.client.login.AuthenticateResultInfo;
import net.datenwerke.security.service.usermanager.entities.User;

public class AuthenticationResult {
	
	private boolean allowed;
	private final User user;
	
	private List<AuthenticateResultInfo> infos = new ArrayList<AuthenticateResultInfo>();
	
	@Deprecated
	public AuthenticationResult(boolean valid, User user, boolean authoritative) {
		this(valid || !authoritative, user);
	}

	public AuthenticationResult(boolean allowed, User user) {
		this.allowed = allowed;
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public boolean isAllowed() {
		return allowed;
	}

	public void setInfos(List<AuthenticateResultInfo> infos) {
		this.infos = infos;
	}

	public List<AuthenticateResultInfo> getInfos() {
		return infos;
	}

	public void addInfo(AuthenticateResultInfo info){
		if(null == infos)
			infos = new ArrayList<AuthenticateResultInfo>();

		infos.add(info);
	}

	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
	}
	
}
