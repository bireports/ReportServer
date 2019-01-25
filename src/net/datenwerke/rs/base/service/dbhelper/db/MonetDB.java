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
 
 
package net.datenwerke.rs.base.service.dbhelper.db;

import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;
import net.datenwerke.rs.base.service.dbhelper.queries.LimitQuery;
import net.datenwerke.rs.base.service.dbhelper.queries.OffsetQuery;
import net.datenwerke.rs.base.service.dbhelper.queries.OrderQuery;
import net.datenwerke.rs.base.service.dbhelper.queries.Query;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.ColumnNamingService;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;

/**
 * 
 *
 */
public class MonetDB extends DatabaseHelper {

	public static final String DB_NAME = "MonetDB";
	public static final String DB_DRIVER = "nl.cwi.monetdb.jdbc.MonetDriver";
	public static final String DB_DESCRIPTOR = "DBHelper_MonetDB";

	@Override
	public String getDescriptor() {
		return DB_DESCRIPTOR; 
	}

	@Override
	public String getDriver() {
		return DB_DRIVER;
	}
	
	@Override
	public String createDummyQuery() {
		return "SELECT 1+1";
	}

	@Override
	public String getName() {
		return DB_NAME;
	}
	
	@Override
	public String getIdentifierQuoteChar() {
		return "\"";
	}
	
	@Override
	public OrderQuery getNewOrderQuery(Query nestedQuery, QueryBuilder queryBuilder,
			ColumnNamingService columnNamingService) {
		OrderQuery orderQuery = super.getNewOrderQuery(nestedQuery, queryBuilder, columnNamingService);
		orderQuery.setNestQuery(false);
		return orderQuery;
	}
	
	@Override
	public OffsetQuery getNewOffsetQuery(Query nestedQuery, QueryBuilder queryBuilder,
			ColumnNamingService columnNamingService) {
		 OffsetQuery offsetQuery = super.getNewOffsetQuery(nestedQuery, queryBuilder, columnNamingService);
		 offsetQuery.setNestQuery(false);
		 return offsetQuery;
	}

	@Override
	public LimitQuery getNewLimitQuery(Query nestedQuery, QueryBuilder queryBuilder) {
		LimitQuery limitQuery = super.getNewLimitQuery(nestedQuery, queryBuilder);
		limitQuery.setNestQuery(false);
		return limitQuery;
	}
}
