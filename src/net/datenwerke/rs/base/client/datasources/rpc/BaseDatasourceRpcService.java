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
 
 
package net.datenwerke.rs.base.client.datasources.rpc;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.model.ListStringBaseModel;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.base.client.dbhelper.dto.DatabaseHelperDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceContainerDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

@RemoteServiceRelativePath("base_datasource")
public interface BaseDatasourceRpcService extends RemoteService {

	public ArrayList<DatabaseHelperDto> getDBHelperList() throws ServerCallFailedException;
	
	public DatabaseHelperDto dummy(DatabaseHelperDto dto);

	List<String> loadColumnDefinition(DatasourceContainerDto container,
			String query) throws ServerCallFailedException;

	PagingLoadResult<ListStringBaseModel> loadData(
			DatasourceContainerDto container, PagingLoadConfig loadConfig,
			String query) throws ServerCallFailedException;
	
}
