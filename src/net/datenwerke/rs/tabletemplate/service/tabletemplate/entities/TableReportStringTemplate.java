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
 
 
package net.datenwerke.rs.tabletemplate.service.tabletemplate.entities;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

@Entity
@Table(name="TABLE_REPORT_STR_TEMPLATE")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.tabletemplate.client.tabletemplate.dto"
)
public class TableReportStringTemplate extends TableReportTemplate {
	
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	private String template;

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getTemplate() {
		return template;
	}
	
	@Override
	public void updateData(TableReportTemplate template) {
		if(! (template instanceof TableReportStringTemplate))
			throw new IllegalArgumentException("Expected " + TableReportStringTemplate.class);
		
		super.updateData(template);
		
		setTemplate(((TableReportStringTemplate)template).getTemplate());
	}
	
	@Override
	protected TableReportTemplate doCreateTemporary() {
		TableReportStringTemplate template = new TableReportStringTemplate();
		template.setTemplate(getTemplate());
		return template;
	}

}
