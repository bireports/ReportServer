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
import net.datenwerke.rs.scripting.client.scripting.dto.DisplayConditionDto;
import net.datenwerke.rs.scripting.client.scripting.dto.DisplayConditionTypeDto;
import net.datenwerke.rs.scripting.service.scripting.extensions.DisplayCondition;
import net.datenwerke.rs.scripting.service.scripting.extensions.DisplayConditionType;
import net.datenwerke.rs.scripting.service.scripting.extensions.dtogen.Dto2DisplayConditionGenerator;
import net.datenwerke.rs.utils.entitycloner.annotation.TransientID;
import net.datenwerke.rs.utils.reflection.ReflectionService;

/**
 * Dto2PosoGenerator for DisplayCondition
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2DisplayConditionGenerator implements Dto2PosoGenerator<DisplayConditionDto,DisplayCondition> {

	private final Provider<DtoService> dtoServiceProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	private final ReflectionService reflectionService;
	@Inject
	public Dto2DisplayConditionGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider,
		ReflectionService reflectionService
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
		this.reflectionService = reflectionService;
	}

	public DisplayCondition loadPoso(DisplayConditionDto dto)  {
		/* Poso is not an entity .. so what should I load? */
		return null;
	}

	public DisplayCondition instantiatePoso()  {
		DisplayCondition poso = new DisplayCondition();
		return poso;
	}

	public DisplayCondition createPoso(DisplayConditionDto dto)  throws ExpectedException {
		DisplayCondition poso = new DisplayCondition();

		/* merge data */
		mergePoso(dto, poso);
		return poso;
	}

	public DisplayCondition createUnmanagedPoso(DisplayConditionDto dto)  throws ExpectedException {
		DisplayCondition poso = new DisplayCondition();

		mergePlainDto2UnmanagedPoso(dto,poso);

		return poso;
	}

	public void mergePoso(DisplayConditionDto dto, final DisplayCondition poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2Poso(dto, poso);
		else
			mergePlainDto2Poso(dto, poso);
	}

	protected void mergePlainDto2Poso(DisplayConditionDto dto, final DisplayCondition poso)  throws ExpectedException {
		/*  set propertyName */
		poso.setPropertyName(dto.getPropertyName() );

		/*  set type */
		DisplayConditionTypeDto tmpDto_type = dto.getType();
		poso.setType((DisplayConditionType)dtoServiceProvider.get().createPoso(tmpDto_type));

		/*  set value */
		poso.setValue(dto.getValue() );

	}

	protected void mergeProxy2Poso(DisplayConditionDto dto, final DisplayCondition poso)  throws ExpectedException {
		/*  set propertyName */
		if(dto.isPropertyNameModified()){
			poso.setPropertyName(dto.getPropertyName() );
		}

		/*  set type */
		if(dto.isTypeModified()){
			DisplayConditionTypeDto tmpDto_type = dto.getType();
			poso.setType((DisplayConditionType)dtoServiceProvider.get().createPoso(tmpDto_type));
		}

		/*  set value */
		if(dto.isValueModified()){
			poso.setValue(dto.getValue() );
		}

	}

	public void mergeUnmanagedPoso(DisplayConditionDto dto, final DisplayCondition poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2UnmanagedPoso(dto, poso);
		else
			mergePlainDto2UnmanagedPoso(dto, poso);
	}

	protected void mergePlainDto2UnmanagedPoso(DisplayConditionDto dto, final DisplayCondition poso)  throws ExpectedException {
		/*  set propertyName */
		poso.setPropertyName(dto.getPropertyName() );

		/*  set type */
		DisplayConditionTypeDto tmpDto_type = dto.getType();
		poso.setType((DisplayConditionType)dtoServiceProvider.get().createPoso(tmpDto_type));

		/*  set value */
		poso.setValue(dto.getValue() );

	}

	protected void mergeProxy2UnmanagedPoso(DisplayConditionDto dto, final DisplayCondition poso)  throws ExpectedException {
		/*  set propertyName */
		if(dto.isPropertyNameModified()){
			poso.setPropertyName(dto.getPropertyName() );
		}

		/*  set type */
		if(dto.isTypeModified()){
			DisplayConditionTypeDto tmpDto_type = dto.getType();
			poso.setType((DisplayConditionType)dtoServiceProvider.get().createPoso(tmpDto_type));
		}

		/*  set value */
		if(dto.isValueModified()){
			poso.setValue(dto.getValue() );
		}

	}

	public DisplayCondition loadAndMergePoso(DisplayConditionDto dto)  throws ExpectedException {
		DisplayCondition poso = loadPoso(dto);
		if(null != poso){
			mergePoso(dto, poso);
			return poso;
		}
		return createPoso(dto);
	}

	public void postProcessCreate(DisplayConditionDto dto, DisplayCondition poso)  {
	}


	public void postProcessCreateUnmanaged(DisplayConditionDto dto, DisplayCondition poso)  {
	}


	public void postProcessLoad(DisplayConditionDto dto, DisplayCondition poso)  {
	}


	public void postProcessMerge(DisplayConditionDto dto, DisplayCondition poso)  {
	}


	public void postProcessInstantiate(DisplayCondition poso)  {
	}



}
