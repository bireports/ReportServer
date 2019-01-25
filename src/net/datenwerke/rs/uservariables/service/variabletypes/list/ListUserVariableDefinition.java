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
 
 
package net.datenwerke.rs.uservariables.service.variabletypes.list;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name="LIST_USERVARIABLE_DEF")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.uservariables.client.variabletypes.list.dto",
	displayTitle="UserVariablesMessages.INSTANCE.listVariableText()",
	additionalImports=UserVariablesMessages.class
)
public class ListUserVariableDefinition extends UserVariableDefinition {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6514324539059075005L;

	@Override
	protected UserVariableInstance doCreateVariableInstance() {
		return new ListUserVariableInstance();
	}

	@Transient
	@Override
	public Class<?> getType() {
		return List.class;
	}

}
