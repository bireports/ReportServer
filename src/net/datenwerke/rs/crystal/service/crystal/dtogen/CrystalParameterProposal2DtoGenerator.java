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
 
 
package net.datenwerke.rs.crystal.service.crystal.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.crystal.client.crystal.dto.CrystalParameterProposalDto;
import net.datenwerke.rs.crystal.service.crystal.CrystalParameterProposal;
import net.datenwerke.rs.crystal.service.crystal.dtogen.CrystalParameterProposal2DtoGenerator;
import net.datenwerke.rs.utils.misc.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Poso2DtoGenerator for CrystalParameterProposal
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class CrystalParameterProposal2DtoGenerator implements Poso2DtoGenerator<CrystalParameterProposal,CrystalParameterProposalDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public CrystalParameterProposal2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public CrystalParameterProposalDto instantiateDto(CrystalParameterProposal poso)  {
		CrystalParameterProposalDto dto = new CrystalParameterProposalDto();
		return dto;
	}

	public CrystalParameterProposalDto createDto(CrystalParameterProposal poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final CrystalParameterProposalDto dto = new CrystalParameterProposalDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set key */
			dto.setKey(StringEscapeUtils.escapeXml(StringUtils.left(poso.getKey(),8192)));

		}
		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set name */
			dto.setName(StringEscapeUtils.escapeXml(StringUtils.left(poso.getName(),8192)));

			/*  set parameterProposal */
			Object tmpDtoParameterDefinitionDtogetParameterProposal = dtoServiceProvider.get().createDto(poso.getParameterProposal(), here, referenced);
			dto.setParameterProposal((ParameterDefinitionDto)tmpDtoParameterDefinitionDtogetParameterProposal);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoParameterDefinitionDtogetParameterProposal, poso.getParameterProposal(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setParameterProposal((ParameterDefinitionDto)refDto);
				}
			});

			/*  set type */
			dto.setType(StringEscapeUtils.escapeXml(StringUtils.left(poso.getType(),8192)));

		}

		return dto;
	}


}
