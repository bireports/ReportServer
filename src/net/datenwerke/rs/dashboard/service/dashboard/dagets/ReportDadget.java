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
 
 
package net.datenwerke.rs.dashboard.service.dashboard.dagets;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.dashboard.service.dashboard.entities.Dadget;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskReportReference;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;


@Entity
@Table(name="DADGET_REPORT")
@GenerateDto(
	dtoPackage="net.datenwerke.rs.dashboard.client.dashboard.dto",
	createDecorator=true
)
public class ReportDadget extends Dadget {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3154364200782237989L;

	@ExposeToClient(inheritDtoView=true)
	@ManyToOne(fetch=FetchType.LAZY)
	private Report report;
	
	@ExposeToClient(inheritDtoView=true)
	@ManyToOne(fetch=FetchType.LAZY)
	private TsDiskReportReference reportReference;
	
	@ExposeToClient(
			view=DtoView.MINIMAL
			)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	private String config;
	
	@JoinTable(name="DADGET_REPORT_2_PARAM_INST")
	@ExposeToClient
	@EnclosedEntity
    @OneToMany(cascade={CascadeType.ALL}, orphanRemoval=true)
    private Set<ParameterInstance> parameterInstances = new HashSet<ParameterInstance>();
	
	public void setReport(Report report) {
		this.report = report;
		if(null != report)
			setReportReference(null);
	}

	public Report getReport() {
		return report;
	}

	public void setReportReference(TsDiskReportReference reportReference) {
		this.reportReference = reportReference;
		if(null != reportReference)
			report = null;
	}

	public TsDiskReportReference getReportReference() {
		return reportReference;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public Set<ParameterInstance> getParameterInstances() {
		return parameterInstances;
	}

	public void setParameterInstances(Set<ParameterInstance> parameterInstances) {
		if(null == parameterInstances)
			parameterInstances = new HashSet<ParameterInstance>();
			
		this.parameterInstances.clear();
		this.parameterInstances.addAll(parameterInstances);
	}

}
