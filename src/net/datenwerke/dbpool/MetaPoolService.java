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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.inject.Inject;
import javax.sql.DataSource;

import net.datenwerke.dbpool.annotations.PoolBoneCP;
import net.datenwerke.dbpool.annotations.PoolC3P0;
import net.datenwerke.dbpool.annotations.UseBoneCp;
import net.datenwerke.dbpool.annotations.UseConnectionPool;
import net.datenwerke.dbpool.config.ConnectionConfig;
import net.datenwerke.dbpool.config.ConnectionPoolConfig;
import net.datenwerke.dbpool.config.predefined.StandardConnectionConfig;
import net.datenwerke.dbpool.exceptions.DriverNotFoundException;

import com.google.inject.Provider;

/**
 * A pool service that can either use {@link BoneCpPoolServiceImpl}
 * or {@link DbC3p0PoolServiceImpl} as backend.
 * 
 *
 */
public class MetaPoolService implements DbPoolService {

	private final DbPoolService boneCp;
	private final DbPoolService c3p0;
	private final Provider<Boolean> useBoneCpProvider;
	private final Provider<Boolean> useConnectionPool;
	
	@Inject
	public MetaPoolService(
		@PoolBoneCP DbPoolService boneCp,
		@PoolC3P0 DbPoolService c3p0,
		@UseConnectionPool Provider<Boolean> useConnectionPool,
		@UseBoneCp Provider<Boolean> useBoneCpProvider
		) {
		this.boneCp = boneCp;
		this.c3p0 = c3p0;
		this.useConnectionPool = useConnectionPool;
		this.useBoneCpProvider = useBoneCpProvider;
	}
	
	protected boolean isUseBoneCp(){
		return Boolean.TRUE.equals(useBoneCpProvider.get());
	}
	
	@Override
	public Future getConnection(ConnectionPoolConfig config)
			throws SQLException {
		if(! useConnectionPool.get() || ! config.isPoolable())
			return getConnectionDirectly(config, new StandardConnectionConfig());
		
		if(isUseBoneCp())
			return boneCp.getConnection(config);
		return c3p0.getConnection(config);
	}
	
	public DbPoolService getActiveService() {
		if(isUseBoneCp())
			return boneCp;
		return c3p0;
	}
	
	public DbPoolService getC3P0(){
		return c3p0;
	}
	
	public DbPoolService getBoneCp() {
		return boneCp;
	}

	@Override
	public boolean isActive() {
		if(isUseBoneCp())
			return boneCp.isActive();
		return c3p0.isActive();
	}

	@Override
	public Future getConnection(ConnectionPoolConfig poolConfig,
			ConnectionConfig connConfig) throws SQLException {
		if(! useConnectionPool.get() || ! poolConfig.isPoolable())
			return getConnectionDirectly(poolConfig, connConfig);
		
		if(isUseBoneCp())
			return boneCp.getConnection(poolConfig, connConfig);
		return c3p0.getConnection(poolConfig, connConfig);
	}
	
	protected Future<Connection> getConnectionDirectly(final ConnectionPoolConfig config, final ConnectionConfig connConfig) throws SQLException {
		try {
			Class.forName(config.getDriver());
		} catch (ClassNotFoundException e) {
			throw new DriverNotFoundException(config.getDriver(), e);
		}
		
		return new Future<Connection>(){

			@Override
			public boolean cancel(boolean mayInterruptIfRunning) {
				return false;
			}

			@Override
			public Connection get() throws InterruptedException,
					ExecutionException {
				/* open connection */
				Connection conn = null;
				
				/* set timeout */
				DriverManager.setLoginTimeout(10);
				
				/* create connection */
				try {
					conn = DriverManager.getConnection(
					         config.getJdbcUrl(),
					         config.getUsername(),
					         config.getPassword() );
					
					configureConnection(conn, connConfig);
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
				
				return conn;
			}

			@Override
			public Connection get(long timeout, TimeUnit unit)
					throws InterruptedException, ExecutionException,
					TimeoutException {
				return get();
			}

			@Override
			public boolean isCancelled() {
				return false;
			}

			@Override
			public boolean isDone() {
				return true;
			}
			
		};
	}

	protected void configureConnection(Connection conn, ConnectionConfig connConfig) throws SQLException {
		if(connConfig.isReadOnly())
			conn.setReadOnly(true);
		
		DatabaseMetaData metaData = conn.getMetaData();
		
		if(null != connConfig.getIsolationLevel() && metaData.supportsTransactionIsolationLevel(connConfig.getIsolationLevel()))
			conn.setTransactionIsolation(connConfig.getIsolationLevel());
	}

	@Override
	public Map getPoolMap() {
		if(isUseBoneCp())
			return boneCp.getPoolMap();
		return c3p0.getPoolMap();
	}

	@Override
	public void shutdownPool(ConnectionPoolConfig poolConfig) {
		boneCp.shutdownPool(poolConfig);
		c3p0.shutdownPool(poolConfig);
	}

	@Override
	public void shutdownAll() {
		boneCp.shutdownAll();
		c3p0.shutdownAll();
	}
	
	@Override
	public DataSource getDataSource(ConnectionPoolConfig config) {
		if(isUseBoneCp())
			return boneCp.getDataSource(config);
		return c3p0.getDataSource(config);
	}
	
	@Override
	public DataSource getDataSource(ConnectionPoolConfig poolConfig,
			ConnectionConfig connConfig) {
		if(isUseBoneCp())
			return boneCp.getDataSource(poolConfig, connConfig);
		return c3p0.getDataSource(poolConfig, connConfig);
	}

}
