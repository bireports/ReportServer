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
 
 
package net.datenwerke.scheduler.service.scheduler.entities.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.IllegalArgumentException;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.scheduler.client.scheduler.dto.MisfireInstructionDto;
import net.datenwerke.scheduler.service.scheduler.entities.MisfireInstruction;
import net.datenwerke.scheduler.service.scheduler.entities.dtogen.MisfireInstruction2DtoGenerator;

/**
 * Poso2DtoGenerator for MisfireInstruction
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class MisfireInstruction2DtoGenerator implements Poso2DtoGenerator<MisfireInstruction,MisfireInstructionDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public MisfireInstruction2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public MisfireInstructionDto instantiateDto(MisfireInstruction poso)  {
		/* Simply return the first enum! */
		MisfireInstructionDto dto = MisfireInstructionDto.EXECUTE_ONCE;
		return dto;
	}

	public MisfireInstructionDto createDto(MisfireInstruction poso, DtoView here, DtoView referenced)  {
		switch(poso){
			case EXECUTE_ONCE:
				return MisfireInstructionDto.EXECUTE_ONCE;
			case EXECUTE_ALL:
				return MisfireInstructionDto.EXECUTE_ALL;
		}
		throw new IllegalArgumentException("unknown enum type for: " + poso);
	}


}
