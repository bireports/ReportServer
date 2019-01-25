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

import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;

public class LimitQuery extends CompositeQuery{

	protected QueryBuilder queryBuilder;
	protected boolean nestQuery = true;

	public LimitQuery(Query nestedQuery, QueryBuilder queryBuilder) {
		super(nestedQuery);
		this.queryBuilder = queryBuilder;
	}
	
	public void setNestQuery(boolean nestQuery) {
		this.nestQuery = nestQuery;
	}
	
	public boolean isNestQuery() {
		return nestQuery;
	}

	@Override
	public void appendToBuffer(StringBuffer buf) {
		if(0 == queryBuilder.getLimit()){
			buf.append("SELECT * FROM (");
			nestedQuery.appendToBuffer(buf);
			buf.append(") limitQry");
			buf.append(" WHERE 0 = 1 ");
		} else {
			if(nestQuery)
				buf.append("SELECT * FROM (");
			
			nestedQuery.appendToBuffer(buf);
			
			if(nestQuery)
				buf.append(") limitQry");
			
			buf.append(" LIMIT ");
			buf.append(queryBuilder.getLimit());
		}
	}
}
