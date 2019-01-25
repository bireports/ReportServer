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
 
 
package net.datenwerke.rs.crystal.service.crystal.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.google.inject.Injector;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gf.base.service.annotations.Indexed;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.crystal.client.crystal.locale.CrystalMessages;
import net.datenwerke.rs.crystal.service.crystal.CrystalReportService;
import net.datenwerke.rs.crystal.service.crystal.locale.CrystalEngineMessages;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;
import net.datenwerke.rs.utils.instancedescription.annotations.InstanceDescription;
import net.datenwerke.treedb.service.treedb.annotation.TreeDBAllowedChildren;

@Entity
@Table(name="CRYSTAL_REPORT")
@Audited
@Indexed
@TreeDBAllowedChildren({
	CrystalReportVariant.class
})
@GenerateDto(
	dtoPackage="net.datenwerke.rs.crystal.client.crystal.dto",
	createDecorator=true,
	typeDescriptionMsg=CrystalMessages.class,
	typeDescriptionKey="reportTypeName",
	icon="jpg"
)
@InstanceDescription(
	msgLocation=CrystalEngineMessages.class,
	objNameKey="birtReportTypeName",
	icon = "file"
)
public class CrystalReport extends Report {

	/**
	 * 
	 */
	private static final long serialVersionUID = 436406833662339663L;
	
	@EnclosedEntity
	@ExposeToClient
	@OneToOne(cascade={CascadeType.ALL})
	private CrystalReportFile reportFile;
	
	
	public void setReportFile(CrystalReportFile reportFile) {
		this.reportFile = reportFile;
	}
	
	public CrystalReportFile getReportFile() {
		return reportFile;
	}

	@Override
	protected Report createVariant(Report report) {
		if(! (report instanceof CrystalReport))
			throw new IllegalArgumentException("Expected CrystalReport");
		
		CrystalReportVariant variant = new CrystalReportVariant();
		
		/* copy parameter instances */
		initVariant(variant, report);
		
		return variant;
		
	}

	@Override
	public void replaceWith(Report aReport, Injector injector) {
		if(! (aReport instanceof CrystalReport))
			throw new IllegalArgumentException("Expected CrystalReport");
		
		super.replaceWith(aReport, injector);
		
		CrystalReport report = (CrystalReport) aReport;
		
		if(null != reportFile)
			injector.getInstance(CrystalReportService.class).remove(reportFile);
		setReportFile(report.getReportFile());
	}

}
