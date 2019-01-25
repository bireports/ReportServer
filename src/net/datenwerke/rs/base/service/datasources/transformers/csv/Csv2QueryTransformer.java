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
 
 
package net.datenwerke.rs.base.service.datasources.transformers.csv;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.datenwerke.dbpool.DbPoolService;
import net.datenwerke.rs.base.service.datasources.definitions.CsvDatasource;
import net.datenwerke.rs.base.service.datasources.definitions.CsvDatasourceConfig;
import net.datenwerke.rs.base.service.datasources.table.impl.TableDataSource;
import net.datenwerke.rs.base.service.dbhelper.DBHelperService;
import net.datenwerke.rs.base.service.reportengines.table.utils.TableModelDbHelper;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainerProvider;
import net.datenwerke.rs.core.service.internaldb.TempTableResult;
import net.datenwerke.rs.core.service.internaldb.TempTableService;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.rs.resultcache.ResultCacheService;
import net.datenwerke.rs.saikupivot.service.SaikuDatasourceQueryTransformerMarker;


/**
 * Transforms DataSourceDefitions into QRDataSources
 *
 */
@Singleton
public class Csv2QueryTransformer extends Csv2XTransformer<SaikuDatasourceQueryTransformerMarker> {

	@Inject
	public Csv2QueryTransformer(
			DbPoolService dbPoolService,
			DBHelperService dbHelperService, 
			TempTableService tempTableService, 
			TableModelDbHelper tableModelDbHelper, 
			ResultCacheService resultCacheService
			){
		super(dbPoolService, dbHelperService, tempTableService, tableModelDbHelper, resultCacheService);
	}

	@Override
	public boolean consumes(DatasourceContainerProvider containerProvider, Class<?> dst) {
		DatasourceContainer container = containerProvider.getDatasourceContainer();
		return (null != container && container.getDatasource() instanceof CsvDatasource && dst.isAssignableFrom(SaikuDatasourceQueryTransformerMarker.class));
	}

	@Override
	protected SaikuDatasourceQueryTransformerMarker transformResult(Object r, DatasourceContainerProvider datasourceContainerProvider, ParameterSet parameterSet, Class<TableDataSource> targetType) {
		TempTableResult tempTableResult = (TempTableResult) r;

		String query = tempTableResult.getFinalQuery();
		
		String queryWrapper = ((CsvDatasourceConfig)datasourceContainerProvider.getDatasourceContainer().getDatasourceConfig()).getQueryWrapper();
		if(null == queryWrapper || "".equals(queryWrapper.trim()))
			queryWrapper = query;
		
		parameterSet.addVariable("query", query);
		for(String alias : tempTableResult.getTableHelper().getTableAliases())
			parameterSet.addVariable(alias, tempTableResult.getTableHelper().getTableName(alias));
		
		return new SaikuDatasourceQueryTransformerMarker(queryWrapper);
	}
}
