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
 
 
package net.datenwerke.rs.core.service.internaldb;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import net.datenwerke.dbpool.config.ConnectionPoolConfig;
import net.datenwerke.rs.base.service.datasources.definitions.DatabaseDatasource;

public interface TempTableService {

	String PROPERTY_KEY_DEFAULT_DATASOURCE = "internaldb.datasource";
	
	/**
	 * Ensure to call {@link TempTableHelper#writeOperationCompleted()} after completing your write operation.
	 * 
	 * @param requester
	 * @return
	 */
	public TempTableHelper getHelper(String requester);
	public void shutdown();
	public ConnectionPoolConfig getConnectionConfig();
	void dropRSTMPtables() throws SQLException, InterruptedException, ExecutionException;

	DatabaseDatasource getInternalDbDatasource();
}
