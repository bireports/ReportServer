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
 
 
package net.datenwerke.rs.base.service.datasources;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.dbpool.hooks.DbPoolConnectionHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.datasources.definitions.CsvDatasource;
import net.datenwerke.rs.base.service.datasources.eventhandler.HandleCsvDatasourceMergeEvents;
import net.datenwerke.rs.base.service.datasources.hooker.BaseDatasourceProviderHooker;
import net.datenwerke.rs.base.service.datasources.hooker.StandardConnectionHook;
import net.datenwerke.rs.base.service.datasources.statementmanager.db.MonetDbStatementCancler;
import net.datenwerke.rs.base.service.datasources.statementmanager.hooks.StatementCancellationHook;
import net.datenwerke.rs.base.service.datasources.table.hookers.QueryCommentAdderHooker;
import net.datenwerke.rs.base.service.datasources.table.impl.hooks.TableDbDatasourceOpenedHook;
import net.datenwerke.rs.base.service.datasources.transformers.DataSourceDefinitionTransformer;
import net.datenwerke.rs.base.service.datasources.transformers.birtreport.Birt2ConnectionTransformer;
import net.datenwerke.rs.base.service.datasources.transformers.birtreport.Birt2JdbcDatasourceTransformer;
import net.datenwerke.rs.base.service.datasources.transformers.birtreport.Birt2TableTransformer;
import net.datenwerke.rs.base.service.datasources.transformers.csv.Csv2ConnectionTransformer;
import net.datenwerke.rs.base.service.datasources.transformers.csv.Csv2InputStreamTransformer;
import net.datenwerke.rs.base.service.datasources.transformers.csv.Csv2JasperTransformer;
import net.datenwerke.rs.base.service.datasources.transformers.csv.Csv2JdcbDatasourceTransformer;
import net.datenwerke.rs.base.service.datasources.transformers.csv.Csv2QueryTransformer;
import net.datenwerke.rs.base.service.datasources.transformers.csv.Csv2TableTransformer;
import net.datenwerke.rs.base.service.datasources.transformers.csv.Csv2TempTableResultTransformer;
import net.datenwerke.rs.base.service.datasources.transformers.database.Database2JasperTransformer;
import net.datenwerke.rs.base.service.datasources.transformers.database.Database2JdbcConnectionTransformer;
import net.datenwerke.rs.base.service.datasources.transformers.database.Database2JdbcDatasourceTransformer;
import net.datenwerke.rs.base.service.datasources.transformers.database.Database2TableTransformer;
import net.datenwerke.rs.core.service.datasourcemanager.hooks.DatasourceProviderHook;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.security.service.eventlogger.jpa.MergeEntityEvent;

public class DatasourceExtensionStartup {

	@Inject
	public DatasourceExtensionStartup(
		HookHandlerService hookHandler,
		EventBus eventBus,
		Provider<BaseDatasourceProviderHooker> databaseProvider,
		StandardConnectionHook connectionHooker,
		QueryCommentAdderHooker commentAdder,
		HandleCsvDatasourceMergeEvents csvDatasourceMergeHandler,
		
		Birt2TableTransformer birt2TableTransformer,
		Birt2ConnectionTransformer birt2ConnectionTransformer,
		Birt2JdbcDatasourceTransformer birt2JdbcDatasourceTransformer,
		
		Csv2InputStreamTransformer csv2InputStreamTransformer,
		Csv2JasperTransformer csv2JasperTransformer,
		Csv2TableTransformer csv2TableTransformer,
		Csv2ConnectionTransformer csv2ConnectionTransformer,
		Csv2JdcbDatasourceTransformer csv2JdbcDatasourceTransformer,
		Csv2QueryTransformer csv2QueryTransformer,
		Csv2TempTableResultTransformer csv2TempTableResultTransformer,
		
		Database2JasperTransformer database2JasperTransformer, 
		Database2JdbcConnectionTransformer database2JdbcConnectionTransformer, 
		Database2JdbcDatasourceTransformer database2JdbcDatasourceTransformer, 
		Database2TableTransformer database2TableTransformer,
		
		Provider<MonetDbStatementCancler> monetDbCancler
		){
		
		hookHandler.attachHooker(DatasourceProviderHook.class, databaseProvider);
		hookHandler.attachHooker(TableDbDatasourceOpenedHook.class, commentAdder);
		hookHandler.attachHooker(DbPoolConnectionHook.class, connectionHooker);
		
		hookHandler.attachHooker(DataSourceDefinitionTransformer.class, birt2TableTransformer);
		hookHandler.attachHooker(DataSourceDefinitionTransformer.class, birt2ConnectionTransformer);
		hookHandler.attachHooker(DataSourceDefinitionTransformer.class, birt2JdbcDatasourceTransformer);
		
		hookHandler.attachHooker(DataSourceDefinitionTransformer.class, csv2InputStreamTransformer);
		hookHandler.attachHooker(DataSourceDefinitionTransformer.class, csv2JasperTransformer);
		hookHandler.attachHooker(DataSourceDefinitionTransformer.class, csv2TableTransformer);
		hookHandler.attachHooker(DataSourceDefinitionTransformer.class, csv2ConnectionTransformer);
		hookHandler.attachHooker(DataSourceDefinitionTransformer.class, csv2JdbcDatasourceTransformer);
		hookHandler.attachHooker(DataSourceDefinitionTransformer.class, csv2QueryTransformer);
		hookHandler.attachHooker(DataSourceDefinitionTransformer.class, csv2TempTableResultTransformer);
		
		hookHandler.attachHooker(DataSourceDefinitionTransformer.class, database2JasperTransformer);
		hookHandler.attachHooker(DataSourceDefinitionTransformer.class, database2JdbcConnectionTransformer);
		hookHandler.attachHooker(DataSourceDefinitionTransformer.class, database2JdbcDatasourceTransformer);
		hookHandler.attachHooker(DataSourceDefinitionTransformer.class, database2TableTransformer);
		
		eventBus.attachObjectEventHandler(MergeEntityEvent.class, CsvDatasource.class, csvDatasourceMergeHandler);
		
		hookHandler.attachHooker(StatementCancellationHook.class, monetDbCancler);
	}
}
