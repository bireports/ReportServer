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
 
 
package net.datenwerke.rs.dashboard.service.dashboard.dagets.post;

import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoPostProcessor;
import net.datenwerke.rs.dashboard.client.dashboard.dto.LibraryDadgetDto;
import net.datenwerke.rs.dashboard.service.dashboard.dagets.LibraryDadget;
import net.datenwerke.rs.dashboard.service.dashboard.entities.DadgetNode;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.rights.Read;

import com.google.inject.Inject;

public class LibraryDadget2DtoPost
		implements
		Poso2DtoPostProcessor<LibraryDadget, LibraryDadgetDto> {

	private final SecurityService securityService;
	
	@Inject
	public LibraryDadget2DtoPost(SecurityService securityService) {
		this.securityService = securityService;
	}

	@Override
	public void dtoCreated(LibraryDadget poso, LibraryDadgetDto dto) {
		DadgetNode dadgetNode = poso.getDadgetNode();
		if(null != dadgetNode && ! securityService.checkRights(dadgetNode, Read.class))
			dto.setDadgetNode(null);
	}

	@Override
	public void dtoInstantiated(LibraryDadget arg0, LibraryDadgetDto arg1) {
		// TODO Auto-generated method stub
		
	}


}
