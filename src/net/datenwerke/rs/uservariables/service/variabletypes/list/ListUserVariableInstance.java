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
 
 
package net.datenwerke.rs.uservariables.service.variabletypes.list;

import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.OrderColumn;
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
@Table(name="LIST_USERVARIABLE_INST")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.uservariables.client.variabletypes.list.dto"
)
public class ListUserVariableInstance extends UserVariableInstance {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8818703141839593844L;
	
	@JoinTable(name="LIST_USERVARIABLE_INST_VL", joinColumns=@JoinColumn(name="list_user_var_instanc_id"))
	@ElementCollection
	@ExposeToClient
	@OrderColumn(name="val_n")
	private Set<String> value;
	
	@Override
	public Object getVariableValue() {
		return getValue();
	}

	public void setValue(Set<String> value) {
		this.value = value;
	}


	public Set<String> getValue() {
		return value;
	}
}
