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
 
 
package net.datenwerke.rs.base.service.datasources.table.impl;

import java.io.Closeable;
import java.util.List;

import net.datenwerke.rs.base.service.datasources.table.impl.config.TableDatasourceConfig;
import net.datenwerke.rs.base.service.reportengines.table.entities.AdditionalColumnSpec;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.FilterBlock;
import net.datenwerke.rs.base.service.reportengines.table.output.object.TableDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainerProvider;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;

/**
 * Provides an interface for a table based data source.
 * 
 *
 */
public interface TableDataSource extends Closeable {

	/**
	 * Sets a flag that tells the datasource to not execute the report as it is, but to only count the rows.
	 */
	public void countRows();
	
	public DatasourceContainerProvider getDatasourceContainerProvider();
	
	public void applyParameters(ParameterSet paramterSet);
	
	public void applyColumnConfiguration(List<Column> columnList);
	
	public void limit(int number);
	
	public void distinct(boolean enableDistinct);
	
	public void paged(int offset, int length);
	
	/**
	 * <p>If DataSource is not "open" open will be called</p>
	 * @return
	 * @throws ReportServerException
	 */
	public boolean next() throws ReportExecutorException;
	
	/**
	 * <p>If DataSource is not "open" open will be called and cursor will be moved to first "row"</p>
	 * 
	 * @param pos
	 * @return
	 * @throws ReportServerException
	 */
	public Object getFieldValue(int pos) throws ReportExecutorException;
	
	/**
	 * <p>If DataSource is not "open" open will be called</p>
	 * 
	 * @return
	 * @throws ReportServerException
	 */
	public TableDefinition getTableDefinition() throws ReportExecutorException;
	
	public TableDefinition getPlainTableDefinition();
	
	public void open() throws ReportExecutorException;
	
	public void open(String executorToken) throws ReportExecutorException;
	
	public boolean isOpen();
	
	public void close();

	public void setPreFilter(FilterBlock rootBlock);

	public void addAdditionalColumnSpecs(
			List<AdditionalColumnSpec> additionalColumns);

	public void setIgnoreAnyColumnConfiguration(boolean b);

	ParameterSet getParameters();

	void applyConfig(TableDatasourceConfig config);
	
	public void cancelStatement();

	public void addQueryComment(String comment);
}