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
 
 
package net.datenwerke.rs.birt.service.reportengine.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.client.reportengines.jasper.locale.JasperMessages;
import net.datenwerke.rs.birt.client.reportengines.locale.BirtMessages;
import net.datenwerke.rs.birt.service.reportengine.BirtReportService;
import net.datenwerke.rs.birt.service.reportengine.locale.BirtEngineMessages;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;
import net.datenwerke.rs.utils.instancedescription.annotations.InstanceDescription;
import net.datenwerke.treedb.service.treedb.annotation.TreeDBAllowedChildren;

import org.hibernate.envers.Audited;

import net.datenwerke.gf.base.service.annotations.Indexed;

import com.google.inject.Injector;

@Entity
@Table(name="BIRT_REPORT")
@Audited
@Indexed
@TreeDBAllowedChildren({
	BirtReportVariant.class
})
@GenerateDto(
	dtoPackage="net.datenwerke.rs.birt.client.reportengines.dto",
	createDecorator=true,
	typeDescriptionMsg=BirtMessages.class,
	typeDescriptionKey="reportTypeName",
	icon="jpg"
)
@InstanceDescription(
	msgLocation=BirtEngineMessages.class,
	objNameKey="birtReportTypeName",
	icon = "file"
)
public class BirtReport extends Report{

	/**
	 * 
	 */
	private static final long serialVersionUID = 131396070918781142L;

	@EnclosedEntity
	@ExposeToClient
	@OneToOne(cascade={CascadeType.ALL})
	private BirtReportFile reportFile;

	
	public void setReportFile(BirtReportFile reportFile) {
		this.reportFile = reportFile;
	}
	
	
	public BirtReportFile getReportFile() {
		return reportFile;
	}


	@Override
	protected Report createVariant(Report report) {
		if(! (report instanceof BirtReport))
			throw new IllegalArgumentException("Expected BirtReport"); //$NON-NLS-1$
		
		BirtReportVariant variant = new BirtReportVariant();
		
		/* copy parameter instances */
		initVariant(variant, report);
		
		return variant;
	}

	@Override
	public void replaceWith(Report aReport, Injector injector){
		if(! (aReport instanceof BirtReport))
			throw new IllegalArgumentException("Expected BirtReport"); //$NON-NLS-1$
		super.replaceWith(aReport, injector);
		
		BirtReport report = (BirtReport) aReport;
		
		if(null != reportFile)
			injector.getInstance(BirtReportService.class).remove(reportFile);
		setReportFile(report.getReportFile());
	}
}
