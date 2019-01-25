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
 
 
package net.datenwerke.security.ext.client.security;

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.security.client.security.GenericTargetIdentifier;
import net.datenwerke.security.client.security.SecurityUIService;
import net.datenwerke.security.client.security.dto.GenericSecurityTargetContainer;
import net.datenwerke.security.client.security.dto.RightDto;

import com.google.inject.Inject;

public class SecurityUIServiceImpl implements SecurityUIService {

	private final LoginService loginService;
	
	private GenericSecurityTargetContainer genericSecurityContainer;
	
	@Inject
	public SecurityUIServiceImpl(
		LoginService loginService	
		){
		
		/* store objects */
		this.loginService = loginService;
	}
	
	@Override
	public void setGenericSecurityContainer(GenericSecurityTargetContainer genericSecurityContainer){
		this.genericSecurityContainer = genericSecurityContainer;
	}

	@Override
	public boolean hasRight(Class<? extends GenericTargetIdentifier> identifier, Class<? extends RightDto> toCheck) {
		if(null == toCheck)
			return false;
		for(RightDto right : genericSecurityContainer.getRights(identifier))
			if(right.getClass().equals(toCheck))
				return true;
		return false;
	}

	@Override
	public boolean isSuperUser() {
		return loginService.getCurrentUser().isSuperUser();
	}


}
