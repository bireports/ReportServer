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
 
 
package net.datenwerke.rs.base.service.dbhelper.db.mssql;

import net.datenwerke.rs.base.service.dbhelper.queries.OrderQuery;
import net.datenwerke.rs.base.service.dbhelper.queries.Query;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.ColumnNamingService;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder.OrderDefinition;
import net.datenwerke.rs.base.service.reportengines.table.entities.Order;

public class MsSQLOrderQuery extends OrderQuery {

	public MsSQLOrderQuery(Query nestedQuery, QueryBuilder queryBuilder, ColumnNamingService columnNamingService) {
		super(nestedQuery, queryBuilder, columnNamingService);
	}
	
	@Override
	public void appendToBuffer(StringBuffer buf) {
		/* count the visible columns to order by */
		if(queryBuilder.isDistinct()){
			/* count the visible columns to order by */
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
			
		buf.append("SELECT TOP 9223372036854775807 * FROM ("); // 9223372036854775807 = BIGINT_MAXVALUE
		nestedQuery.appendToBuffer(buf);
		buf.append(") orderQry ORDER BY ");
		
		int i = 1;
		for(OrderDefinition def : queryBuilder.getOrderDefinitions()){
			/* if distinct && hidden -> ignore */
			if(queryBuilder.ignoreHiddenColumns() && null != def.getColumn().isHidden() && def.getColumn().isHidden())
				continue;
			
			if(i > 1)
				buf.append(", "); //$NON-NLS-1$

			buf.append(columnNamingService.getColumnName(def.getColumn()))
				.append(' ') //$NON-NLS-1$
				.append( def.getOrder().equals(Order.ASC) ? "ASC" : "DESC" ); //$NON-NLS-1$ //$NON-NLS-2$

			i++;
		}
	}


}
