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
 
 
package net.datenwerke.rs.base.client.datasources;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.model.ListStringBaseModel;
import net.datenwerke.rs.base.client.datasources.rpc.BaseDatasourceRpcServiceAsync;
import net.datenwerke.rs.base.client.dbhelper.dto.DatabaseHelperDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public class BaseDatasourceDao extends Dao {

	private final BaseDatasourceRpcServiceAsync rpcService;
	
	@Inject
	public BaseDatasourceDao(BaseDatasourceRpcServiceAsync rpcService){
		this.rpcService = rpcService;
	}
	
	public void getDBHelperList(AsyncCallback<ArrayList<DatabaseHelperDto>> callback){
		rpcService.getDBHelperList(transformListCallback(callback));
	}
	
	public void loadColumnDefinition(DatasourceContainerDto container, String query,
			AsyncCallback<List<String>> callback) {
		rpcService.loadColumnDefinition(container, query, transformAndKeepCallback(callback));
	}

	public void loadData(DatasourceContainerDto container,
			PagingLoadConfig loadConfig, String query,
			AsyncCallback<PagingLoadResult<ListStringBaseModel>> callback) {
		rpcService.loadData(container, loadConfig, query, transformAndKeepCallback(callback));
	}

}
