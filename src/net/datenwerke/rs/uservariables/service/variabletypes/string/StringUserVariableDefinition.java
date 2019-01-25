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
 
 
package net.datenwerke.rs.uservariables.service.variabletypes.string;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.uservariables.client.uservariables.locale.UserVariablesMessages;
import net.datenwerke.rs.uservariables.service.uservariables.entities.UserVariableDefinition;
import net.datenwerke.rs.uservariables.service.uservariables.entities.UserVariableInstance;

import org.hibernate.envers.Audited;

/**
 * 
 *
 */
@Entity
@Table(name="STR_USERVARIABLE_DEF")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.uservariables.client.variabletypes.string.dto",
	displayTitle="UserVariablesMessages.INSTANCE.stringVariableText()",
	additionalImports=UserVariablesMessages.class
)
public class StringUserVariableDefinition extends UserVariableDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2670503506233986073L;

	@ExposeToClient
	private Integer width = 150;
	
	@ExposeToClient
	private Integer height = 1;
	
	public Integer getWidth(){ 
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}
	
	public Integer getHeight(){ 
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
	
	@Override
	protected UserVariableInstance doCreateVariableInstance() {
		return new StringUserVariableInstance();
	}

	@Transient
	@Override
	public Class<?> getType() {
		return String.class;
	}

}
