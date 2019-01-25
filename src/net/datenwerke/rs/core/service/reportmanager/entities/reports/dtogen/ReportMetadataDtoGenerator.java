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
 
 
package net.datenwerke.rs.core.service.reportmanager.entities.reports.dtogen;

import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportMetadataDto;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportMetadata;

import com.google.inject.Inject;

/**
 * Poso2DtoGenerator for ReportProperty
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class ReportMetadataDtoGenerator implements Poso2DtoGenerator<ReportMetadata,ReportMetadataDto> {

	private final DtoService dtoService;

	@Inject
	public ReportMetadataDtoGenerator(
		DtoService dtoService	){
		this.dtoService = dtoService;
	}

	public ReportMetadataDto instantiateDto(ReportMetadata poso)  {
		ReportMetadataDto dto = new ReportMetadataDto();
		return dto;
	}

	public ReportMetadataDto createDto(ReportMetadata poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		ReportMetadataDto dto = new ReportMetadataDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set id */
			dto.setId(poso.getId() );

			/*  set name */
			dto.setName(poso.getName() );

		}
		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set value */
			dto.setValue(poso.getValue() );

		}

		return dto;
	}


}
