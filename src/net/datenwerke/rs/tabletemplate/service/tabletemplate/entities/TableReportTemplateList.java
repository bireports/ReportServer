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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportProperty;
import net.datenwerke.rs.tabletemplate.service.tabletemplate.TableTemplateConstants;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;

@Entity
@Table(name="TABLE_REPORT_TEMPLATE_LST")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.tabletemplate.client.tabletemplate.dto"
)
public class TableReportTemplateList extends ReportProperty implements Iterable<TableReportTemplate> {

	@JoinTable(name="TAB_REP_TPL_LST_2_TPL")
	@EnclosedEntity
	@OneToMany(cascade=CascadeType.ALL)
	private Set<TableReportTemplate> templates = new HashSet<TableReportTemplate>();

	public TableReportTemplateList(){
		super(TableTemplateConstants.TEMPLATE_LIST_PROPERTY_NAME);
		
	}
	
	@Override
	public void setName(String name) {
		throw new UnsupportedOperationException("Cannot change name of this property");
	}
	
	public void setTemplates(Set<TableReportTemplate> templates) {
		this.templates = templates;
	}

	public Set<TableReportTemplate> getTemplates() {
		return templates;
	}

	@Override
	public Iterator<TableReportTemplate> iterator() {
		return templates.iterator();
	}

	public void add(TableReportTemplate template) {
		templates.add(template);
	}
	
	
}
