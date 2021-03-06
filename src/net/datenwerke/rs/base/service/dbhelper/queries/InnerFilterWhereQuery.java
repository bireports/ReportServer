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

public class InnerFilterWhereQuery extends CompositeQuery {

	private QueryBuilder queryBuilder;


	public InnerFilterWhereQuery(Query nestedQuery, QueryBuilder queryBuilder, ColumnNamingService uniqueNameService) {
		super(nestedQuery);
		this.queryBuilder = queryBuilder;
		this.columnNamingService = uniqueNameService;
	}
	
	
	@Override
	public void appendToBuffer(StringBuffer buf) {
		if(queryBuilder.getAdditionalColumns().isEmpty()){
			nestedQuery.appendToBuffer(buf);
			buf.append(" WHERE ");
		} else {
			buf.append("SELECT * FROM (");
			nestedQuery.appendToBuffer(buf);
			buf.append(") innerFilterQry ");
			buf.append(" WHERE ");
		}
		
		Iterator<QryCondition> it = queryBuilder.getInnerConditions().iterator();
		while(it.hasNext()){
			it.next().appendToBuffer(buf, columnNamingService);
			if(it.hasNext())
				buf.append(" AND ");
		}
	}

}
