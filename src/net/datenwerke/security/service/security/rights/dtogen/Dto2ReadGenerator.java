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
 
 
package net.datenwerke.security.service.security.rights.dtogen;

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
import net.datenwerke.rs.utils.entitycloner.annotation.TransientID;
import net.datenwerke.rs.utils.reflection.ReflectionService;
import net.datenwerke.security.client.security.dto.ReadDto;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.security.rights.dtogen.Dto2ReadGenerator;

/**
 * Dto2PosoGenerator for Read
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class Dto2ReadGenerator implements Dto2PosoGenerator<ReadDto,Read> {

	private final Provider<DtoService> dtoServiceProvider;

	private final net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor;

	private final ReflectionService reflectionService;
	@Inject
	public Dto2ReadGenerator(
		net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoSupervisorDefaultImpl dto2PosoSupervisor,
		Provider<DtoService> dtoServiceProvider,
		ReflectionService reflectionService
	){
		this.dto2PosoSupervisor = dto2PosoSupervisor;
		this.dtoServiceProvider = dtoServiceProvider;
		this.reflectionService = reflectionService;
	}

	public Read loadPoso(ReadDto dto)  {
		/* Poso is not an entity .. so what should I load? */
		return null;
	}

	public Read instantiatePoso()  {
		Read poso = new Read();
		return poso;
	}

	public Read createPoso(ReadDto dto)  throws ExpectedException {
		Read poso = new Read();

		/* merge data */
		mergePoso(dto, poso);
		return poso;
	}

	public Read createUnmanagedPoso(ReadDto dto)  throws ExpectedException {
		Read poso = new Read();

		mergePlainDto2UnmanagedPoso(dto,poso);

		return poso;
	}

	public void mergePoso(ReadDto dto, final Read poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2Poso(dto, poso);
		else
			mergePlainDto2Poso(dto, poso);
	}

	protected void mergePlainDto2Poso(ReadDto dto, final Read poso)  throws ExpectedException {
	}

	protected void mergeProxy2Poso(ReadDto dto, final Read poso)  throws ExpectedException {
	}

	public void mergeUnmanagedPoso(ReadDto dto, final Read poso)  throws ExpectedException {
		if(dto.isDtoProxy())
			mergeProxy2UnmanagedPoso(dto, poso);
		else
			mergePlainDto2UnmanagedPoso(dto, poso);
	}

	protected void mergePlainDto2UnmanagedPoso(ReadDto dto, final Read poso)  throws ExpectedException {
	}

	protected void mergeProxy2UnmanagedPoso(ReadDto dto, final Read poso)  throws ExpectedException {
	}

	public Read loadAndMergePoso(ReadDto dto)  throws ExpectedException {
		Read poso = loadPoso(dto);
		if(null != poso){
			mergePoso(dto, poso);
			return poso;
		}
		return createPoso(dto);
	}

	public void postProcessCreate(ReadDto dto, Read poso)  {
	}


	public void postProcessCreateUnmanaged(ReadDto dto, Read poso)  {
	}


	public void postProcessLoad(ReadDto dto, Read poso)  {
	}


	public void postProcessMerge(ReadDto dto, Read poso)  {
	}


	public void postProcessInstantiate(Read poso)  {
	}



}
