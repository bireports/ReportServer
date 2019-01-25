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
 
 
package net.datenwerke.rs.dashboard.service.dashboard.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.client.dtomanager.FolderDto;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.rs.core.service.reportmanager.interfaces.ReportVariant;
import net.datenwerke.rs.dashboard.service.dashboard.locale.DashboardMessages;
import net.datenwerke.rs.utils.instancedescription.annotations.Description;
import net.datenwerke.rs.utils.instancedescription.annotations.InstanceDescription;
import net.datenwerke.rs.utils.instancedescription.annotations.Title;
import net.datenwerke.treedb.service.treedb.annotation.TreeDBAllowedChildren;
import net.datenwerke.treedb.service.treedb.annotation.TreeDBDeniedChildren;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import net.datenwerke.gf.base.service.annotations.Field;
import net.datenwerke.gf.base.service.annotations.Indexed;

/**
 * Entity that can be used to organize reports and reportgroups hierarchically.
 * 
 *
 */
@Entity
@Table(name="DASHBOARD_FOLDER")
@Audited
@Indexed
@TreeDBAllowedChildren({
	DadgetNode.class,
	DashboardNode.class,
	
	DashboardFolder.class
})
@TreeDBDeniedChildren(ReportVariant.class)
@GenerateDto(
	dtoPackage="net.datenwerke.rs.dashboard.client.dashboard.dto",
	dtoImplementInterfaces=FolderDto.class,
	typeDescriptionMsg=BaseMessages.class, typeDescriptionKey="folder",
	icon="folder"
)
@InstanceDescription(
	msgLocation=DashboardMessages.class,
	objNameKey="folderTypeName",		
	icon = "folder"
)
public class DashboardFolder extends AbstractDashboardManagerNode  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3139118358223220435L;

	@ExposeToClient(
		view=DtoView.MINIMAL,
		displayTitle=true
	)
	@Column(length = 128)
	@Field
	@Title
	private String name;

	@ExposeToClient(view=DtoView.MINIMAL)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	@Field
	@Description
    private String description;
	
	public DashboardFolder(){
		
	}

	public DashboardFolder(String name){
		setName(name);
	}
	
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
    
	@Override
	public boolean isFolder() {
		return true;
	}

}
