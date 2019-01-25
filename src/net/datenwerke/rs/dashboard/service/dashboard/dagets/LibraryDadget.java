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
 
 
package net.datenwerke.rs.dashboard.service.dashboard.dagets;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.dashboard.service.dashboard.dagets.post.LibraryDadget2DtoPost;
import net.datenwerke.rs.dashboard.service.dashboard.entities.Dadget;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DadgetNode;

@Entity
@Table(name="DADGET_LIBRARY")
@GenerateDto(
	dtoPackage="net.datenwerke.rs.dashboard.client.dashboard.dto",
	createDecorator=true,
	poso2DtoPostProcessors=LibraryDadget2DtoPost.class
)
public class LibraryDadget extends Dadget {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4343069561738262668L;
	
	@ExposeToClient(inheritDtoView=true)
	@ManyToOne
	private DadgetNode dadgetNode;

	public DadgetNode getDadgetNode() {
		return dadgetNode;
	}

	public void setDadgetNode(DadgetNode dadgetNode) {
		this.dadgetNode = dadgetNode;
	}


}