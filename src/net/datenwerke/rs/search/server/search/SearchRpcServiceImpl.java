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
 
 
package net.datenwerke.rs.search.server.search;

import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.search.client.search.dto.SearchFilterDto;
import net.datenwerke.rs.search.client.search.dto.SearchResultListDto;
import net.datenwerke.rs.search.client.search.rpc.SearchRpcService;
import net.datenwerke.rs.search.service.search.SearchService;
import net.datenwerke.rs.search.service.search.results.SearchFilter;
import net.datenwerke.rs.search.service.search.results.SearchResultList;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SearchRpcServiceImpl extends SecuredRemoteServiceServlet implements SearchRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1882454731453143096L;

	private final DtoService dtoService;
	private final SearchService searchService;	

	
	@Inject
	public SearchRpcServiceImpl(
		DtoService dtoService,
		SearchService searchService	
		){
		
		/* store objects */
		this.dtoService = dtoService;
		this.searchService = searchService;
	}


	@Override
	public SearchResultListDto find(Dto2PosoMapper typeDto, String searchStr) throws ServerCallFailedException {
		Class<?> baseType = dtoService.getPosoFromDtoMapper(typeDto);
		SearchFilter filter = new SearchFilter();
		filter.setBaseType(baseType);
		return find(searchStr, filter);
	}


	@Override
	public SearchResultListDto find(String searchStr)throws ServerCallFailedException {
		SearchFilter filter = new SearchFilter();
		return find(searchStr, filter);
	}


	@Override
	public SearchResultListDto find(String searchStr, SearchFilterDto loadConfig) throws ExpectedException {
		SearchFilter filter = (SearchFilter) dtoService.createPoso(loadConfig);
		return find(searchStr, filter);
	}


	protected SearchResultListDto find(String searchStr, SearchFilter filter) {
		SearchResultList results = searchService.findAsResultList(searchStr, filter);
		return (SearchResultListDto) dtoService.createDto(results);
	}
}
