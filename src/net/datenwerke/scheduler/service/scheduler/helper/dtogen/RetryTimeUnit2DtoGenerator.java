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
 
 
package net.datenwerke.scheduler.service.scheduler.helper.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.IllegalArgumentException;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.scheduler.client.scheduler.dto.config.complex.RetryTimeUnitDto;
import net.datenwerke.scheduler.service.scheduler.helper.RetryTimeUnit;
import net.datenwerke.scheduler.service.scheduler.helper.dtogen.RetryTimeUnit2DtoGenerator;

/**
 * Poso2DtoGenerator for RetryTimeUnit
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class RetryTimeUnit2DtoGenerator implements Poso2DtoGenerator<RetryTimeUnit,RetryTimeUnitDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public RetryTimeUnit2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public RetryTimeUnitDto instantiateDto(RetryTimeUnit poso)  {
		/* Simply return the first enum! */
		RetryTimeUnitDto dto = RetryTimeUnitDto.MINUTES;
		return dto;
	}

	public RetryTimeUnitDto createDto(RetryTimeUnit poso, DtoView here, DtoView referenced)  {
		switch(poso){
			case MINUTES:
				return RetryTimeUnitDto.MINUTES;
			case HOURS:
				return RetryTimeUnitDto.HOURS;
			case DAYS:
				return RetryTimeUnitDto.DAYS;
			case WEEKS:
				return RetryTimeUnitDto.WEEKS;
			case MONTHS:
				return RetryTimeUnitDto.MONTHS;
			case YEARS:
				return RetryTimeUnitDto.YEARS;
		}
		throw new IllegalArgumentException("unknown enum type for: " + poso);
	}


}
