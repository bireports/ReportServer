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
 
 
package net.datenwerke.rs.dashboard.service.dashboard.dagets.dtogen;

import com.google.inject.Inject;
import com.google.inject.Provider;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoGenerator;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.gxtdto.server.dtomanager.DtoMainService;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.dashboard.client.dashboard.dto.FavoriteListEntryDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.decorator.FavoriteListEntryDtoDec;
import net.datenwerke.rs.dashboard.service.dashboard.dagets.FavoriteListEntry;
import net.datenwerke.rs.dashboard.service.dashboard.dagets.dtogen.FavoriteListEntry2DtoGenerator;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;

/**
 * Poso2DtoGenerator for FavoriteListEntry
 *
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.dtoservices.dtogenerator.DtoAnnotationProcessor")
public class FavoriteListEntry2DtoGenerator implements Poso2DtoGenerator<FavoriteListEntry,FavoriteListEntryDtoDec> {

	private final Provider<DtoService> dtoServiceProvider;

	@Inject
	public FavoriteListEntry2DtoGenerator(
		Provider<DtoService> dtoServiceProvider	){
		this.dtoServiceProvider = dtoServiceProvider;
	}

	public FavoriteListEntryDtoDec instantiateDto(FavoriteListEntry poso)  {
		FavoriteListEntryDtoDec dto = new FavoriteListEntryDtoDec();
		return dto;
	}

	public FavoriteListEntryDtoDec createDto(FavoriteListEntry poso, DtoView here, DtoView referenced)  {
		/* create dto and set view */
		final FavoriteListEntryDtoDec dto = new FavoriteListEntryDtoDec();
		dto.setDtoView(here);

		if(here.compareTo(DtoView.MINIMAL) >= 0){
			/*  set id */
			dto.setId(poso.getId() );

		}
		if(here.compareTo(DtoView.NORMAL) >= 0){
			/*  set referenceEntry */
			Object tmpDtoAbstractTsDiskNodeDtogetReferenceEntry = dtoServiceProvider.get().createDto(poso.getReferenceEntry(), here, referenced);
			dto.setReferenceEntry((AbstractTsDiskNodeDto)tmpDtoAbstractTsDiskNodeDtogetReferenceEntry);
			/* ask for a dto with higher view if generated */
			((DtoMainService)dtoServiceProvider.get()).getCreationHelper().onDtoCreation(tmpDtoAbstractTsDiskNodeDtogetReferenceEntry, poso.getReferenceEntry(), new net.datenwerke.gxtdto.server.dtomanager.CallbackOnDtoCreation(){
				public void callback(Object refDto){
					if(null != refDto)
						dto.setReferenceEntry((AbstractTsDiskNodeDto)refDto);
				}
			});

		}

		return dto;
	}


}
