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
 
 
package net.datenwerke.rs.saiku.server.rest.resources;

import java.util.List;

import mondrian.olap.MondrianServer.MondrianVersion;
import mondrian.server.monitor.ConnectionInfo;
import mondrian.server.monitor.ServerInfo;
import mondrian.server.monitor.StatementInfo;

public class MondrianStats {

	private ServerInfo server;
	private MondrianVersion mondrianVersion;
	private int openConnectionCount;
	private int openMdxStatementCount;
	private int openSqlStatementCount;
	private int executingMdxStatementCount;
	private float avgCellDimensionality;
	private List<ConnectionInfo> connections;
	private List<StatementInfo> statements;

	public MondrianStats(
			ServerInfo server, 
			MondrianVersion mv, 
			int statementCurrentlyOpenCount,
			int connectionCurrentlyOpenCount,
			int sqlStatementCurrentlyOpenCount,
			int statementCurrentlyExecutingCount, 
			float avgCellDimensionality,
			List<ConnectionInfo> connections, 
			List<StatementInfo> statements) 
	{
		
		this.server = server;
		this.mondrianVersion = mv;
		this.openConnectionCount = connectionCurrentlyOpenCount;
		this.openMdxStatementCount = statementCurrentlyOpenCount;
		this.openSqlStatementCount = sqlStatementCurrentlyOpenCount;
		this.executingMdxStatementCount = statementCurrentlyExecutingCount;
		this.avgCellDimensionality = avgCellDimensionality;
		this.connections = connections;
		this.statements = statements;
		
		
		
		
	}

	/**
	 * @return the server
	 */
	public ServerInfo getServer() {
		return server;
	}

	/**
	 * @return the openConnectionCount
	 */
	public int getOpenConnectionCount() {
		return openConnectionCount;
	}

	/**
	 * @return the openMdxStatementCount
	 */
	public int getOpenMdxStatementCount() {
		return openMdxStatementCount;
	}

	/**
	 * @return the openSqlStatementCount
	 */
	public int getOpenSqlStatementCount() {
		return openSqlStatementCount;
	}

	/**
	 * @return the executingMdxStatementCount
	 */
	public int getExecutingMdxStatementCount() {
		return executingMdxStatementCount;
	}

	/**
	 * @return the avgCellDimensionality
	 */
	public float getAvgCellDimensionality() {
		return avgCellDimensionality;
	}

	/**
	 * @return the connections
	 */
	public List<ConnectionInfo> getConnections() {
		return connections;
	}

	/**
	 * @return the statements
	 */
	public List<StatementInfo> getStatements() {
		return statements;
	}

}
