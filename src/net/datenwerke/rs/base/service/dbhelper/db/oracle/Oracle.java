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
 
 
package net.datenwerke.rs.base.service.dbhelper.db.oracle;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.utils.SqlTypes;
import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;
import net.datenwerke.rs.base.service.dbhelper.queries.LimitQuery;
import net.datenwerke.rs.base.service.dbhelper.queries.OffsetQuery;
import net.datenwerke.rs.base.service.dbhelper.queries.Query;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.ColumnNamingService;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.rs.utils.oracle.StupidOracleService;

import com.google.inject.Inject;

/**
 * 
 *
 */
public class Oracle extends DatabaseHelper {

	public static final String CONVERT_BLOBS_FOR_FILTERING_PROPERTY = "database.oracle.filter.convertclobs";
	
	public static final String DB_NAME = "Oracle";
	public static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	public static final String DB_DESCRIPTOR = "DBHelper_Oracle";


	private final StupidOracleService sos;

	private ConfigService configService;
	
	@Inject
	public Oracle(
		StupidOracleService sos, 
		ConfigService configService
		) {
		
		this.sos = sos;
		this.configService = configService;
	}
	
	private boolean isConvertClob(){
		return configService.getConfigFailsafe("misc/misc.cf").getBoolean(CONVERT_BLOBS_FOR_FILTERING_PROPERTY, true);
	}
	
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
	public ResultSetObjectHandler createResultSetHandler(final ResultSet resultSet, final Connection con) throws SQLException {
		ResultSetMetaData md = resultSet.getMetaData();
		int cnt = md.getColumnCount();
		final List<Integer> dateIndices = new ArrayList<Integer>();
		for(int i = 1; i <= cnt; i++)
			if(SqlTypes.isDateLikeType(md.getColumnType(i)))
				dateIndices.add(i);
		
		if(dateIndices.isEmpty())
			return super.createResultSetHandler(resultSet, con);
		
		return new ResultSetObjectHandler() {
			
			@Override
			public Object getObject(int pos) throws SQLException {
				if(dateIndices.contains(pos)){
					Object obj = resultSet.getObject(pos);
					
					if(sos.isOracleTimestamp(obj)){
						return sos.getTimeStampFromOracleTimestamp(obj, con);
					}else if(sos.isOracleDatum(obj))
						return sos.getDateFromOracleDatum(obj);
				}
				return resultSet.getObject(pos);
			}
		};
	}
	
	@Override
	public String prepareColumnForSorting(String name, Column column) {
		if(isConvertClob() && Integer.valueOf(SqlTypes.CLOB).equals(column.getType()))
			return "dbms_lob.substr(" + name + ", 4000, 1)";
		return super.prepareColumnForComparison(name, column);
	}
	
	@Override
	public String prepareColumnForComparison(String name, Column column) {
		if(isConvertClob() && Integer.valueOf(SqlTypes.CLOB).equals(column.getType()))
			return "dbms_lob.substr(" + name + ", 4000, 1)";
		return super.prepareColumnForComparison(name, column);
	}
	
	@Override
	public String prepareColumnForDistinctQuery(String name, Column column) {
		if(isConvertClob() && Integer.valueOf(SqlTypes.CLOB).equals(column.getType()))
			return "dbms_lob.substr(" + name + ", 4000, 1) " + name;
		return super.prepareColumnForDistinctQuery(name, column);
	}
	
	@Override
	public LimitQuery getNewLimitQuery(Query nestedQuery, QueryBuilder queryBuilder) {
		return new OracleLimitQuery(nestedQuery, queryBuilder);
	}

	@Override
	public OffsetQuery getNewOffsetQuery(Query nestedQuery, QueryBuilder queryBuilder, ColumnNamingService columnNamingService) {
		return new OracleOffsetQuery(nestedQuery, queryBuilder, columnNamingService);
	}
	
}
