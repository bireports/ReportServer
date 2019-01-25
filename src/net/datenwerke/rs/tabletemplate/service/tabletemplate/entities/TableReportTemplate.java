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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.rs.utils.entitycloner.annotation.TransientID;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

@Entity
@Table(name="TABLE_REPORT_TEMPLATE")
@Audited
@Inheritance(strategy=InheritanceType.JOINED)
@GenerateDto(
	dtoPackage="net.datenwerke.rs.tabletemplate.client.tabletemplate.dto",
	abstractDto=true
)
abstract public class TableReportTemplate {

	@ExposeToClient(
		view=DtoView.LIST,
		displayTitle=true
	)
	private String name = "unnamed";
	
	@ExposeToClient(
		view=DtoView.LIST
	)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	private String description; 
	
	@ExposeToClient(
		view=DtoView.LIST
	)
	private String contentType;
	
	@ExposeToClient(
		view=DtoView.LIST
	)
	private String fileExtension;
	
	@Version
	private Long version;
	
	@ExposeToClient(
		view=DtoView.LIST
	)
	private String key;
	
	@ExposeToClient(id=true)
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ExposeToClient(view=DtoView.MINIMAL)
	@Transient @TransientID
	private Long temporaryId;
	
	@ExposeToClient(view=DtoView.LIST)
	private String templateType;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setTemporaryId(Long temporaryId) {
		this.temporaryId = temporaryId;
	}

	public Long getTemporaryId() {
		return temporaryId;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getTemplateType() {
		return templateType;
	}
	
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public String getFileExtension() {
		return fileExtension;
	}
	
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}

	public void updateData(TableReportTemplate template) {
		setName(template.getName());
		setDescription(template.getDescription());
		setKey(template.getKey());
		setFileExtension(template.fileExtension);
	}

	final public TableReportTemplate createTemporary() {
		TableReportTemplate template = doCreateTemporary();
		
		template.setTemplateType(templateType);
		template.setName(name);
		template.setKey(key);
		template.setId(getId());
		template.setDescription(description);
		template.setFileExtension(fileExtension);
		
		return template;
	}

	abstract protected TableReportTemplate doCreateTemporary();




}
