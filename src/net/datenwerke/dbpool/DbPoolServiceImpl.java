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
 
 
package net.datenwerke.dbpool;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

import javax.sql.DataSource;

import net.datenwerke.dbpool.config.ConnectionConfig;
import net.datenwerke.dbpool.config.ConnectionPoolConfig;

public abstract class DbPoolServiceImpl<C> implements DbPoolService<C>{
	
	
	@Override
	public DataSource getDataSource(ConnectionPoolConfig config) {
		return new RsDbPoolDataSource(config);
	}
	
	
	
	@Override
	public DataSource getDataSource(ConnectionPoolConfig poolConfig, ConnectionConfig connConfig) {
		return new RsDbPoolDataSource(poolConfig, connConfig);
	}
	
	
	class RsDbPoolDataSource implements DataSource {
		
		private PrintWriter logWriter;
		private int loginTimeout;
		private ConnectionPoolConfig connectionPoolConfig;
		private ConnectionConfig connectionConfig;
		
		public RsDbPoolDataSource() {
		}

		public RsDbPoolDataSource(ConnectionPoolConfig connectionPoolConfig) {
			this.connectionPoolConfig = connectionPoolConfig;
		}

		public RsDbPoolDataSource(ConnectionPoolConfig connectionPoolConfig, ConnectionConfig connectionConfig) {
			this.connectionPoolConfig = connectionPoolConfig;
			this.connectionConfig = connectionConfig;
		}

		@Override
		public int getLoginTimeout() {
			return loginTimeout;
		}

		@Override
		public void setLoginTimeout(int loginTimeout) {
			this.loginTimeout = loginTimeout;
		}


		@Override
		public PrintWriter getLogWriter() throws SQLException {
			return logWriter;
		}
		
		@Override
		public void setLogWriter(PrintWriter logWriter) throws SQLException {
			this.logWriter = logWriter;
		}
		
		@Override
		public Logger getParentLogger() throws SQLFeatureNotSupportedException {
			return null;
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return false;
		}

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return null;
		}

		@Override
		public Connection getConnection() throws SQLException {
			try {
				if(null != connectionPoolConfig && null != connectionConfig)
					return DbPoolServiceImpl.this.getConnection(connectionPoolConfig).get();
				else if(null != connectionPoolConfig)
					return DbPoolServiceImpl.this.getConnection(connectionPoolConfig, connectionConfig).get();

			} catch (Exception e) {
				throw new SQLException("Failed to acquire connection from pool", e);
			}
			return null;
		}

		@Override
		public Connection getConnection(String username, String password) throws SQLException {
			return null;
		}

	}

}
