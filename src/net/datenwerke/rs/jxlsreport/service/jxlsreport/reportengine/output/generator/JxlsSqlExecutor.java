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
 
 
package net.datenwerke.rs.jxlsreport.service.jxlsreport.reportengine.output.generator;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.datenwerke.rs.base.service.datasources.table.impl.utils.JasperStyleParameterParser;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterValue;
import net.sf.jasperreports.engine.JRException;
import net.sf.jxls.report.ReportManagerImpl;

public class JxlsSqlExecutor {
	protected static final Log log = LogFactory.getLog(ReportManagerImpl.class);
	private Connection connection;
	private ParameterSet parameters;

	public JxlsSqlExecutor(Connection connection, Map beans, ParameterSet parameters) {
		this.connection = connection;
		this.parameters = parameters;
	}

	public JxlsSqlExecutor(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public List exec(String sql) throws SQLException {
		return execSqlWithParameters(sql, true);
	}
	
	public List execSqlWithParameters(String sql, boolean lowercase) throws SQLException {
		Map<String, ParameterValue> pMap = null;
		if(null != parameters)
			pMap = parameters.getParameterMap();

		/* create real stmt */
		JasperStyleParameterParser parser = new JasperStyleParameterParser(connection, sql, pMap, parameters);

		try {
			ResultSet rs = null;
			PreparedStatement stmt = parser.getStatement();
			try{
				rs = stmt.executeQuery();

				RowSetDynaClass rsdc = new RowSetDynaClass(rs, lowercase, true);
				return rsdc.getRows();
			} finally {
				try{
					if(null != stmt)
						stmt.close();
				} finally {
					if(null != rs)
						rs.close();
				}
			}
		} catch (JRException e) {
			throw new RuntimeException(e);
		}
	}

	public List exec(String sql, Object... params) throws SQLException {
		return execSql(sql, true, params);
	}

	public List execCS(String sql) throws SQLException {
		return execSqlWithParameters(sql, false); 
	}
	
	public List execCS(String sql, Object... params) throws SQLException {
		return execSql(sql, false, params);
	}

	protected List execSql(String sql, boolean lowercase, Object... params) throws SQLException {
		sql = sql.replaceAll("&apos;", "'");

		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			stmt = connection.prepareStatement(sql);
			if(null != params && params.length > 0){
				int index = 1;
				for(Object p : params){
					if(p instanceof String)
						stmt.setString(index, p.toString());
					else if(p instanceof Timestamp)
						stmt.setTimestamp(index, (Timestamp) p);
					else if(p instanceof Time)
						stmt.setTime(index, (Time) p);
					else if(p instanceof Date)
						stmt.setDate(index, (Date) p);
					else if(p instanceof java.util.Date)
						stmt.setDate(index, new Date(((java.util.Date) p).getTime()));
					else if(p instanceof BigDecimal)
						stmt.setBigDecimal(index, (BigDecimal) p);
					else if(p instanceof Double)
						stmt.setDouble(index, (Double) p);
					else if(p instanceof Float)
						stmt.setFloat(index, (Float) p);
					else if(p instanceof Long)
						stmt.setLong(index, (Long) p);
					else if(p instanceof Integer)
						stmt.setInt(index, (Integer) p);
					else if(p instanceof Short)
						stmt.setShort(index, (Short) p);
					else if(p instanceof Boolean)
						stmt.setBoolean(index, (boolean) p);
					else if(p instanceof Byte)
						stmt.setByte(index, (byte) p);
					else if(p instanceof byte[])
						stmt.setBytes(index, (byte[]) p);
					else
						throw new IllegalArgumentException("Unsupported parameter of type " + p.getClass());

					index++;
				}
			}

			rs = stmt.executeQuery();

			RowSetDynaClass rsdc = new RowSetDynaClass(rs, lowercase, true);
			return rsdc.getRows();
		} finally {
			try{
				if(null != stmt)
					stmt.close();
			} finally {
				if(null != rs)
					rs.close();
			}
		}
	}


}