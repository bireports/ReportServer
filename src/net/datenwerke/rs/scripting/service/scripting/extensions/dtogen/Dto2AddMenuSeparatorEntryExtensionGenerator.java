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
 
 
package net.datenwerke.rs.scripting.service.scripting.extensions.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import java.lang.Exception;
import java.lang.RuntimeException;
import java.lang.reflect.Field;
import java.util.Collection;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoGenerator;
import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.validator.DtoPropertyValidator;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.scripting.client.scripting.dto.AddMenuSeparatorEntryExtensionDto;
import net.datenwerke.rs.scripting.service.scripting.extensions.AddMenuSeparatorEntryExtension;
import net.datenwerke.rs.scripting.service.scripting.extensions.dtogen.Dto2AddMenuSeparatorEntryExtensionGenerator;
import net.datenwerke.rs.utils.entitycloner.annotation.TransientID;
import net.datenwerke.rs.utils.reflection.ReflectionService;

/**
 * Dto2PosoGenerator for AddMenuSeparatorEntryExtension
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2AddMenuSeparatorEntryExtensionGenerator implements Dto2PosoGenerator<AddMenuSeparatorEntryExtensionDto,AddMenuSeparatorEntryExtension> {

	private final Provider<DtoService> dtoServiceProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	private final ReflectionService reflectionService;
	@Inject
	public Dto2AddMenuSeparatorEntryExtensionGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider,
		ReflectionService reflectionService
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
		this.reflectionService = reflectionService;
	}

	public AddMenuSeparatorEntryExtension loadPoso(AddMenuSeparatorEntryExtensionDto dto)  {
		/* Poso is not an entity .. so what should I load? */
		return null;
	}

	public AddMenuSeparatorEntryExtension instantiatePoso()  {
		AddMenuSeparatorEntryExtension poso = new AddMenuSeparatorEntryExtension();
		return poso;
	}

	public AddMenuSeparatorEntryExtension createPoso(AddMenuSeparatorEntryExtensionDto dto)  throws ExpectedException {
		AddMenuSeparatorEntryExtension poso = new AddMenuSeparatorEntryExtension();

		/* merge data */
		mergePoso(dto, poso);
		return poso;
	}

	public AddMenuSeparatorEntryExtension createUnmanagedPoso(AddMenuSeparatorEntryExtensionDto dto)  throws ExpectedException {
		AddMenuSeparatorEntryExtension poso = new AddMenuSeparatorEntryExtension();

		mergePlainDto2UnmanagedPoso(dto,poso);

		return poso;
	}

	public void mergePoso(AddMenuSeparatorEntryExtensionDto dto, final AddMenuSeparatorEntryExtension poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2Poso(dto, poso);
		else
			mergePlainDto2Poso(dto, poso);
	}

	protected void mergePlainDto2Poso(AddMenuSeparatorEntryExtensionDto dto, final AddMenuSeparatorEntryExtension poso)  throws ExpectedException {
		/*  set menuName */
		poso.setMenuName(dto.getMenuName() );

	}

	protected void mergeProxy2Poso(AddMenuSeparatorEntryExtensionDto dto, final AddMenuSeparatorEntryExtension poso)  throws ExpectedException {
		/*  set menuName */
		if(dto.isMenuNameModified()){
			poso.setMenuName(dto.getMenuName() );
		}

	}

	public void mergeUnmanagedPoso(AddMenuSeparatorEntryExtensionDto dto, final AddMenuSeparatorEntryExtension poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2UnmanagedPoso(dto, poso);
		else
			mergePlainDto2UnmanagedPoso(dto, poso);
	}

	protected void mergePlainDto2UnmanagedPoso(AddMenuSeparatorEntryExtensionDto dto, final AddMenuSeparatorEntryExtension poso)  throws ExpectedException {
		/*  set menuName */
		poso.setMenuName(dto.getMenuName() );

	}

	protected void mergeProxy2UnmanagedPoso(AddMenuSeparatorEntryExtensionDto dto, final AddMenuSeparatorEntryExtension poso)  throws ExpectedException {
		/*  set menuName */
		if(dto.isMenuNameModified()){
			poso.setMenuName(dto.getMenuName() );
		}

	}

	public AddMenuSeparatorEntryExtension loadAndMergePoso(AddMenuSeparatorEntryExtensionDto dto)  throws ExpectedException {
		AddMenuSeparatorEntryExtension poso = loadPoso(dto);
		if(null != poso){
			mergePoso(dto, poso);
			return poso;
		}
		return createPoso(dto);
	}

	public void postProcessCreate(AddMenuSeparatorEntryExtensionDto dto, AddMenuSeparatorEntryExtension poso)  {
	}


	public void postProcessCreateUnmanaged(AddMenuSeparatorEntryExtensionDto dto, AddMenuSeparatorEntryExtension poso)  {
	}


	public void postProcessLoad(AddMenuSeparatorEntryExtensionDto dto, AddMenuSeparatorEntryExtension poso)  {
	}


	public void postProcessMerge(AddMenuSeparatorEntryExtensionDto dto, AddMenuSeparatorEntryExtension poso)  {
	}


	public void postProcessInstantiate(AddMenuSeparatorEntryExtension poso)  {
	}



}
