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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.dtoservices.dtogenerator.annotations.IgnoreMergeBackDto;
import net.datenwerke.eximport.ex.annotations.ExImportConfig;
import net.datenwerke.rs.base.service.reportengines.locale.ReportEnginesMessages;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.parameters.entities.ParameterDefinition;
import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.core.service.reportmanager.interfaces.ReportVariant;
import net.datenwerke.rs.utils.entitycloner.annotation.ClonePostProcessor;
import net.datenwerke.rs.utils.instancedescription.annotations.InstanceDescription;

import org.apache.commons.lang.NotImplementedException;
import org.hibernate.envers.Audited;
import org.hibernate.proxy.HibernateProxy;
import net.datenwerke.gf.base.service.annotations.Indexed;

/**
 *
 *
 */
@Entity
@Table(name="TABLE_REPORT_VARIANT")
@Audited
@Indexed
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.reportengines.table.dto", 
	createDecorator=true
)
@ExImportConfig(
	excludeFields={
		"datasourceContainer",
		"metadataDatasourceContainer",
		"parameterDefinitions"
	}
)
@InstanceDescription(
	msgLocation=ReportEnginesMessages.class,
	objNameKey="tableReportVariantTypeName",
	icon = "table"
)
public class TableReportVariant extends TableReport implements ReportVariant {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1314238195723702665L;

	public TableReport getBaseReport() {
		AbstractReportManagerNode parent = getParent();
		if(parent instanceof HibernateProxy)
			parent = (AbstractReportManagerNode) ((HibernateProxy)parent).getHibernateLazyInitializer().getImplementation();
		return (TableReport) parent;
	}

	public void setBaseReport(Report baseReport) {
		throw new IllegalStateException("Should not be called on Server");
	}
	
	@IgnoreMergeBackDto
	@Override
	public void setDatasourceContainer(DatasourceContainer datasource){
		throw new NotImplementedException();
	}
	
	@Override
	public DatasourceContainer getDatasourceContainer() {
		return getBaseReport().getDatasourceContainer();
	}
	
	@Override
	public DatasourceContainer getMetadataDatasourceContainer() {
		return getBaseReport().getMetadataDatasourceContainer();
	}
	
	@Override
	public boolean isAllowCubification() {
		return getBaseReport().isAllowCubification();
	}
	
	@IgnoreMergeBackDto
	@Override
	public void setMetadataDatasourceContainer(DatasourceContainer datasource) {
		throw new NotImplementedException();
	}

	@Override
	public List<ParameterDefinition> getParameterDefinitions() {
		return getBaseReport().getParameterDefinitions();
	}
	
	@IgnoreMergeBackDto
	@Override
	public void setParameterDefinitions( List<ParameterDefinition> parameters) {
		throw new NotImplementedException();
	}

	/**
	 */
	@ClonePostProcessor
	public void guideCloningProcess(Object report){
		super.setParameterDefinitions(null);
		super.setMetadataDatasourceContainer(null);
		super.setDatasourceContainer(null);
	}
	
	@Override
	public boolean hasChildren() {
		return false;
	}
}
