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

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.uservariables.service.uservariables.entities.UserVariableInstance;

import org.hibernate.envers.Audited;

/**
 * 
 *
 */
@Entity
@Table(name="STR_USERVARIABLE_INST")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.uservariables.client.variabletypes.string.dto"
)
public class StringUserVariableInstance extends UserVariableInstance {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2419315182953436034L;
	
	
	@ExposeToClient
	private String value;
	
	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public Object getVariableValue() {
		return getValue();
	}
}
