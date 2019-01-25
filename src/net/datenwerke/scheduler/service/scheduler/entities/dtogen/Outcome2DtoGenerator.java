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
 
 
package net.datenwerke.scheduler.service.scheduler.entities.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.IllegalArgumentException;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.scheduler.client.scheduler.dto.OutcomeDto;
import net.datenwerke.scheduler.service.scheduler.entities.Outcome;
import net.datenwerke.scheduler.service.scheduler.entities.dtogen.Outcome2DtoGenerator;

/**
 * Poso2DtoGenerator for Outcome
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Outcome2DtoGenerator implements Poso2DtoGenerator<Outcome,OutcomeDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public Outcome2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public OutcomeDto instantiateDto(Outcome poso)  {
		/* Simply return the first enum! */
		OutcomeDto dto = OutcomeDto.SUCCESS;
		return dto;
	}

	public OutcomeDto createDto(Outcome poso, DtoView here, DtoView referenced)  {
		switch(poso){
			case SUCCESS:
				return OutcomeDto.SUCCESS;
			case FAILURE:
				return OutcomeDto.FAILURE;
			case VETO:
				return OutcomeDto.VETO;
			case EXECUTING:
				return OutcomeDto.EXECUTING;
		}
		throw new IllegalArgumentException("unknown enum type for: " + poso);
	}


}
