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
 
 
package net.datenwerke.rs.base.service.dbhelper.querybuilder.queryconditions;

import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.ColumnNamingService;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;

public class LikeQueryCondition extends CompareOpQueryCondition {

	public LikeQueryCondition(Column column, Object data, QueryBuilder queryBuilder) {
		super(column, data, queryBuilder);
	}

	@Override
	public void appendToBuffer(StringBuffer buf, ColumnNamingService columnNamingService) {
		boolean caseSensitive = null != column.getFilter() ? column.getFilter().isCaseSensitive() : true;
		if(! caseSensitive && data instanceof String)
			data = ((String)data).toLowerCase();
		
		
		DatabaseHelper dbHelper = queryBuilder.getDbHelper();
		
		switch(compareOpConfig){
			case COLUMN_DATA:
				if(! caseSensitive)
					buf.append(" LOWER("); //$NON-NLS-1$
				buf.append(dbHelper.prepareColumnForComparison(columnNamingService.getColumnName(column), column)).append(" ");
				if(! caseSensitive)
					buf.append(") "); //$NON-NLS-1$
				buf.append(getCompareOpSymbol()).append(" ");
				if(! caseSensitive)
					buf.append(" LOWER("); //$NON-NLS-1$
				buf.append(queryBuilder.nextReplacement(data, column.getType()));
				if(! caseSensitive)
					buf.append(')');
			break;
			
			case COLUMN_SUBQUERY:
				if(! caseSensitive)
					buf.append(" LOWER("); //$NON-NLS-1$
				buf.append(dbHelper.prepareColumnForComparison(columnNamingService.getColumnName(column), column)).append(" ");
				if(! caseSensitive)
					buf.append(") "); //$NON-NLS-1$
				
				buf.append(getCompareOpSymbol()).append(" ");
				
				if(! caseSensitive)
					buf.append(" LOWER("); //$NON-NLS-1$
				buf.append('(');
				query.appendToBuffer(buf);
				if(! caseSensitive)
					buf.append(")");
				buf.append(")");
			break;
			
			default:
				throw new RuntimeException("Invalid Query Condition Configuration");
		}
	}
	
	@Override
	protected String getCompareOpSymbol() {
		return "LIKE";
	}
	
}
