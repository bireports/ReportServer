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
 
 
package net.datenwerke.rs.computedcolumns.service.computedcolumns.entities;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.service.reportengines.table.entities.AdditionalColumnSpec;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

@Entity
@Table(name="COMPUTED_COLUMN")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.computedcolumns.client.computedcolumns.dto"
)
public class ComputedColumn extends AdditionalColumnSpec {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6766016497691991076L;

	@ExposeToClient(disableHtmlEncode=true)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	private String expression;
	
	/* @ExposeToClient -> is exposed by Column */
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	private String description;
	
	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getExpression() {
		return expression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
