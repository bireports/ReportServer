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
 
 
package net.datenwerke.rs.base.service.datasources.statementmanager;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

public interface StatementManagerService {

	public static class StatementContainer{
		private final Statement statement;
		private final Connection connection;
		public StatementContainer(Statement statement, Connection connection) {
			super();
			this.statement = statement;
			this.connection = connection;
		}
		public Statement getStatement() {
			return statement;
		}
		public Connection getConnection() {
			return connection;
		}
	}
	
	void registerStatement(String statementId, Statement statement, Connection connection);

	public Collection<StatementContainer> getStatements(String statementId);

	public void unregisterStatement(String statementId);
	
	public void cancelStatement(String statementId);

	Map<String, Map<String, StatementContainer>> getUserStatementMap();

	Map<String, Map<String, StatementContainer>> getUserStatementMap(Long currentUserId);

	Map<Long, Map<String, Map<String, StatementContainer>>> getAllStatements();

	Date getRegisterDate(String id);

}
