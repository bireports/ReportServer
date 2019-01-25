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
 
 
package net.datenwerke.rs.uservariables.service.variabletypes.string.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.uservariables.client.uservariables.dto.UserVariableDefinitionDto;
import net.datenwerke.rs.uservariables.client.variabletypes.string.dto.StringUserVariableInstanceDto;
import net.datenwerke.rs.uservariables.service.variabletypes.string.StringUserVariableInstance;
import net.datenwerke.rs.uservariables.service.variabletypes.string.dtogen.StringUserVariableInstance2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import net.datenwerke.security.client.usermanager.dto.AbstractUserManagerNodeDto;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for StringUserVariableInstance
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class StringUserVariableInstance2DtoGenerator implements Poso2DtoGenerator<StringUserVariableInstance,StringUserVariableInstanceDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public StringUserVariableInstance2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public StringUserVariableInstanceDto instantiateDto(StringUserVariableInstance poso)  {
		StringUserVariableInstanceDto dto = new StringUserVariableInstanceDto();
		return dto;
	}

	public StringUserVariableInstanceDto createDto(StringUserVariableInstance poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final StringUserVariableInstanceDto dto = new StringUserVariableInstanceDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set id */
			dto.setId(poso.getId() );

		}
		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set definition */
			Object tmpDtoUserVariableDefinitionDtogetDefinition = dtoServiceProvider.get().createDto(poso.getDefinition(), referenced, referenced);
			dto.setDefinition((UserVariableDefinitionDto)tmpDtoUserVariableDefinitionDtogetDefinition);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoUserVariableDefinitionDtogetDefinition, poso.getDefinition(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setDefinition((UserVariableDefinitionDto)refDto);
				}
			});

			/*  set folk */
			Object tmpDtoAbstractUserManagerNodeDtogetFolk = dtoServiceProvider.get().createDto(poso.getFolk(), referenced, referenced);
			dto.setFolk((AbstractUserManagerNodeDto)tmpDtoAbstractUserManagerNodeDtogetFolk);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoAbstractUserManagerNodeDtogetFolk, poso.getFolk(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setFolk((AbstractUserManagerNodeDto)refDto);
				}
			});

			/*  set value */
			dto.setValue(StringEscapeUtils.escapeXml(StringUtils.left(poso.getValue(),8192)));

		}

		return dto;
	}


}
