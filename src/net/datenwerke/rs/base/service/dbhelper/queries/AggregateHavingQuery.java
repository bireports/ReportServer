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
 
 
package net.datenwerke.rs.base.service.dbhelper.queries;

import java.util.Iterator;

import net.datenwerke.rs.base.service.dbhelper.querybuilder.ColumnNamingService;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.queryconditions.QryCondition;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;

public class AggregateHavingQuery extends CompositeQuery{

	final private QueryBuilder queryBuilder;

	public AggregateHavingQuery(Query nestedQuery, QueryBuilder queryBuilder, ColumnNamingService columnNamingService) {
		super(nestedQuery);
		this.queryBuilder = queryBuilder;
		this.columnNamingService = columnNamingService;
	}

	@Override
	public void appendToBuffer(StringBuffer buf) {

		if(!queryBuilder.hasAggregateColumns()){
			nestedQuery.appendToBuffer(buf);
			return;
		}
		
		buf.append("SELECT ");

		int i = 1;
		for(Column col : queryBuilder.getColumns()){
			/* if distinct && hidden -> ignore */
			if(queryBuilder.ignoreHiddenColumns() && null != col.isHidden() && col.isHidden())
				continue;
					
			if(null != col.getAggregateFunction()){
				if(i > 1)
					buf.append(", ");
				
				buf.append(queryBuilder.getDbHelper().aggregateFunction(
								col.getAggregateFunction(), 
								columnNamingService.getColumnName(col)));

				buf.append(" AS ");
				buf.append(columnNamingService.getColumnName(col));
				i++;
			}else{
				if(i > 1)
					buf.append(", ");
				buf.append(columnNamingService.getColumnName(col));
				i++;
			}
		}
		
		buf.append(" FROM (");
		
		nestedQuery.appendToBuffer(buf);
		
		buf.append(") aggregateQry ");
		i = 1;
		for(Column col : queryBuilder.getColumns()){
			if(null == col.getAggregateFunction()){
				if(!col.isGroupedBy())
					continue;
				
				if(i == 1)
					buf.append("GROUP BY ");
				if(i > 1)
					buf.append(", ");
				buf.append(columnNamingService.getColumnName(col));
				i++;
			}
		}
		
		
		ColumnNamingService havingNamingService = new ColumnNamingService() {
			
			@Override
			public String getColumnName(Column col) {
				return queryBuilder.getDbHelper().aggregateFunction(
						col.getAggregateFunction(), 
						columnNamingService.getColumnName(col));
			}
		};
		
		Iterator<QryCondition> it = queryBuilder.getConditionsHaving().iterator();
		if(it.hasNext())
			buf.append(" HAVING ");
		while(it.hasNext()){
			it.next().appendToBuffer(buf, havingNamingService);
			if(it.hasNext())
				buf.append(" AND ");
		}
	}
	
	

}
