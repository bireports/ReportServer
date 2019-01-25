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
 
 
package net.datenwerke.rs.base.client.datasources;

import java.util.Collection;

import net.datenwerke.rs.base.client.dbhelper.dto.DatabaseHelperDto;
import net.datenwerke.rs.base.client.reportengines.table.dto.AggregateFunctionDto;

public class BaseDatasourceUiServiceImpl implements BaseDatasourceUiService {

	private Collection<DatabaseHelperDto> databaseHelper;
	
	@Override
	public Collection<DatabaseHelperDto> getDatabaseHelpers() {
		if(null == this.databaseHelper)
			throw new IllegalStateException("DatabseHelper were not yet set."); //$NON-NLS-1$
		return this.databaseHelper;
	}

	@Override
	public void setDatabaseHelpers(Collection<DatabaseHelperDto> databaseHelper) {
		if(null != this.databaseHelper)
			throw new IllegalStateException("DatabseHelper were already set."); //$NON-NLS-1$
		this.databaseHelper = databaseHelper;
	}

	@Override
	public boolean isNumericalAggregation(AggregateFunctionDto aggregate) {
		switch(aggregate){
		case SUM:
		case AVG:
		case VARIANCE:
			return true;
		}
		return false;
	}
}
