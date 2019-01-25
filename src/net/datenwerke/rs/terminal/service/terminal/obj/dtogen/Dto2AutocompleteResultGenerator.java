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
 
 
package net.datenwerke.rs.terminal.service.terminal.obj.dtogen;

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
import net.datenwerke.rs.terminal.client.terminal.dto.AutocompleteResultDto;
import net.datenwerke.rs.terminal.service.terminal.obj.AutocompleteResult;
import net.datenwerke.rs.terminal.service.terminal.obj.dtogen.Dto2AutocompleteResultGenerator;
import net.datenwerke.rs.utils.entitycloner.annotation.TransientID;
import net.datenwerke.rs.utils.reflection.ReflectionService;

/**
 * Dto2PosoGenerator for AutocompleteResult
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2AutocompleteResultGenerator implements Dto2PosoGenerator<AutocompleteResultDto,AutocompleteResult> {

	private final Provider<DtoService> dtoServiceProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	private final ReflectionService reflectionService;
	@Inject
	public Dto2AutocompleteResultGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider,
		ReflectionService reflectionService
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
		this.reflectionService = reflectionService;
	}

	public AutocompleteResult loadPoso(AutocompleteResultDto dto)  {
		/* Poso is not an entity .. so what should I load? */
		return null;
	}

	public AutocompleteResult instantiatePoso()  {
		AutocompleteResult poso = new AutocompleteResult();
		return poso;
	}

	public AutocompleteResult createPoso(AutocompleteResultDto dto)  throws ExpectedException {
		AutocompleteResult poso = new AutocompleteResult();

		/* merge data */
		mergePoso(dto, poso);
		return poso;
	}

	public AutocompleteResult createUnmanagedPoso(AutocompleteResultDto dto)  throws ExpectedException {
		AutocompleteResult poso = new AutocompleteResult();

		mergePlainDto2UnmanagedPoso(dto,poso);

		return poso;
	}

	public void mergePoso(AutocompleteResultDto dto, final AutocompleteResult poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2Poso(dto, poso);
		else
			mergePlainDto2Poso(dto, poso);
	}

	protected void mergePlainDto2Poso(AutocompleteResultDto dto, final AutocompleteResult poso)  throws ExpectedException {
		/*  set completeStump */
		poso.setCompleteStump(dto.getCompleteStump() );

	}

	protected void mergeProxy2Poso(AutocompleteResultDto dto, final AutocompleteResult poso)  throws ExpectedException {
		/*  set completeStump */
		if(dto.isCompleteStumpModified()){
			poso.setCompleteStump(dto.getCompleteStump() );
		}

	}

	public void mergeUnmanagedPoso(AutocompleteResultDto dto, final AutocompleteResult poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2UnmanagedPoso(dto, poso);
		else
			mergePlainDto2UnmanagedPoso(dto, poso);
	}

	protected void mergePlainDto2UnmanagedPoso(AutocompleteResultDto dto, final AutocompleteResult poso)  throws ExpectedException {
		/*  set completeStump */
		poso.setCompleteStump(dto.getCompleteStump() );

	}

	protected void mergeProxy2UnmanagedPoso(AutocompleteResultDto dto, final AutocompleteResult poso)  throws ExpectedException {
		/*  set completeStump */
		if(dto.isCompleteStumpModified()){
			poso.setCompleteStump(dto.getCompleteStump() );
		}

	}

	public AutocompleteResult loadAndMergePoso(AutocompleteResultDto dto)  throws ExpectedException {
		AutocompleteResult poso = loadPoso(dto);
		if(null != poso){
			mergePoso(dto, poso);
			return poso;
		}
		return createPoso(dto);
	}

	public void postProcessCreate(AutocompleteResultDto dto, AutocompleteResult poso)  {
	}


	public void postProcessCreateUnmanaged(AutocompleteResultDto dto, AutocompleteResult poso)  {
	}


	public void postProcessLoad(AutocompleteResultDto dto, AutocompleteResult poso)  {
	}


	public void postProcessMerge(AutocompleteResultDto dto, AutocompleteResult poso)  {
	}


	public void postProcessInstantiate(AutocompleteResult poso)  {
	}



}
