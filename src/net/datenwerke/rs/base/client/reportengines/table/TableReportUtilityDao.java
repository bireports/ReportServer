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
 
 
package net.datenwerke.rs.base.client.reportengines.table;

import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.callback.DaoAsyncCallback;
import net.datenwerke.gxtdto.client.model.ListStringBaseModel;
import net.datenwerke.gxtdto.client.model.StringBaseModel;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportInformation;
import net.datenwerke.rs.base.client.reportengines.table.helpers.filter.FilterType;
import net.datenwerke.rs.base.client.reportengines.table.helpers.filter.SelectorPanelLoadConfig;
import net.datenwerke.rs.base.client.reportengines.table.rpc.TableReportUtilityServiceAsync;

import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * 
 *
 */
public class TableReportUtilityDao extends Dao {

	private final TableReportUtilityServiceAsync rpcService;

	@Inject
	public TableReportUtilityDao(TableReportUtilityServiceAsync rpcService){
		super();
		this.rpcService = rpcService;
	}
	
	public Request getDistinctValuesPaged(SelectorPanelLoadConfig pagingLoadConfig,
			TableReportDto report, ColumnDto column, FilterType type,
			boolean useFilters, boolean countResults, String executeToken, AsyncCallback<PagingLoadResult<StringBaseModel>> callback){
		report = unproxy(report);
		column = unproxy(column);
		return rpcService.getDistinctValuesPaged(pagingLoadConfig, report, column, type, useFilters, countResults, executeToken, transformAndKeepCallback(callback));
	}

	public Request getReturnedColumns(TableReportDto tableReport, String executeToken, 
			AsyncCallback<ListLoadResult<ColumnDto>> callback){
		tableReport = unproxy(tableReport);
		return rpcService.getReturnedColumns(tableReport, executeToken, transformAndKeepCallback(callback));
	}

	public Request getReportInformation(TableReportDto tableReport, String executeToken,
			AsyncCallback<TableReportInformation> callback){
		tableReport = unproxy(tableReport);
		DaoAsyncCallback<TableReportInformation> daoCallback = transformAndKeepCallback(callback);
		daoCallback.ignoreExpectedExceptions(true);
		return rpcService.getReportInformation(tableReport, executeToken, daoCallback);
	}

	public void loadColumnDefinition(TableReportDto report, String query, String executeToken, 
			AsyncCallback<List<ColumnDto>> callback) {
		report = unproxy(report);
		rpcService.loadColumnDefinition(report, query, executeToken, transformAndKeepCallback(callback));
	}

	public void loadData(TableReportDto report,
			PagingLoadConfig loadConfig, String query,
			AsyncCallback<PagingLoadResult<ListStringBaseModel>> callback) {
		report = unproxy(report);
		rpcService.loadData(report, loadConfig, query, transformAndKeepCallback(callback));
	}
	
}
