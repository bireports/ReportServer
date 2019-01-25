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
 
 
package net.datenwerke.rs.base.service.reportengines.table;

import net.datenwerke.gf.service.maintenance.hooks.MaintenanceTask;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.dbhelper.hooks.DbFilterExecutorHook;
import net.datenwerke.rs.base.service.reportengines.table.entities.AdditionalColumnSpec;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.base.service.reportengines.table.eventhandler.HandleTableReportStoredEventHandler;
import net.datenwerke.rs.base.service.reportengines.table.eventhandler.RemoveAdditionalColumnSpecEventHandler;
import net.datenwerke.rs.base.service.reportengines.table.hookers.BaseTableOutputGeneratorProvider;
import net.datenwerke.rs.base.service.reportengines.table.hookers.BinaryColumnFilterExecutorHooker;
import net.datenwerke.rs.base.service.reportengines.table.hookers.CSVExecutionConfigFromPropertyMapHooker;
import net.datenwerke.rs.base.service.reportengines.table.hookers.ExecuteAsTableReportPreviewHooker;
import net.datenwerke.rs.base.service.reportengines.table.hookers.PagingExecutionConfigFromPropertyMapHooker;
import net.datenwerke.rs.base.service.reportengines.table.hooks.TableOutputGeneratorProviderHook;
import net.datenwerke.rs.base.service.reportengines.table.maintenance.TableReportIntegrityValidator;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.reportmanager.engine.hooks.ReportExecutionConfigFromPropertyMapHook;
import net.datenwerke.rs.core.service.reportmanager.eventhandler.HandleDatasourceRemoveEventHandler;
import net.datenwerke.rs.core.service.reportmanager.hooks.ReportExecutorExecuteAsHooker;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.security.service.eventlogger.jpa.JpaEvent;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TableReportStartup {

	@Inject
	public TableReportStartup(
		HookHandlerService hookHandler,
		EventBus eventBus,
		CSVExecutionConfigFromPropertyMapHooker csvFromProperty,
		PagingExecutionConfigFromPropertyMapHooker pagingFromProperty,
		
		Provider<ExecuteAsTableReportPreviewHooker> executeTablePreview,
		Provider<BinaryColumnFilterExecutorHooker> binaryColumnFilterExecutor,
		
		Provider<BaseTableOutputGeneratorProvider> baseExporters,
		
		HandleDatasourceRemoveEventHandler handleDatasourceRemoveEventHandler,
		HandleTableReportStoredEventHandler handleTableReportStoredEventHandler,
		
		RemoveAdditionalColumnSpecEventHandler handleAdditionalColumnSepcRemoveHandler,
		
		TableReportIntegrityValidator tableReportIntegrity
		){
		
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, DatasourceDefinition.class, handleDatasourceRemoveEventHandler);
		
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, AdditionalColumnSpec.class, handleAdditionalColumnSepcRemoveHandler);
		
		eventBus.attachObjectEventHandler(JpaEvent.class, TableReport.class, handleTableReportStoredEventHandler);
		
		/* maintenance */
		hookHandler.attachHooker(MaintenanceTask.class, tableReportIntegrity);
		
		hookHandler.attachHooker(ReportExecutionConfigFromPropertyMapHook.class, csvFromProperty);
		hookHandler.attachHooker(ReportExecutionConfigFromPropertyMapHook.class, pagingFromProperty);
		
		hookHandler.attachHooker(ReportExecutorExecuteAsHooker.class, executeTablePreview);
		
		hookHandler.attachHooker(DbFilterExecutorHook.class, binaryColumnFilterExecutor);
		
		/* base exporters */
		hookHandler.attachHooker(TableOutputGeneratorProviderHook.class, baseExporters, HookHandlerService.PRIORITY_LOW);
	}
}
