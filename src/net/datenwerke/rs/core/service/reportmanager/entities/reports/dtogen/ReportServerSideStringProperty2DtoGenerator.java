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
 
 
package net.datenwerke.rs.core.service.reportmanager.entities.reports.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportServerSideStringPropertyDto;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.ReportServerSideStringProperty;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.dtogen.ReportServerSideStringProperty2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for ReportServerSideStringProperty
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class ReportServerSideStringProperty2DtoGenerator implements Poso2DtoGenerator<ReportServerSideStringProperty,ReportServerSideStringPropertyDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public ReportServerSideStringProperty2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public ReportServerSideStringPropertyDto instantiateDto(ReportServerSideStringProperty poso)  {
		ReportServerSideStringPropertyDto dto = new ReportServerSideStringPropertyDto();
		return dto;
	}

	public ReportServerSideStringPropertyDto createDto(ReportServerSideStringProperty poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final ReportServerSideStringPropertyDto dto = new ReportServerSideStringPropertyDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set id */
			dto.setId(poso.getId() );

			/*  set name */
			dto.setName(StringEscapeUtils.escapeXml(StringUtils.left(poso.getName(),8192)));

		}
		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set strValue */
			dto.setStrValue(StringEscapeUtils.escapeXml(StringUtils.left(poso.getStrValue(),8192)));

		}

		return dto;
	}


}