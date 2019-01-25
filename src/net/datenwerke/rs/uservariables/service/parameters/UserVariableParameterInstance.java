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
 
 
package net.datenwerke.rs.uservariables.service.parameters;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.AdditionalField;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.rs.uservariables.service.parameters.postprocessor.UserVariableInstance2DtoPostProcessor;
import net.datenwerke.rs.uservariables.service.uservariables.UserVariableService;
import net.datenwerke.rs.uservariables.service.uservariables.entities.UserVariableDefinition;
import net.datenwerke.rs.uservariables.service.uservariables.entities.UserVariableInstance;
import net.datenwerke.security.service.usermanager.entities.User;

import org.hibernate.envers.Audited;

import com.google.inject.Inject;

/**
 * 
 *
 */
@Entity
@Table(name="USERVAR_PARAM_INST")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.uservariables.client.parameters.dto",
	createDecorator=true,
	poso2DtoPostProcessors=UserVariableInstance2DtoPostProcessor.class,
	additionalFields={
		@AdditionalField(name="value", type=String.class)
	}
)
public class UserVariableParameterInstance extends ParameterInstance<UserVariableParameterDefinition> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4562050325298290322L;
	
	@Inject
	private static UserVariableService userVariableService;

	@Override
	public Object getSelectedValue(User user) {
		/* get user variable */
		UserVariableParameterDefinition parameterDefinition = (UserVariableParameterDefinition) getDefinition();
		UserVariableDefinition variableDefinition = parameterDefinition.getUserVariableDefinition();
		
		/* find variable instance for current user */
		UserVariableInstance instance = userVariableService.getVariableInstanceForUser(user, variableDefinition);
		
		if(null == instance){
			throw new RuntimeException("Failed retrieving value for UserVariable \"" + variableDefinition.getName() + "\" and user \""+user.getUsername()+"\". Usually that means, the variable was not bound anywhere in the users subtree.");
		}
		
		return instance.getVariableValue();
	}
	
	@Override
	public Object getDefaultValue(User user, ParameterSet parameterSet) {
		return getSelectedValue(user);
	}

	@Override
	protected Class<?> getType() {
		UserVariableParameterDefinition parameterDefinition = (UserVariableParameterDefinition) getDefinition();
		UserVariableDefinition variableDefinition = parameterDefinition.getUserVariableDefinition();
		return variableDefinition.getType();
	}
	
	@Override
	public void parseStringValue(String value) {
		// don't set user variables
	}

	
}
