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
 
 
package net.datenwerke.rs.globalconstants.server.globalconstants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.globalconstants.client.globalconstants.dto.GlobalConstantDto;
import net.datenwerke.rs.globalconstants.client.globalconstants.rpc.GlobalConstantsRpcService;
import net.datenwerke.rs.globalconstants.service.globalconstants.GlobalConstantsService;
import net.datenwerke.rs.globalconstants.service.globalconstants.entities.GlobalConstant;
import net.datenwerke.rs.globalconstants.service.globalconstants.genrights.GlobalConstantsAdminViewSecurityTarget;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.annotation.GenericTargetVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.security.rights.Write;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

/**
 * 
 *
 */
@Singleton
public class GlobalConstantsRpcServiceImpl extends SecuredRemoteServiceServlet
		implements GlobalConstantsRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7354627066364356935L;

	private final DtoService dtoService;
	private final GlobalConstantsService globalConstantsService;
	
	@Inject
	public GlobalConstantsRpcServiceImpl(
		DtoService dtoService,
		GlobalConstantsService globalConstantsService
		){
		
		this.dtoService = dtoService;
		this.globalConstantsService = globalConstantsService;
	}

	@Override
	@SecurityChecked(
			genericTargetVerification = {
				@GenericTargetVerification(
					target=GlobalConstantsAdminViewSecurityTarget.class,
					verify=@RightsVerification(rights=Read.class)
				)
			}
		)
	@Transactional(rollbackOn={Exception.class})
	public List<GlobalConstantDto> loadGlobalConstants() {
		List<GlobalConstantDto> constantDtos = new ArrayList<GlobalConstantDto>();
		
		for(GlobalConstant constant : globalConstantsService.getAllGlobalConstants())
			constantDtos.add((GlobalConstantDto) dtoService.createDto(constant));
		
		Collections.sort(constantDtos, new Comparator<GlobalConstantDto>(){

			@Override
			public int compare(GlobalConstantDto o1,
					GlobalConstantDto o2) {
				if(null == o1.getName())
					return 0;
				
				return o1.getName().compareTo(o2.getName());
			}
			
		});
		
		return constantDtos;
	}

	@Override
	@SecurityChecked(
			genericTargetVerification = {
				@GenericTargetVerification(
					target=GlobalConstantsAdminViewSecurityTarget.class,
					verify=@RightsVerification(rights=Write.class)
				)
			}
		)
	@Transactional(rollbackOn={Exception.class})
	public GlobalConstantDto addNewConstant() {
		GlobalConstant constant = new GlobalConstant();

		globalConstantsService.persist(constant);
		
		return (GlobalConstantDto) dtoService.createDto(constant);
	}

	@Override
	@SecurityChecked(
			genericTargetVerification = {
				@GenericTargetVerification(
					target=GlobalConstantsAdminViewSecurityTarget.class,
					verify=@RightsVerification(rights=Write.class)
				)
			}
		)
	@Transactional(rollbackOn={Exception.class})
	public void removeAllConstants() {
		globalConstantsService.removeAllConstants();
	}

	@Override
	@SecurityChecked(
			genericTargetVerification = {
				@GenericTargetVerification(
					target=GlobalConstantsAdminViewSecurityTarget.class,
					verify=@RightsVerification(rights=Write.class)
				)
			}
		)
	@Transactional(rollbackOn={Exception.class})
	public void removeConstants(Collection<GlobalConstantDto> constantDtos) {
		for(GlobalConstantDto constantDto : constantDtos ){
			GlobalConstant constant = (GlobalConstant) dtoService.loadPoso(constantDto);
			globalConstantsService.remove(constant);
		}
	}

	@Override
	@SecurityChecked(
		genericTargetVerification = {
			@GenericTargetVerification(
				target=GlobalConstantsAdminViewSecurityTarget.class,
				verify=@RightsVerification(rights=Write.class)
			)
		}
	)
	@Transactional(rollbackOn={Exception.class})
	public GlobalConstantDto updateConstant(
			GlobalConstantDto constantDto) throws ExpectedException {
		GlobalConstant constant = (GlobalConstant) dtoService.loadPoso(constantDto);
		
		dtoService.mergePoso(constantDto, constant);
		
		globalConstantsService.merge(constant);
		
		return (GlobalConstantDto) dtoService.createDto(constant);
	}
	
}
