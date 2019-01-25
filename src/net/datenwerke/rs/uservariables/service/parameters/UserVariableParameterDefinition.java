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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.uservariables.client.uservariables.locale.UserVariablesMessages;
import net.datenwerke.rs.uservariables.service.uservariables.entities.UserVariableDefinition;

import org.hibernate.envers.Audited;

/**
 *
 */
@Entity
@Table(name="USERVAR_PARAM_DEF")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.uservariables.client.parameters.dto",
	displayTitle="UserVariablesMessages.INSTANCE.userVariablesParameterText()",
	additionalImports=UserVariablesMessages.class
)
public class UserVariableParameterDefinition extends ParameterDefinition<UserVariableParameterInstance> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7110014518870459228L;
	
	@ExposeToClient
	@ManyToOne(targetEntity=UserVariableDefinition.class)
	private UserVariableDefinition userVariableDefinition;
	
	public void setUserVariableDefinition(UserVariableDefinition userVariableDefinition) {
		this.userVariableDefinition = userVariableDefinition;
	}

	public UserVariableDefinition getUserVariableDefinition() {
		return userVariableDefinition;
	}
	
	@Override
	protected UserVariableParameterInstance doCreateParameterInstance(){
		return new UserVariableParameterInstance();
	}

	@Override
	public Boolean isEditable() {
		return false;
	}
}
