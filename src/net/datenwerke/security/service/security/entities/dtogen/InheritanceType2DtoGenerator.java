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
 
 
package net.datenwerke.security.service.security.entities.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.IllegalArgumentException;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.security.client.security.dto.InheritanceTypeDto;
import net.datenwerke.security.service.security.entities.InheritanceType;
import net.datenwerke.security.service.security.entities.dtogen.InheritanceType2DtoGenerator;

/**
 * Poso2DtoGenerator for InheritanceType
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class InheritanceType2DtoGenerator implements Poso2DtoGenerator<InheritanceType,InheritanceTypeDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public InheritanceType2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public InheritanceTypeDto instantiateDto(InheritanceType poso)  {
		/* Simply return the first enum! */
		InheritanceTypeDto dto = InheritanceTypeDto.HERE;
		return dto;
	}

	public InheritanceTypeDto createDto(InheritanceType poso, DtoView here, DtoView referenced)  {
		switch(poso){
			case HERE:
				return InheritanceTypeDto.HERE;
			case INHERITED:
				return InheritanceTypeDto.INHERITED;
			case BOTH:
				return InheritanceTypeDto.BOTH;
		}
		throw new IllegalArgumentException("unknown enum type for: " + poso);
	}


}
