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
 
 
package net.datenwerke.rs.search.client.search;

import java.util.ArrayList;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.rs.search.client.search.dto.SearchFilterDto;
import net.datenwerke.rs.search.client.search.dto.SearchResultEntryDto;
import net.datenwerke.rs.search.client.search.dto.SearchResultListDto;
import net.datenwerke.rs.search.client.search.dto.decorator.SearchResultListDtoDec;
import net.datenwerke.rs.search.client.search.rpc.SearchRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class SearchDao extends Dao {

	private final SearchRpcServiceAsync rpcService;

	private static SearchResultListDto emptyResult;
	static {
		emptyResult = new SearchResultListDtoDec(new ArrayList<SearchResultEntryDto>());
	}
	
	
	@Inject
	public SearchDao(SearchRpcServiceAsync rpcService) {
		this.rpcService = rpcService;
	}
	
	public void find(Dto2PosoMapper type, String searchStr, AsyncCallback<SearchResultListDto> callback){
		if(null == searchStr || "".equals(searchStr.trim()))
			callback.onSuccess(emptyResult);
		else
			rpcService.find(type, searchStr, transformAndKeepCallback(callback));
	}

	public void find(String searchStr, AsyncCallback<SearchResultListDto> callback) {
		if(null == searchStr || "".equals(searchStr.trim()))
			callback.onSuccess(emptyResult);
		else
			rpcService.find(searchStr, transformAndKeepCallback(callback));
	}

	public void find(String searchStr, SearchFilterDto filter,
			AsyncCallback<SearchResultListDto> callback) {
		if(null == searchStr || "".equals(searchStr.trim()))
			callback.onSuccess(emptyResult);
		else 
			rpcService.find(searchStr, filter, transformAndKeepCallback(callback));
	}
	
}
