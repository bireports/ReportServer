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
 
 
package net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.IllegalArgumentException;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.TimeUnitsDto;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.TimeUnits;
import net.datenwerke.scheduler.service.scheduler.triggers.complex.config.enums.dtogen.TimeUnits2DtoGenerator;

/**
 * Poso2DtoGenerator for TimeUnits
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class TimeUnits2DtoGenerator implements Poso2DtoGenerator<TimeUnits,TimeUnitsDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public TimeUnits2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public TimeUnitsDto instantiateDto(TimeUnits poso)  {
		/* Simply return the first enum! */
		TimeUnitsDto dto = TimeUnitsDto.HOURS;
		return dto;
	}

	public TimeUnitsDto createDto(TimeUnits poso, DtoView here, DtoView referenced)  {
		switch(poso){
			case HOURS:
				return TimeUnitsDto.HOURS;
			case MINUTES:
				return TimeUnitsDto.MINUTES;
		}
		throw new IllegalArgumentException("unknown enum type for: " + poso);
	}


}
