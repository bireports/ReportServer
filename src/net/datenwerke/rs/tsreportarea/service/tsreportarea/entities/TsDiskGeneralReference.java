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
 
 
package net.datenwerke.rs.tsreportarea.service.tsreportarea.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.datenwerke.dtoservices.dtogenerator.annotations.AdditionalField;
import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.post.GeneralReference2DtoPost;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.locale.TsDiskMessages;
import net.datenwerke.rs.utils.instancedescription.annotations.Description;
import net.datenwerke.rs.utils.instancedescription.annotations.InstanceDescription;
import net.datenwerke.rs.utils.instancedescription.annotations.Title;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import net.datenwerke.gf.base.service.annotations.Field;

/**
 * 
 *
 */
@Entity
@Table(name="TS_DISK_GENERAL_REFERENCE")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.tsreportarea.client.tsreportarea.dto",
	createDecorator=true,
	abstractDto = true,
	poso2DtoPostProcessors=GeneralReference2DtoPost.class,
	additionalFields = {
		@AdditionalField(name="referenceLastUpdated", type=Date.class)
	}
)
@InstanceDescription(
	msgLocation=TsDiskMessages.class,
	objNameKey="tsDiskGeneralReferenceName",
	icon = "file"
)
public abstract class TsDiskGeneralReference extends AbstractTsDiskNode{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2253693289691622558L;
	
	@ExposeToClient(
		view=DtoView.MINIMAL,
		displayTitle=true
	)
	@Field
	@Column(length = 128)
	@Title
	private String name;

	@ExposeToClient(view=DtoView.MINIMAL)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	@Field
	@Description
    private String description;
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	@Transient
	abstract public Date getReferenceLastUpdated();

	@Transient
	abstract public boolean hasData();

	@Transient
	public byte[] getData(){
		return null;
	}

	@Transient
	public String getDataContentType() {
		return null;
	}

	@Transient
	public long getSize() {
		return 0;
	}

}
