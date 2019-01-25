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
 
 
package net.datenwerke.rs.base.service.parameters.datasource.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.IllegalArgumentException;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.client.parameters.datasource.dto.SingleSelectionModeDto;
import net.datenwerke.rs.base.service.parameters.datasource.SingleSelectionMode;
import net.datenwerke.rs.base.service.parameters.datasource.dtogen.SingleSelectionMode2DtoGenerator;

/**
 * Poso2DtoGenerator for SingleSelectionMode
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class SingleSelectionMode2DtoGenerator implements Poso2DtoGenerator<SingleSelectionMode,SingleSelectionModeDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public SingleSelectionMode2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public SingleSelectionModeDto instantiateDto(SingleSelectionMode poso)  {
		/* Simply return the first enum! */
		SingleSelectionModeDto dto = SingleSelectionModeDto.Dropdown;
		return dto;
	}

	public SingleSelectionModeDto createDto(SingleSelectionMode poso, DtoView here, DtoView referenced)  {
		switch(poso){
			case Dropdown:
				return SingleSelectionModeDto.Dropdown;
			case Popup:
				return SingleSelectionModeDto.Popup;
			case Radio:
				return SingleSelectionModeDto.Radio;
			case Listbox:
				return SingleSelectionModeDto.Listbox;
		}
		throw new IllegalArgumentException("unknown enum type for: " + poso);
	}


}
