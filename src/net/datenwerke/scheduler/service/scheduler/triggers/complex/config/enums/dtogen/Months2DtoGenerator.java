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
 
 
package net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.IllegalArgumentException;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.MonthsDto;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.Months;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.dtogen.Months2DtoGenerator;

/**
 * Poso2DtoGenerator for Months
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Months2DtoGenerator implements Poso2DtoGenerator<Months,MonthsDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public Months2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public MonthsDto instantiateDto(Months poso)  {
		/* Simply return the first enum! */
		MonthsDto dto = MonthsDto.JANUARY;
		return dto;
	}

	public MonthsDto createDto(Months poso, DtoView here, DtoView referenced)  {
		switch(poso){
			case JANUARY:
				return MonthsDto.JANUARY;
			case FEBRUARY:
				return MonthsDto.FEBRUARY;
			case MARCH:
				return MonthsDto.MARCH;
			case APRIL:
				return MonthsDto.APRIL;
			case MAY:
				return MonthsDto.MAY;
			case JUNE:
				return MonthsDto.JUNE;
			case JULY:
				return MonthsDto.JULY;
			case AUGUST:
				return MonthsDto.AUGUST;
			case SEPTEMBER:
				return MonthsDto.SEPTEMBER;
			case OCTOBER:
				return MonthsDto.OCTOBER;
			case NOVEMBER:
				return MonthsDto.NOVEMBER;
			case DECEMBER:
				return MonthsDto.DECEMBER;
		}
		throw new IllegalArgumentException("unknown enum type for: " + poso);
	}


}
