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

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.hookservices.annotations.HookConfig;

import com.jolbox.bonecp.ConnectionHandle;
import com.jolbox.bonecp.StatementHandle;
import com.jolbox.bonecp.hooks.AcquireFailConfig;

@HookConfig
public interface DbPoolConnectionHook extends Hook {

	void onAcquire(Connection connection);

	void onCheckIn(Connection connection);

	void onCheckOut(Connection connection);

	void onDestroy(Connection connection);

	void onAcquireFail(Throwable t, AcquireFailConfig acquireConfig);

	void onQueryExecuteTimeLimitExceeded(ConnectionHandle handle,
			Statement statement, String sql, Map<Object, Object> logParams,
			long timeElapsedInNs);

	void onConnectionException(ConnectionHandle connection, String state,
			Throwable t);

	void onBeforeStatementExecute(ConnectionHandle conn,
			StatementHandle statement, String sql, Map<Object, Object> params);

	void onAfterStatementExecute(ConnectionHandle conn,
			StatementHandle statement, String sql, Map<Object, Object> params);

	void onMarkPossiblyBroken(ConnectionHandle connection, String state,
			SQLException e);

}
