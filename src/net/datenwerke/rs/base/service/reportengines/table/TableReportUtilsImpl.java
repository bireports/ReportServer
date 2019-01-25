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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.NonFatalException;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportInformation;
import net.datenwerke.rs.base.service.reportengines.table.entities.AdditionalColumnSpec;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.ColumnReference;
import net.datenwerke.rs.base.service.reportengines.table.entities.ColumnReference__;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport__;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.BinaryColumnFilter;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.BinaryColumnFilter__;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.ColumnFilter;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.ColumnFilter__;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.PreFilter;
import net.datenwerke.rs.base.service.reportengines.table.output.object.CompiledDataCountTableReport;
import net.datenwerke.rs.base.service.reportengines.table.output.object.RSTableModel;
import net.datenwerke.rs.base.service.reportengines.table.output.object.TableDefinition;
import net.datenwerke.rs.base.service.reportengines.table.utils.ColumnMetadata;
import net.datenwerke.rs.base.service.reportengines.table.utils.TableReportColumnMetadataService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer__;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.reportmanager.ReportExecutorService;
import net.datenwerke.rs.core.service.reportmanager.engine.config.RECCountData;
import net.datenwerke.rs.core.service.reportmanager.engine.config.RECMetadata;
import net.datenwerke.rs.core.service.reportmanager.engine.config.RECReportExecutorToken;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.utils.reflection.ProxyUtils;
import net.datenwerke.rs.utils.simplequery.annotations.Join;
import net.datenwerke.rs.utils.simplequery.annotations.Predicate;
import net.datenwerke.rs.utils.simplequery.annotations.QueryByAttribute;
import net.datenwerke.rs.utils.simplequery.annotations.SimpleQuery;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.eventlogger.annotations.FireMergeEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FirePersistEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireRemoveEntityEvents;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

public class TableReportUtilsImpl implements TableReportUtils {

	private final TableReportColumnMetadataService tableReportMetadataService;
	private final ReportExecutorService reportExecutor;
	private final Provider<EntityManager> entityManagerProvider;
	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final ProxyUtils proxyUtils;
	
	@Inject
	public TableReportUtilsImpl(
		TableReportColumnMetadataService tableReportMetadataService,
		ReportExecutorService reportExecutor,
		Provider<EntityManager> entityManagerProvider,
		Provider<AuthenticatorService> authenticatorServiceProvider,
		ProxyUtils proxyUtils
		) {
		
		/* store objects */
		this.tableReportMetadataService = tableReportMetadataService;
		this.reportExecutor = reportExecutor;
		this.entityManagerProvider = entityManagerProvider;
		this. authenticatorServiceProvider = authenticatorServiceProvider;
		this.proxyUtils = proxyUtils;
	}
	
	@Override
	public TableReportInformation getReportInformation(TableReport report, String executeToken) throws ReportExecutorException {
		TableReportInformation information = new TableReportInformation();
		
		/* visible count */
		int visCols = 0;
		for(Column col : report.getColumns())
			if(null == col.isHidden() || !col.isHidden())
				visCols++;
		information.setVisibleCount(visCols);
		
		/* column count */
		RSTableModel res = (RSTableModel)  reportExecutor.execute(report, ReportExecutorService.OUTPUT_FORMAT_METADATA, new RECMetadata(), new RECReportExecutorToken(executeToken));
		TableDefinition tableDefinition = res.getTableDefinition();
		information.setColumnCount(tableDefinition.getColumnNames().size());
		
		/* row count */
		CompiledDataCountTableReport dataCount = (CompiledDataCountTableReport)  reportExecutor.execute(report, ReportExecutorService.OUTPUT_FORMAT_DATACOUNT, new RECCountData(), new RECReportExecutorToken(executeToken));
		information.setDataCount(dataCount.getDataCount());
		information.setExecuteDuration(dataCount.getExecuteDuration());
	
		return information;
	}
	
	@Override
	@SimpleQuery(from=TableReport.class, join=@Join(joinAttribute=TableReport__.metadataDatasourceContainer, where=@Predicate(attribute=DatasourceContainer__.datasource,value="ds")))
	public List<TableReport> getReportsWithMetadataDatasource(@Named("ds") DatasourceDefinition ds) {
		return null; // by magic
	}

	@Override
	@QueryByAttribute(where=ColumnFilter__.column)
	public List<ColumnFilter> getColumnFiltersWithColumn(@Named("column") Column column) {
		return null; // by magic
	}

	@Override
	public Collection<BinaryColumnFilter> getBinaryColumnFiltersWithColumn(@Named("column") Column column) {
		Set<BinaryColumnFilter> filters = new HashSet<BinaryColumnFilter>();
		filters.addAll(getBinaryColumnFiltersWithColumnA(column));
		filters.addAll(getBinaryColumnFiltersWithColumnB(column));
		
		return filters;
	}
	
	@QueryByAttribute(where=BinaryColumnFilter__.columnA)
	protected List<BinaryColumnFilter> getBinaryColumnFiltersWithColumnA(@Named("column") Column column) {
		return null; // by magic
	}
	
	@QueryByAttribute(where=BinaryColumnFilter__.columnB)
	protected List<BinaryColumnFilter> getBinaryColumnFiltersWithColumnB(@Named("column") Column column) {
		return null; // by magic
	}
	
	@Override
	@QueryByAttribute(where=ColumnReference__.reference)
	public List<ColumnReference> getColumnReferencesFor(@Named("column") AdditionalColumnSpec reference) {
		return null; // by magic
	}
	
	
		
	
	@Override
	public List<Column> getReturnedPlainColumns(TableReport report, String executeToken) throws ReportExecutorException, NonFatalException {
		User user = authenticatorServiceProvider.get().getCurrentUser();
		return getReturnedColumns(report, user, executeToken, true);
	}

	@Override
	public List<Column> getReturnedColumns(TableReport report, String executeToken) throws ReportExecutorException, NonFatalException {
		User user = authenticatorServiceProvider.get().getCurrentUser();
		return getReturnedColumns(report, user, executeToken, false);
	}
	
	@Override
	public List<Column> getReturnedPlainColumns(TableReport report, User user, String executeToken) throws ReportExecutorException, NonFatalException {
		return getReturnedColumns(report, user, executeToken, true);
	}

	@Override
	public List<Column> getReturnedColumns(TableReport report, User user, String executeToken) throws ReportExecutorException, NonFatalException {
		return getReturnedColumns(report, user, executeToken, false);
	}
	
	
	protected List<Column> getReturnedColumns(TableReport report, User user, String executeToken, boolean ignoreAnyColumnConfiguration) throws ReportExecutorException, NonFatalException {

		ArrayList<Column> columns = new ArrayList<Column>();
		
		/* all columns please */
		report.setColumns(null);
		report.setSelectAllColumns(true);
		if(ignoreAnyColumnConfiguration)
			report.setIgnoreAnyColumnConfiguration(true);

		Map<String, ColumnMetadata> columnMetadataMap = tableReportMetadataService.createColumnMetadataMap(report, user);
		
		/* disable subtotal */
		report.setEnableSubtotals(false);
		
		/* execute report */
		RSTableModel res = (RSTableModel)  reportExecutor.execute(report, user, ReportExecutorService.OUTPUT_FORMAT_METADATA, new RECMetadata(), new RECReportExecutorToken(executeToken));
		
		TableDefinition tableDefinition = res.getTableDefinition();
		
		/*
		 *  [0] -> name (String)
		 *  [1] -> Javatype (Class)
		 *  [2] -> size (Integer)
		 *  [3] -> sqlType (Integer) 
		 */
		for(Object[] column : tableDefinition.getColumns()){
			Column col = new Column();
			col.setName((String)column[0]);
			col.setType(((Integer)column[3]));
			
			if(columnMetadataMap.containsKey(col.getName())){
				ColumnMetadata metadata = columnMetadataMap.get(col.getName());
				col.setDefaultAlias(metadata.getDefaultAlias());
				col.setDescription(metadata.getDescription());
				col.setSemanticType(metadata.getSemanticType());
				col.setIndexColumn(metadata.isIndexColumn());
			}

			columns.add(col);
		}
		

		return columns;
	}
	
	@FireRemoveEntityEvents
	@Override
	public void remove(Column column){
		EntityManager em = entityManagerProvider.get();
		column = em.find(column.getClass(), column.getId());
		if(null != column)
			em.remove(column);
	}

	@FirePersistEntityEvents
	@Override
	public void persist(AdditionalColumnSpec column){
		EntityManager em = entityManagerProvider.get();
		em.persist(column);
	}
	
	@FirePersistEntityEvents
	@Override
	public void persist(Column column){
		EntityManager em = entityManagerProvider.get();
		em.persist(column);
	}
	
	@FireMergeEntityEvents
	@Override
	public Column merge(Column column){
		if(null == column)
			return null;
		EntityManager em = entityManagerProvider.get();
		column = (Column) em.find(proxyUtils.getUnproxiedClass(column.getClass()), column.getId());
		return em.merge(column);
	}
	
	
	@FireRemoveEntityEvents
	@Override
	public void remove(AdditionalColumnSpec column){
		EntityManager em = entityManagerProvider.get();
		column = em.find(column.getClass(), column.getId());
		if(null != column)
			em.remove(column);
	}
	
	@FireRemoveEntityEvents
	@Override
	public void remove(PreFilter filter){
		EntityManager em = entityManagerProvider.get();
		filter = em.find(filter.getClass(), filter.getId());
		if(null != filter)
			em.remove(filter);
	}
}
