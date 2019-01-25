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

import net.datenwerke.dtoservices.dtogenerator.poso2dtogenerator.interfaces.Poso2DtoPostProcessor;
import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.search.client.search.dto.SearchResultEntryDto;
import net.datenwerke.rs.search.client.search.dto.decorator.SearchResultEntryDtoDec;
import net.datenwerke.rs.search.service.search.results.SearchResultEntry;

import com.google.inject.Inject;

public class SearchEntry2DtoPost implements Poso2DtoPostProcessor<SearchResultEntry, SearchResultEntryDto> {

	private final DtoService dtoService;
	
	@Inject
	public SearchEntry2DtoPost(DtoService dtoService) {
		this.dtoService = dtoService;
	}

	@Override
	public void dtoCreated(SearchResultEntry p, SearchResultEntryDto d) {
		if(null != p.getResultObject()){
			try{
				Object r = dtoService.createDto(p.getResultObject());
				if(r instanceof DwModel)
					((SearchResultEntryDtoDec)d).setResultObject((DwModel) r);
			}catch(Exception e){}
		}
	}

	@Override
	public void dtoInstantiated(SearchResultEntry arg0,
			SearchResultEntryDto arg1) {
		
	}

}
