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
 
 
package net.datenwerke.rs.fileserver.service.fileserver.terminal.commands.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.fileserver.client.fileserver.dto.EditCommandResultExtensionDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFileDto;
import net.datenwerke.rs.fileserver.service.fileserver.terminal.commands.EditCommandResultExtension;
import net.datenwerke.rs.fileserver.service.fileserver.terminal.commands.dtogen.EditCommandResultExtension2DtoGenerator;

/**
 * Poso2DtoGenerator for EditCommandResultExtension
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class EditCommandResultExtension2DtoGenerator implements Poso2DtoGenerator<EditCommandResultExtension,EditCommandResultExtensionDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public EditCommandResultExtension2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public EditCommandResultExtensionDto instantiateDto(EditCommandResultExtension poso)  {
		EditCommandResultExtensionDto dto = new EditCommandResultExtensionDto();
		return dto;
	}

	public EditCommandResultExtensionDto createDto(EditCommandResultExtension poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final EditCommandResultExtensionDto dto = new EditCommandResultExtensionDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set data */
			dto.setData(poso.getData() );

			/*  set file */
			Object tmpDtoFileServerFileDtogetFile = dtoServiceProvider.get().createDto(poso.getFile(), here, referenced);
			dto.setFile((FileServerFileDto)tmpDtoFileServerFileDtogetFile);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoFileServerFileDtogetFile, poso.getFile(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setFile((FileServerFileDto)refDto);
				}
			});

		}

		return dto;
	}


}
