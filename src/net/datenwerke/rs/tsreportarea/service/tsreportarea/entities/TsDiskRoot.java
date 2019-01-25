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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.treedb.service.treedb.annotation.TreeDBAllowedChildren;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

/**
 * 
 *
 */
@Entity
@Table(name="TS_DISK_ROOT")
@Audited
@TreeDBAllowedChildren({
	TsDiskFolder.class,
	TsDiskReportReference.class,
	TsDiskGeneralReference.class
})
@GenerateDto(
	dtoPackage="net.datenwerke.rs.tsreportarea.client.tsreportarea.dto"
)
public class TsDiskRoot extends AbstractTsDiskNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5537404632434918329L;
	
	@ExposeToClient(
		view=DtoView.MINIMAL,
		displayTitle=true
	)
	@Column(length = 128)
	private String name = "Root";

	@ExposeToClient(view=DtoView.MINIMAL)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
    private String description;
	
	@ExposeToClient
	@OneToOne
	private TeamSpace teamSpace;

	public void setTeamSpace(TeamSpace teamSpace) {
		this.teamSpace = teamSpace;
	}

	public TeamSpace getTeamSpace() {
		return teamSpace;
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

}
