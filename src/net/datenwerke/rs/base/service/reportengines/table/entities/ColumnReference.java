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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;

import org.hibernate.envers.Audited;

@Entity
@Table(name="COLUMN_REFERENCE")
@Inheritance(strategy=InheritanceType.JOINED)
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.table.dto",
	createDecorator=true
)
public class ColumnReference extends Column {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8283001475354861348L;

	@ExposeToClient
	@ManyToOne
	private AdditionalColumnSpec reference;

	public void setReference(AdditionalColumnSpec reference) {
		this.reference = reference;
	}

	public AdditionalColumnSpec getReference() {
		return reference;
	}
	
	@Override
	public String getName() {
		if(null == getReference())
			return "";
		return getReference().getName();
	}
	
	@Override
	public void setName(String name) {
	}
	
	@Override
	public String getDescription() {
		if(null == getReference())
			return "";
		return getReference().getDescription();
	}
	
	@Override
	public void setDescription(String description) {
	}
	
	@Override
	public Integer getType() {
		if(null == getReference())
			return null;
		return getReference().getType();
	}
	
	@Override
	public void setType(Integer type) {
	}
}
