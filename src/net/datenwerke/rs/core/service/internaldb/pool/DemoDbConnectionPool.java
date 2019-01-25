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
 
 
package net.datenwerke.rs.core.service.internaldb.pool;

import java.util.Date;
import java.util.Properties;

import net.datenwerke.dbpool.config.ConnectionPoolConfig;


public class DemoDbConnectionPool implements ConnectionPoolConfig {

	private final String jdbcUrl;

	public DemoDbConnectionPool(String jdbcUrl){
		this.jdbcUrl = jdbcUrl;
	}
	
	@Override
	public String getDriver() {
		return "org.h2.Driver";
	}

	@Override
	public String getUsername() {
		return "demo";
	}

	@Override
	public String getPassword() {
		return "demo";
	}

	@Override
	public String getJdbcUrl() {
		return jdbcUrl;
	}

	@Override
	public boolean isPoolable() {
		return false;
	}

	@Override
	public boolean isMightChange() {
		return false;
	}

	@Override
	public Properties getProperties() {
		return new Properties();
	}

	@Override
	public Date getLastUpdated() {
		return null;
	}
}
