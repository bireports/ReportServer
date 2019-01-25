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
 
 
package net.datenwerke.rs.grideditor.service.grideditor.definition.validator.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.grideditor.client.grideditor.dto.MaxBigDecimalValidatorDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.decorator.MaxBigDecimalValidatorDtoDec;
import net.datenwerke.rs.grideditor.service.grideditor.definition.validator.MaxBigDecimalValidator;
import net.datenwerke.rs.grideditor.service.grideditor.definition.validator.dtogen.MaxBigDecimalValidator2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for MaxBigDecimalValidator
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class MaxBigDecimalValidator2DtoGenerator implements Poso2DtoGenerator<MaxBigDecimalValidator,MaxBigDecimalValidatorDtoDec> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public MaxBigDecimalValidator2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public MaxBigDecimalValidatorDtoDec instantiateDto(MaxBigDecimalValidator poso)  {
		MaxBigDecimalValidatorDtoDec dto = new MaxBigDecimalValidatorDtoDec();
		return dto;
	}

	public MaxBigDecimalValidatorDtoDec createDto(MaxBigDecimalValidator poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final MaxBigDecimalValidatorDtoDec dto = new MaxBigDecimalValidatorDtoDec();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set errorMsg */
			dto.setErrorMsg(StringEscapeUtils.escapeXml(StringUtils.left(poso.getErrorMsg(),8192)));

			/*  set number */
			dto.setNumber(poso.getNumber() );

		}

		return dto;
	}


}
