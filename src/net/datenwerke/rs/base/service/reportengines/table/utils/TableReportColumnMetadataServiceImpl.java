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
 
 
package net.datenwerke.rs.base.service.reportengines.table.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.NonFatalException;
import net.datenwerke.rs.base.service.reportengines.table.SimpleDataSupplier;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.base.service.reportengines.table.output.object.RSTableModel;
import net.datenwerke.rs.base.service.reportengines.table.output.object.RSTableRow;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainerProvider;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainerProviderImpl;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.security.service.usermanager.entities.User;

@Singleton
public class TableReportColumnMetadataServiceImpl implements TableReportColumnMetadataService {

	private final SimpleDataSupplier simpleDataSupplyer;

	@Inject
	public TableReportColumnMetadataServiceImpl(
			SimpleDataSupplier simpleDataSupplyer 
	) {

		this.simpleDataSupplyer = simpleDataSupplyer;
	}

	@Override
	public void augmentWithMetadata(TableReport report, User user)
			throws NonFatalException {
		augmentWithMetadata(report.getColumns(), report, user);
	}


	public void augmentWithMetadata(Collection<Column> columns, TableReport report, User user) throws NonFatalException{
		Map<String, ColumnMetadata> columnMetadataMap = createColumnMetadataMap((TableReport) report, user);

		for(Column col : columns){
			if(columnMetadataMap.containsKey(col.getName())){
				ColumnMetadata metadata = columnMetadataMap.get(col.getName());
				col.setDefaultAlias(metadata.getDefaultAlias());
				col.setDescription(metadata.getDescription());
				col.setSemanticType(metadata.getSemanticType());
				col.setIndexColumn(metadata.isIndexColumn());
			}
		}
	}



	public Map<String, ColumnMetadata> createColumnMetadataMap(TableReport report, User user) throws NonFatalException{
		Map<String, ColumnMetadata> columnMetadataMap = new HashMap<String, ColumnMetadata>();

		if(null == report.getMetadataDatasourceContainer() || null == report.getMetadataDatasourceContainer().getDatasource())
			return columnMetadataMap;

		try {
			RSTableModel res = simpleDataSupplyer.getData(createMetadataDataSourceContainer(report), user);
			if(res.getColumnCount() < 3)
				throw new IllegalArgumentException("Expected Metadata Query to return at least 3 columns.");
			
			boolean hasSemanticType = res.getColumnCount() > 3;
			boolean hasIsIndex = res.getColumnCount() > 4;
			for(RSTableRow s : res.getData()){
				if(null == s.getAt(0))
					continue;
				
				String alias = null == s.getAt(1) ? "" : s.getAt(1).toString();
				String description = null == s.getAt(2) ? "" : s.getAt(2).toString();
				String semanticType = hasSemanticType ? (null == s.getAt(3) ? null : s.getAt(3).toString()) : null;
				boolean hasIndex = hasIsIndex ? (null == s.getAt(4) ? false  :  ! "".equals(s.getAt(4))) : false;
				
				ColumnMetadata metadata = new ColumnMetadata(alias, description);
				metadata.setSemanticType(semanticType);
				metadata.setIndexColumn(hasIndex);
				
				columnMetadataMap.put(s.getAt(0).toString(), metadata);
			}
		} catch (ReportExecutorException e) {
			throw new NonFatalException("Could not load Metadata", e);
		}

		return columnMetadataMap;
	}


	private DatasourceContainerProvider createMetadataDataSourceContainer(final TableReport report){
		return new DatasourceContainerProviderImpl(report.getMetadataDatasourceContainer());
	}



}
