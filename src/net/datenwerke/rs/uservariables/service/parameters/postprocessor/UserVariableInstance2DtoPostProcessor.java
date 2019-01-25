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
 
 
package net.datenwerke.rs.uservariables.service.parameters.postprocessor;

import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoPostProcessor;
import net.datenwerke.rs.uservariables.client.parameters.dto.UserVariableParameterInstanceDto;
import net.datenwerke.rs.uservariables.client.parameters.dto.decorator.UserVariableParameterInstanceDtoDec;
import net.datenwerke.rs.uservariables.service.parameters.UserVariableParameterInstance;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserVariableInstance2DtoPostProcessor implements
		Poso2DtoPostProcessor<UserVariableParameterInstance, UserVariableParameterInstanceDto> {

	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	
	@Inject
	public UserVariableInstance2DtoPostProcessor(
		Provider<AuthenticatorService> authenticatorServiceProvider	
		){
		
		this.authenticatorServiceProvider = authenticatorServiceProvider;
	}
	
	@Override
	public void dtoCreated(UserVariableParameterInstance poso,
			UserVariableParameterInstanceDto dto) {
		try{
			User currentUser = authenticatorServiceProvider.get().getCurrentUser();
			Object value = poso.getSelectedValue(currentUser);
			if(null != value)
				((UserVariableParameterInstanceDtoDec)dto).setValue(value.toString());
		} catch(Exception e){}
	}

	@Override
	public void dtoInstantiated(UserVariableParameterInstance poso,
			UserVariableParameterInstanceDto dto) {
		
	}

}
