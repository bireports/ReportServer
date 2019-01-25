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
 
 
package net.datenwerke.dbpool.hooks.adapter;

import com.jolbox.bonecp.ConnectionHandle;
import com.jolbox.bonecp.StatementHandle;
import com.jolbox.bonecp.hooks.AcquireFailConfig;
import java.lang.Object;
import java.lang.String;
import java.lang.Throwable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import net.datenwerke.dbpool.hooks.DbPoolConnectionHook;
import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.hookservices.HookAdapterProcessor")
public class DbPoolConnectionHookAdapter implements DbPoolConnectionHook {

	@Override
	public void onAcquire(Connection connection)  {
	}


	@Override
	public void onCheckIn(Connection connection)  {
	}


	@Override
	public void onCheckOut(Connection connection)  {
	}


	@Override
	public void onDestroy(Connection connection)  {
	}


	@Override
	public void onAcquireFail(Throwable t, AcquireFailConfig acquireConfig)  {
	}


	@Override
	public void onQueryExecuteTimeLimitExceeded(ConnectionHandle handle, Statement statement, String sql, Map<Object, Object> logParams, long timeElapsedInNs)  {
	}


	@Override
	public void onConnectionException(ConnectionHandle connection, String state, Throwable t)  {
	}


	@Override
	public void onBeforeStatementExecute(ConnectionHandle conn, StatementHandle statement, String sql, Map<Object, Object> params)  {
	}


	@Override
	public void onAfterStatementExecute(ConnectionHandle conn, StatementHandle statement, String sql, Map<Object, Object> params)  {
	}


	@Override
	public void onMarkPossiblyBroken(ConnectionHandle connection, String state, SQLException e)  {
	}



}
