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
 
 
package net.datenwerke.rs.base.service.parameters.datasource.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.IllegalArgumentException;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.client.parameters.datasource.dto.BoxLayoutModeDto;
import net.datenwerke.rs.base.service.parameters.datasource.BoxLayoutMode;
import net.datenwerke.rs.base.service.parameters.datasource.dtogen.BoxLayoutMode2DtoGenerator;

/**
 * Poso2DtoGenerator for BoxLayoutMode
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class BoxLayoutMode2DtoGenerator implements Poso2DtoGenerator<BoxLayoutMode,BoxLayoutModeDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public BoxLayoutMode2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public BoxLayoutModeDto instantiateDto(BoxLayoutMode poso)  {
		/* Simply return the first enum! */
		BoxLayoutModeDto dto = BoxLayoutModeDto.TopDownLeftRight;
		return dto;
	}

	public BoxLayoutModeDto createDto(BoxLayoutMode poso, DtoView here, DtoView referenced)  {
		switch(poso){
			case TopDownLeftRight:
				return BoxLayoutModeDto.TopDownLeftRight;
			case LeftRightTopDown:
				return BoxLayoutModeDto.LeftRightTopDown;
		}
		throw new IllegalArgumentException("unknown enum type for: " + poso);
	}


}
