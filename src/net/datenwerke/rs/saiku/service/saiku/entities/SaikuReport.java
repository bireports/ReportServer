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
 
 
package net.datenwerke.rs.saiku.service.saiku.entities;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import com.google.inject.Injector;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gf.base.service.annotations.Field;
import net.datenwerke.gf.base.service.annotations.Indexed;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.saiku.client.saiku.locale.SaikuMessages;
import net.datenwerke.rs.saiku.service.saiku.locale.SaikuEngineMessages;
import net.datenwerke.rs.utils.instancedescription.annotations.InstanceDescription;
import net.datenwerke.treedb.service.treedb.annotation.TreeDBAllowedChildren;

@Entity
@Table(name="SAIKU_REPORT")
@Audited
@Indexed
@TreeDBAllowedChildren({
	SaikuReportVariant.class
})
@GenerateDto(
	dtoPackage="net.datenwerke.rs.saiku.client.saiku.dto",
	createDecorator=true,
	typeDescriptionMsg=SaikuMessages.class,
	typeDescriptionKey="reportTypeName",
	icon="cubes"
)
@InstanceDescription(
	msgLocation=SaikuEngineMessages.class,
	objNameKey="reportTypeName",
	icon = "cubes"
)
public class SaikuReport extends Report {

	private static final long serialVersionUID = 895906038962019952L;

	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	@Field
	@ExposeToClient(allowArbitraryLobSize=true,disableHtmlEncode=true,exposeValueToClient=false)
	private String queryXml;
	
	@ExposeToClient
	private boolean allowMdx;
	
	@Deprecated
	private boolean hideParents;
	
	public String getQueryXml() {
		return queryXml;
	}

	public void setQueryXml(String queryXml) {
		this.queryXml = queryXml;
	}

	@Deprecated
	public void setHideParents(boolean hideParents) {
		this.hideParents = hideParents;
	}
	
	@Deprecated
	public boolean isHideParents() {
		return hideParents;
	}
	
	@Override
	protected Report createVariant(Report report) {
		if(! (report instanceof SaikuReport))
			throw new IllegalArgumentException("Expected SaikuReport");
		
		SaikuReportVariant variant = new SaikuReportVariant();
		
		/* copy parameter instances */
		initVariant(variant, report);
		
		variant.setQueryXml(((SaikuReport)report).getQueryXml());
		variant.setHideParents(((SaikuReport)report).isHideParents());
		
		return variant;
		
	}

	@Override
	public void replaceWith(Report aReport, Injector injector) {
		if(! (aReport instanceof SaikuReport))
			throw new IllegalArgumentException("Expected SaikuReport");
		
		super.replaceWith(aReport, injector);
		
		SaikuReport report = (SaikuReport) aReport;
		
		/* set any fields from this report */
		setQueryXml(report.getQueryXml());
	}

	public boolean isAllowMdx() {
		return allowMdx;
	}

	public void setAllowMdx(boolean allowMdx) {
		this.allowMdx = allowMdx;
	}
	
	
	
}
