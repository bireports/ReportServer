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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.format;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.utils.juel.SimpleJuel;

import org.hibernate.envers.Audited;

import com.google.inject.Inject;
import com.google.inject.Provider;

@Entity
@Table(name="COLUMN_FORMAT_TEMPLATE")
@Inheritance(strategy=InheritanceType.JOINED)
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.table.dto",
	createDecorator=true
)
public class ColumnFormatTemplate extends ColumnFormat{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7426511593850193265L;

	@Transient @Inject
	protected static Provider<SimpleJuel> simpleJuelProvider;
	
	@ExposeToClient
	private String template;
	
	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTemplate() {
		return template;
	}
	
	@Override
	public String format(Object value) {
		SimpleJuel juel = simpleJuelProvider.get();
		
		juel.addReplacement("isNull", value == null);
		juel.addReplacement("value", value);
		
		return juel.parse(template);
	}

}
