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
 
 
package net.datenwerke.rs.base.service.dbhelper.db;

import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;
import net.datenwerke.rs.base.service.dbhelper.queries.LimitQuery;
import net.datenwerke.rs.base.service.dbhelper.queries.OffsetQuery;
import net.datenwerke.rs.base.service.dbhelper.queries.Query;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.ColumnNamingService;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;

public class Sybase extends DatabaseHelper {

	public static final String DB_NAME = "Sybase";
	public static final String DB_DRIVER = "com.sybase.jdbc4.jdbc.SybDriver";
	public static final String DB_DESCRIPTOR = "DBHelper_Sybase";

	@Override
	public String getDescriptor() {
		return DB_DESCRIPTOR;
	}

	@Override
	public String getDriver() {
		return DB_DRIVER;
	}

	@Override
	public String getName() {
		return DB_NAME;
	}

	@Override
	public String createDummyQuery() {
		return "select 1 dummy from dummy";
	}

	@Override
	public LimitQuery getNewLimitQuery(Query nestedQuery, QueryBuilder queryBuilder) {
		return new LimitQuery(nestedQuery, queryBuilder){
			@Override
			public void appendToBuffer(StringBuffer buf) {
				buf.append("SELECT TOP ");
				buf.append(queryBuilder.getLimit() < 1 ? 1 : queryBuilder.getLimit());
				buf.append(" * FROM (");
				nestedQuery.appendToBuffer(buf);
				buf.append(") limitQry");
			}
		};
	}

	@Override
	public OffsetQuery getNewOffsetQuery(Query nestedQuery, QueryBuilder queryBuilder, ColumnNamingService columnNamingService) {
		return new OffsetQuery(nestedQuery, queryBuilder, columnNamingService){
			@Override
			public void appendToBuffer(StringBuffer buf) {
				buf.append("SELECT TOP ");
				buf.append(queryBuilder.getLimit() < 1 ? 1 : queryBuilder.getLimit());
				buf.append(" START AT ");
				buf.append(queryBuilder.getOffset() + 1);
				buf.append(" * FROM (");
				nestedQuery.appendToBuffer(buf);
				buf.append(") limitQry");
			}
		};
	}
}
