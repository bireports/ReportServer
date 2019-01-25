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
 
 
package net.datenwerke.rs.search.service.search.results.post;

import net.datenwerke.dtoservices.dtogenerator.dto2posogenerator.interfaces.Dto2PosoPostProcessor;
import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.search.client.search.dto.SearchFilterDto;
import net.datenwerke.rs.search.client.search.dto.decorator.SearchFilterDtoDec;
import net.datenwerke.rs.search.service.search.results.SearchFilter;

import com.google.inject.Inject;

public class Dto2SearchFilterPost implements Dto2PosoPostProcessor<SearchFilterDto, SearchFilter> {

	private final DtoService dtoService;
	
	@Inject
	public Dto2SearchFilterPost(DtoService dtoService) {
		this.dtoService = dtoService;
	}

	@Override
	public void posoCreated(SearchFilterDto d, SearchFilter poso) {
		SearchFilterDtoDec dto = (SearchFilterDtoDec) d;
		Dto base = dto.getBaseType();
		if(null != base){
			Object fin = dtoService.createDto(base);
			poso.setBaseType(fin.getClass());
		}
	}

	@Override
	public void posoCreatedUnmanaged(SearchFilterDto arg0, SearchFilter arg1) {
	}

	@Override
	public void posoInstantiated(SearchFilter arg0) {
	}

	@Override
	public void posoLoaded(SearchFilterDto arg0, SearchFilter arg1) {
	}

	@Override
	public void posoMerged(SearchFilterDto arg0, SearchFilter arg1) {
	}

}
