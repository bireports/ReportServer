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
 
 
package net.datenwerke.rs.base.service.datasources.hooker;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import net.datenwerke.dbpool.hooks.DbPoolConnectionHook;
import net.datenwerke.rs.base.service.datasources.events.ConnectionExceptionEvent;
import net.datenwerke.rs.base.service.datasources.events.ConnectionPossiblyBrokenEvent;
import net.datenwerke.rs.base.service.datasources.events.SlowQueryEvent;
import net.datenwerke.rs.base.service.datasources.events.StatementExecuteEvent;
import net.datenwerke.rs.utils.eventbus.EventBus;

import com.google.inject.Inject;
import com.jolbox.bonecp.ConnectionHandle;
import com.jolbox.bonecp.StatementHandle;
import com.jolbox.bonecp.hooks.AcquireFailConfig;


public class StandardConnectionHook implements DbPoolConnectionHook {

	private final EventBus eventBus;

	@Inject
	public StandardConnectionHook(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void onQueryExecuteTimeLimitExceeded(ConnectionHandle conn,
			Statement statement, String sql, Map<Object, Object> logParams,
			long timeElapsedInNs) {
		eventBus.fireEvent(new SlowQueryEvent(sql, timeElapsedInNs, logParams));
	}

	@Override
	public void onBeforeStatementExecute(ConnectionHandle conn,
			StatementHandle statement, String sql, Map<Object, Object> params) {
		eventBus.fireEvent(new StatementExecuteEvent(sql, params));
	}

	@Override
	public void onConnectionException(ConnectionHandle connection, String state, Throwable t) {
		eventBus.fireEvent(new ConnectionExceptionEvent(connection, state, t));
	}
	
	@Override
	public void onMarkPossiblyBroken(ConnectionHandle connection,
			String state, SQLException e) {
		eventBus.fireEvent(new ConnectionPossiblyBrokenEvent(connection, state, e));
	}

	@Override
	public void onAcquire(Connection connection) {
		
	}

	@Override
	public void onCheckIn(Connection connection) {
		
	}

	@Override
	public void onCheckOut(Connection connection) {
		
	}

	@Override
	public void onDestroy(Connection connection) {
		
	}

	@Override
	public void onAcquireFail(Throwable t, AcquireFailConfig acquireConfig) {
		
	}

	@Override
	public void onAfterStatementExecute(ConnectionHandle conn,
			StatementHandle statement, String sql, Map<Object, Object> params) {
		
	}

}
