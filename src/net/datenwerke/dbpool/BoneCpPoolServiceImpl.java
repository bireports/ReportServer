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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

import net.datenwerke.dbpool.config.ConnectionConfig;
import net.datenwerke.dbpool.config.ConnectionPoolConfig;
import net.datenwerke.dbpool.config.DwBoneCpConfig;
import net.datenwerke.dbpool.config.predefined.StandardConnectionConfig;
import net.datenwerke.dbpool.exceptions.DriverNotFoundException;
import net.datenwerke.dbpool.hooks.BoneCpConnectionHook;
import net.datenwerke.rs.utils.entitydiff.EntityDiffService;

import com.google.gwt.core.shared.GWT;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.ConnectionHandle;

public class BoneCpPoolServiceImpl extends DbPoolServiceImpl<BoneCP> implements DbPoolService<BoneCP> {

	private Map<ConnectionPoolConfig, BoneCP> poolMap = new ConcurrentHashMap<ConnectionPoolConfig, BoneCP>();
	private Map<ConnectionPoolConfig, ConnectionPoolConfig> configMap = new ConcurrentHashMap<ConnectionPoolConfig, ConnectionPoolConfig>();
	
	private Map<ConnectionPoolConfig, ReentrantLock> lockMap = new ConcurrentHashMap<ConnectionPoolConfig, ReentrantLock>();
	private Provider<BoneCpConnectionHook> connectionHookProvider;
	
	private final EntityDiffService entityDiffService;
	private Boolean enableStatistics;
	private final JdbcService jdbcService;
	
	@Inject
	public BoneCpPoolServiceImpl(
		EntityDiffService entityDiffService,
		Provider<BoneCpConnectionHook> connectionHookProvider,
		JdbcService jdbcService
		){
	
		/* store objects */
		this.entityDiffService = entityDiffService;
		this.connectionHookProvider = connectionHookProvider;
		this.jdbcService = jdbcService;
	}

	@Override
	public Future<Connection> getConnection(ConnectionPoolConfig poolConfig) throws SQLException {
		return getConnection(poolConfig, new StandardConnectionConfig());
	}
	
	@Override
	public Future<Connection> getConnection(ConnectionPoolConfig poolConfig, ConnectionConfig connConfig) throws SQLException {
		return getConnectionFromPool(poolConfig, connConfig);
	}

	@Override
	public Map<ConnectionPoolConfig, BoneCP> getPoolMap(){
		return new HashMap<ConnectionPoolConfig, BoneCP>(poolMap);
	}
	
	@Override
	public void shutdownAll(){
		for(ConnectionPoolConfig conf : new ArrayList<ConnectionPoolConfig>(poolMap.keySet()))
			shutdownPool(conf);
	}
	
	@Override
	public void shutdownPool(ConnectionPoolConfig poolConfig){
		ReentrantLock lock;
		synchronized (BoneCpPoolServiceImpl.class) {
			if(! lockMap.containsKey(poolConfig))
				lockMap.put(poolConfig, new ReentrantLock());

			lock = lockMap.get(poolConfig);
		}
		
		lock.lock();
		
		try{
			if(poolMap.containsKey(poolConfig)){
				poolMap.get(poolConfig).close();
				
				poolMap.remove(poolConfig);
			}
		}finally {
			lock.unlock();
		}
	}
	
	protected Future<Connection> getConnectionFromPool(ConnectionPoolConfig poolConfig, final ConnectionConfig connConfig) throws SQLException {
		ReentrantLock lock;
		synchronized (BoneCpPoolServiceImpl.class) {
			if(! lockMap.containsKey(poolConfig))
				lockMap.put(poolConfig, new ReentrantLock());

			lock = lockMap.get(poolConfig);
		}
		
		lock.lock();
		
		try{
			/* close old pool ? */
			if(poolMap.containsKey(poolConfig) && isUpdateConfig(poolConfig)){
				poolMap.get(poolConfig).close();
				poolMap.remove(poolConfig);
			}
	
			/* create new pool ? */
			if(! poolMap.containsKey(poolConfig)){
				BoneCP pool = createPool(poolConfig);
				poolMap.put(poolConfig, pool);
				configMap.put(poolConfig, poolConfig);
			}
			
			/* get connection */
			final BoneCP pool = poolMap.get(poolConfig);
			final Future<Connection> future = pool.getAsyncConnection();
			
			return new Future<Connection>() {
	
				@Override
				public boolean cancel(boolean mayInterruptIfRunning) {
					return future.cancel(mayInterruptIfRunning);
				}
	
				@Override
				public Connection get() throws InterruptedException,
						ExecutionException {
					return tryGetConncetion(future.get(), pool, connConfig, 1);
				}
	
				@Override
				public Connection get(long timeout, TimeUnit unit)
						throws InterruptedException, ExecutionException,
						TimeoutException {
					return tryGetConncetion(future.get(timeout, unit), pool, connConfig, 1);
				}
	
				@Override
				public boolean isCancelled() {
					return future.isCancelled();
				}
	
				@Override
				public boolean isDone() {
					return future.isDone();
				}
				
			};
		} finally {
			lock.unlock();
		}
	}

	protected Connection tryGetConncetion(Connection connection, BoneCP pool,
			ConnectionConfig connConfig, int cnt) throws InterruptedException, ExecutionException {
		BoneCPConfig config = pool.getConfig();
		
		try {
			configureConnection(connection, connConfig);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
		/* try connection */
		if(connection instanceof ConnectionHandle && config instanceof DwBoneCpConfig && cnt <= ((DwBoneCpConfig)config).getTestConnectionOnAcquireAttempts()){
			if(! pool.isConnectionHandleAlive((ConnectionHandle)connection)){
				try{
					((ConnectionHandle)connection).refreshConnection();
					ConnectionHandle newConnection = ((ConnectionHandle)connection).recreateConnectionHandle();
					return tryGetConncetion(newConnection, pool, connConfig, cnt + 1);
				} catch(SQLException e){
					throw new ExecutionException(e);
				}
			}
		}
		return connection;
	}

	private BoneCP createPool(ConnectionPoolConfig config) throws SQLException {
		try {
			Class.forName(config.getDriver());
		} catch (ClassNotFoundException e) {
			throw new DriverNotFoundException(config.getDriver(), e);
		}
		
		DwBoneCpConfig boneConfig;
		try {
			boneConfig = new DwBoneCpConfig(config.getProperties());
		} catch (Exception e) {
			throw new RuntimeException("Could not configure connection pool", e);
		}
		
		boneConfig.setUsername(config.getUsername());
		boneConfig.setPassword(config.getPassword());
		boneConfig.setJdbcUrl(jdbcService.adaptJdbcUrl(config.getJdbcUrl()));
		
		boneConfig.setConnectionHook(connectionHookProvider.get());
		
		// debug mode
		if(! GWT.isProdMode())
			boneConfig.setCloseConnectionWatch(true);
		
		if(Boolean.TRUE.equals(enableStatistics))
			boneConfig.setStatisticsEnabled(true);
		
		return new BoneCP(boneConfig);
	}

	protected boolean isUpdateConfig(ConnectionPoolConfig config) {
		if(! poolMap.containsKey(config))
			return false;
		
		if(! config.isMightChange())
			return false;
		
		ConnectionPoolConfig oldConfig = configMap.get(config);
		
		Date lastUpdated = config.getLastUpdated();
		Date oldLastUpdated = oldConfig.getLastUpdated();
		if(null == lastUpdated || null == oldLastUpdated)
			return ! entityDiffService.diff(oldConfig, config).isEqual();
		
		boolean update = lastUpdated.after(oldLastUpdated);
		return update;
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
							 jdbcService.adaptJdbcUrl(config.getJdbcUrl()),
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
	public boolean isActive() {
		return ! poolMap.isEmpty();
	}

	public Boolean getEnableStatistics() {
		return enableStatistics;
	}

	public void setEnableStatistics(Boolean enableStatistics) {
		this.enableStatistics = enableStatistics;
	}
	
	

}
