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
 
 
package net.datenwerke.rs.scriptreport.service.scriptreport.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.scriptreport.service.scriptreport.locale.ScriptReportMessages;
import net.datenwerke.rs.utils.instancedescription.annotations.InstanceDescription;
import net.datenwerke.treedb.service.treedb.annotation.TreeDBAllowedChildren;

import org.hibernate.envers.Audited;
import net.datenwerke.gf.base.service.annotations.Indexed;

@Entity
@Table(name="SCRIPT_REPORT")
@Audited
@Indexed
@Inheritance(strategy=InheritanceType.JOINED)
@GenerateDto(
	dtoPackage="net.datenwerke.rs.scriptreport.client.scriptreport.dto",
	createDecorator=true,
	typeDescriptionMsg=net.datenwerke.rs.scriptreport.client.scriptreport.locale.ScriptReportMessages.class,
	typeDescriptionKey="reportTypeName"
)
@TreeDBAllowedChildren({
	ScriptReportVariant.class
})
@InstanceDescription(
	msgLocation = ScriptReportMessages.class,
	objNameKey = "scriptReportTypeName",
	icon = "script"
)
public class ScriptReport extends Report {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1888604254381316817L;

	@ExposeToClient
	@ManyToOne
	private FileServerFile script;
	
	@ExposeToClient
	private String arguments;
	
	@JoinTable(name="SCRIPT_REPORT_2_EX_FORMAT")
	@ExposeToClient(view=DtoView.LIST)
	@ElementCollection
	@OrderColumn(name="val_n")
	private List<String> exportFormats = new ArrayList<String>();
	
	public void setScript(FileServerFile script) {
		this.script = script;
	}

	public FileServerFile getScript() {
		return script;
	}
	
	public List<String> getExportFormats() {
		return exportFormats;
	}

	public void setExportFormats(List<String> exportFormats) {
		this.exportFormats = exportFormats;
	}
	
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	public String getArguments() {
		return arguments;
	}


	@Override
	protected Report createVariant(Report report) {
		if(! (report instanceof ScriptReport))
			throw new IllegalArgumentException("Expected ScriptReport"); //$NON-NLS-1$
		
		ScriptReportVariant variant = new ScriptReportVariant();
		
		/* copy parameter instances */
		initVariant(variant, report);
		
		return variant;
		
	}


	
}
