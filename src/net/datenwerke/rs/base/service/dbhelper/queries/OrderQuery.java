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
 
 
package net.datenwerke.rs.base.service.dbhelper.queries;

import net.datenwerke.rs.base.service.dbhelper.querybuilder.ColumnNamingService;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder.OrderDefinition;
import net.datenwerke.rs.base.service.reportengines.table.entities.Order;

public class OrderQuery extends CompositeQuery{

	protected QueryBuilder queryBuilder;
	protected boolean nestQuery = true;

	public OrderQuery(Query nestedQuery, QueryBuilder queryBuilder, ColumnNamingService columnNamingService) {
		super(nestedQuery);
		this.queryBuilder = queryBuilder;
		this.columnNamingService = columnNamingService;
	}
	
	public void setNestQuery(boolean nestQuery) {
		this.nestQuery = nestQuery;
	}
	
	public boolean isNestQuery() {
		return nestQuery;
	}

	@Override
	public void appendToBuffer(StringBuffer buf) {
		/* no order if we just want to count */
		if(queryBuilder.isCountRows()){
			nestedQuery.appendToBuffer(buf);
			return;
		}
		
		/* count the visible columns to order by */
		if(queryBuilder.ignoreHiddenColumns()){
			int count = 0;
			for(OrderDefinition def : queryBuilder.getOrderDefinitions()){
				if(null == def.getColumn().isHidden() || !def.getColumn().isHidden())
					count++;
			}
			
			if(count == 0){
				nestedQuery.appendToBuffer(buf);
				return;
			}
		}
		
		if(nestQuery)
			buf.append("SELECT * FROM (");
		
		nestedQuery.appendToBuffer(buf);
		
		if(nestQuery)
			buf.append(") orderQry ORDER BY ");
		else
			buf.append(" ORDER BY ");
		
		int i = 1;
		for(OrderDefinition def : queryBuilder.getOrderDefinitions()){
			if(queryBuilder.ignoreHiddenColumns() && null != def.getColumn().isHidden() && def.getColumn().isHidden())
				continue;
			
			if(i > 1)
				buf.append(", "); //$NON-NLS-1$

			buf.append(queryBuilder.getDbHelper().prepareColumnForSorting(columnNamingService.getColumnName(def.getColumn()), def.getColumn()))
				.append(' ') //$NON-NLS-1$
				.append( def.getOrder().equals(Order.ASC) ? "ASC" : "DESC" ); //$NON-NLS-1$ //$NON-NLS-2$

			i++;
		}
	}
	
}

