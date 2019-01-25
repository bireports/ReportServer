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
 
 
package net.datenwerke.dbpool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.Future;

import javax.sql.DataSource;

import net.datenwerke.dbpool.config.ConnectionConfig;
import net.datenwerke.dbpool.config.ConnectionPoolConfig;

/**
 * A service that provides database connections. The service allows to pool
 * the connections. 
 * 
 *
 * @param <C>
 */
public interface DbPoolService<C> {

	/**
	 * Retrieves a {@link Future} that contains a connection config for 
	 * the pool specified by the supplied {@link ConnectionPoolConfig} object.
	 * 
	 * @param config The config object specifying the connection pool
	 * 
	 * @return A connection wrapped in a {@link Future}
	 * 
	 * @throws SQLException
	 */
	public Future<Connection> getConnection(ConnectionPoolConfig config) throws SQLException;
	
	/**
	 * Returns true if an active pool exists.
	 * 
	 * @return
	 */
	public boolean isActive();

	/**
	 * Retrieves a {@link Future} that contains a connection object for 
	 * the pool specified by the supplied {@link ConnectionPoolConfig} object.
	 * The connection is configured as specified by the {@link ConnectionConfig}.
	 * 
	 * @param poolConfig The config object specifying the pool
	 * @param connConfig A config object specifying connection properties
	 * 
	 * @return A connection wrapped in a {@link Future}
	 * 
	 * @throws SQLException
	 */
	Future<Connection> getConnection(ConnectionPoolConfig poolConfig, ConnectionConfig connConfig) throws SQLException;

	
	public DataSource getDataSource(ConnectionPoolConfig config);
	
	public DataSource getDataSource(ConnectionPoolConfig poolConfig, ConnectionConfig connConfig);
	
	
	/**
	 * Returns a map of all active pools.
	 * @return
	 */
	Map<ConnectionPoolConfig, C> getPoolMap();

	/**
	 * Shuts down the pool identified by the {@link ConnectionPoolConfig} object.
	 * 
	 * @param poolConfig The pool identifier
	 */
	void shutdownPool(ConnectionPoolConfig poolConfig);

	/**
	 * Asks all available connection pools to be closed
	 */
	void shutdownAll();
}
