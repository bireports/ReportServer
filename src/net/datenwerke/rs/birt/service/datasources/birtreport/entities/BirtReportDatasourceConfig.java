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
 
 
package net.datenwerke.rs.birt.service.datasources.birtreport.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.birt.service.reportengine.entities.BirtReport;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinitionConfig;

import org.hibernate.envers.Audited;

@Entity
@Table(name="BIRT_REPORT_DATASRC_CFG")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.birt.client.datasources.dto"
)
public class BirtReportDatasourceConfig extends DatasourceDefinitionConfig {
	
	private static final long serialVersionUID = 206876596722300331L;


	@ExposeToClient
	@ManyToOne
	private BirtReport report;
	
	/**
	 * The name of the dataset / parameter the data is pulled from
	 */
	@ExposeToClient
	private String target;
	
	@ExposeToClient
	private BirtReportDatasourceTargetType targetType = BirtReportDatasourceTargetType.DATASET;
	
	@ExposeToClient
	private String queryWrapper;

	public String getQueryWrapper() {
		return queryWrapper;
	}

	public void setQueryWrapper(String queryWrapper) {
		this.queryWrapper = queryWrapper;
	}

	public BirtReport getReport() {
		return report;
	}

	public void setReport(BirtReport report) {
		this.report = report;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public BirtReportDatasourceTargetType getTargetType() {
		return targetType;
	}

	public void setTargetType(BirtReportDatasourceTargetType targetType) {
		this.targetType = targetType;
	}

}