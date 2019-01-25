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
 
 
package net.datenwerke.rs.base.service.reportengines.table.entities.format.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnFormatDateDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.decorator.ColumnFormatDateDtoDec;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.ColumnFormatDate;
import net.datenwerke.rs.base.service.reportengines.table.entities.format.dtogen.ColumnFormatDate2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for ColumnFormatDate
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class ColumnFormatDate2DtoGenerator implements Poso2DtoGenerator<ColumnFormatDate,ColumnFormatDateDtoDec> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public ColumnFormatDate2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public ColumnFormatDateDtoDec instantiateDto(ColumnFormatDate poso)  {
		ColumnFormatDateDtoDec dto = new ColumnFormatDateDtoDec();
		return dto;
	}

	public ColumnFormatDateDtoDec createDto(ColumnFormatDate poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final ColumnFormatDateDtoDec dto = new ColumnFormatDateDtoDec();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set id */
			dto.setId(poso.getId() );

		}
		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set baseFormat */
			dto.setBaseFormat(StringEscapeUtils.escapeXml(StringUtils.left(poso.getBaseFormat(),8192)));

			/*  set errorReplacement */
			dto.setErrorReplacement(StringEscapeUtils.escapeXml(StringUtils.left(poso.getErrorReplacement(),8192)));

			/*  set replaceErrors */
			dto.setReplaceErrors(poso.isReplaceErrors() );

			/*  set rollOver */
			dto.setRollOver(poso.isRollOver() );

			/*  set targetFormat */
			dto.setTargetFormat(StringEscapeUtils.escapeXml(StringUtils.left(poso.getTargetFormat(),8192)));

		}

		return dto;
	}


}
