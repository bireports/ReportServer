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
 
 
package net.datenwerke.dbpool.hooks;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import com.google.inject.Inject;
import com.jolbox.bonecp.ConnectionHandle;
import com.jolbox.bonecp.StatementHandle;
import com.jolbox.bonecp.hooks.AbstractConnectionHook;
import com.jolbox.bonecp.hooks.AcquireFailConfig;
import com.jolbox.bonecp.hooks.ConnectionState;

public class BoneCpConnectionHook extends AbstractConnectionHook {

	@Inject protected HookHandlerService hookHandler;
	
	@Override
	public void onAcquire(ConnectionHandle connection) {
		for(DbPoolConnectionHook hooker : hookHandler.getHookers(DbPoolConnectionHook.class))
			hooker.onAcquire(connection);
		
		super.onAcquire(connection);
	}

	@Override
	public void onCheckIn(ConnectionHandle connection) {
		for(DbPoolConnectionHook hooker : hookHandler.getHookers(DbPoolConnectionHook.class))
			hooker.onCheckIn(connection);
		
		super.onCheckIn(connection);
	}

	@Override
	public void onCheckOut(ConnectionHandle connection) {
		for(DbPoolConnectionHook hooker : hookHandler.getHookers(DbPoolConnectionHook.class))
			hooker.onCheckOut(connection);
		
		super.onCheckOut(connection);
	}
	
	@Override
	public void onDestroy(ConnectionHandle connection) {
		for(DbPoolConnectionHook hooker : hookHandler.getHookers(DbPoolConnectionHook.class))
			hooker.onDestroy(connection);
		
		super.onDestroy(connection);
	}

	@Override
	public boolean onAcquireFail(Throwable t, AcquireFailConfig acquireConfig) {
		for(DbPoolConnectionHook hooker : hookHandler.getHookers(DbPoolConnectionHook.class))
			hooker.onAcquireFail(t, acquireConfig);
		
		return super.onAcquireFail(t, acquireConfig);
	}
	
	public void onQueryExecuteTimeLimitExceeded(ConnectionHandle handle, Statement statement, String sql, Map<Object, Object> logParams, long timeElapsedInNs){
		for(DbPoolConnectionHook hooker : hookHandler.getHookers(DbPoolConnectionHook.class))
			hooker.onQueryExecuteTimeLimitExceeded(handle, statement, sql, logParams, timeElapsedInNs);
		
		super.onQueryExecuteTimeLimitExceeded(handle, statement, sql, logParams, timeElapsedInNs);
	}
	
	public boolean onConnectionException(ConnectionHandle connection, String state, Throwable t) {
		for(DbPoolConnectionHook hooker : hookHandler.getHookers(DbPoolConnectionHook.class))
			hooker.onConnectionException(connection, state, t);
		
		return super.onConnectionException(connection, state, t);
	}

	@Override
	public void onBeforeStatementExecute(ConnectionHandle conn, StatementHandle statement, String sql, Map<Object, Object> params) {
		for(DbPoolConnectionHook hooker : hookHandler.getHookers(DbPoolConnectionHook.class))
			hooker.onBeforeStatementExecute(conn, statement, sql, params);
		
		super.onBeforeStatementExecute(conn, statement, sql, params);
	}
	
	@Override
	public void onAfterStatementExecute(ConnectionHandle conn, StatementHandle statement, String sql, Map<Object, Object> params) {
		for(DbPoolConnectionHook hooker : hookHandler.getHookers(DbPoolConnectionHook.class))
			hooker.onAfterStatementExecute(conn, statement, sql, params);
		
		super.onAfterStatementExecute(conn, statement, sql, params);
	}

	@Override
	public ConnectionState onMarkPossiblyBroken(ConnectionHandle connection, String state, SQLException e) {
		for(DbPoolConnectionHook hooker : hookHandler.getHookers(DbPoolConnectionHook.class))
			hooker.onMarkPossiblyBroken(connection, state, e);
		
		return super.onMarkPossiblyBroken(connection, state, e);
	}
	
	
}
