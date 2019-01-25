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
 
 
package net.datenwerke.rs.base.service.reportengines.table;

import java.util.Collection;
import java.util.List;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.NonFatalException;
import net.datenwerke.rs.base.client.reportengines.table.dto.TableReportInformation;
import net.datenwerke.rs.base.service.reportengines.table.entities.AdditionalColumnSpec;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.base.service.reportengines.table.entities.ColumnReference;
import net.datenwerke.rs.base.service.reportengines.table.entities.TableReport;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.BinaryColumnFilter;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.ColumnFilter;
import net.datenwerke.rs.base.service.reportengines.table.entities.filters.PreFilter;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.reportmanager.exceptions.ReportExecutorException;
import net.datenwerke.security.service.usermanager.entities.User;

public interface TableReportUtils {

	List<Column> getReturnedColumns(TableReport report, String executeToken)
			throws ReportExecutorException, NonFatalException;

	List<Column> getReturnedPlainColumns(TableReport report, String executeToken)
		throws ReportExecutorException, NonFatalException;

	List<Column> getReturnedColumns(TableReport report, User user, String executeToken)
			throws ReportExecutorException, NonFatalException;

	List<Column> getReturnedPlainColumns(TableReport report, User user, String executeToken)
		throws ReportExecutorException, NonFatalException;

	
	TableReportInformation getReportInformation(TableReport report, String executeToken)
			throws ReportExecutorException;

	public List<TableReport> getReportsWithMetadataDatasource(DatasourceDefinition ds);

	void remove(Column column);
	
	Column merge(Column column);

	void persist(AdditionalColumnSpec column);
	
	void persist(Column column);
	
	void remove(AdditionalColumnSpec column);

	void remove(PreFilter filter);

	List<ColumnFilter> getColumnFiltersWithColumn(Column column);

	Collection<BinaryColumnFilter> getBinaryColumnFiltersWithColumn(
			Column column);

	List<ColumnReference> getColumnReferencesFor(AdditionalColumnSpec reference);
}
