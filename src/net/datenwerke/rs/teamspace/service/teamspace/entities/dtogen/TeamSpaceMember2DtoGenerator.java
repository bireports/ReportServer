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
 
 
package net.datenwerke.rs.teamspace.service.teamspace.entities.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceMemberDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceRoleDto;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpaceMember;
import net.datenwerke.rs.teamspace.service.teamspace.entities.dtogen.TeamSpaceMember2DtoGenerator;
import net.datenwerke.security.client.usermanager.dto.UserDto;

/**
 * Poso2DtoGenerator for TeamSpaceMember
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class TeamSpaceMember2DtoGenerator implements Poso2DtoGenerator<TeamSpaceMember,TeamSpaceMemberDto> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public TeamSpaceMember2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public TeamSpaceMemberDto instantiateDto(TeamSpaceMember poso)  {
		TeamSpaceMemberDto dto = new TeamSpaceMemberDto();
		return dto;
	}

	public TeamSpaceMemberDto createDto(TeamSpaceMember poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final TeamSpaceMemberDto dto = new TeamSpaceMemberDto();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set id */
			dto.setId(poso.getId() );

		}
		if(here.compareTo(DtoView.LIST) >= 0){
			/*  set user */
			Object tmpDtoUserDtogetUser = dtoServiceProvider.get().createDto(poso.getUser(), referenced, referenced);
			dto.setUser((UserDto)tmpDtoUserDtogetUser);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoUserDtogetUser, poso.getUser(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setUser((UserDto)refDto);
				}
			});

		}
		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set role */
			Object tmpDtoTeamSpaceRoleDtogetRole = dtoServiceProvider.get().createDto(poso.getRole(), referenced, referenced);
			dto.setRole((TeamSpaceRoleDto)tmpDtoTeamSpaceRoleDtogetRole);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoTeamSpaceRoleDtogetRole, poso.getRole(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setRole((TeamSpaceRoleDto)refDto);
				}
			});

		}

		return dto;
	}


}
