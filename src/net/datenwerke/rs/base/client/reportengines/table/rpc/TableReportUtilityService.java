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
 
 
package net.datenwerke.rs.base.client.reportengines.table.rpc;

import java.util.List;

import net.datenwerke.gxtdto.client.model.ListStringBaseModel;
import net.datenwerke.gxtdto.client.model.StringBaseModel;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.NonFatalException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportInformation;
import net.datenwerke.rs.base.client.reportengines.table.helpers.filter.FilterType;
import net.datenwerke.rs.base.client.reportengines.table.helpers.filter.SelectorPanelLoadConfig;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

@RemoteServiceRelativePath("tablereportutility")
public interface TableReportUtilityService extends RemoteService{

	public ListLoadResult<ColumnDto> getReturnedColumns(TableReportDto tableReport, String executeToken) throws ServerCallFailedException, NonFatalException;

	public TableReportInformation getReportInformation(TableReportDto tableReport,  String executeToken) throws ServerCallFailedException, NonFatalException;
	
	public PagingLoadResult<StringBaseModel> getDistinctValuesPaged(
			SelectorPanelLoadConfig pagingLoadConfig, TableReportDto report,
			ColumnDto column, FilterType type, boolean useFilters, boolean countResults, String executeToken) throws ServerCallFailedException;

	List<ColumnDto> loadColumnDefinition(TableReportDto report, String query, String executeToken) throws ServerCallFailedException, NonFatalException;

	PagingLoadResult<ListStringBaseModel> loadData(TableReportDto report,
			PagingLoadConfig loadConfig, String query) throws ServerCallFailedException, NonFatalException;
}
