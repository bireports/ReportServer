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
 
 
package net.datenwerke.dbpool.config.predefined;

import java.util.Date;
import java.util.Properties;

import net.datenwerke.dbpool.config.ConnectionPoolConfig;


public class InternalH2ConnectionPool implements ConnectionPoolConfig {

	@Override
	public String getDriver() {
		return "org.h2.Driver";
	}

	@Override
	public String getUsername() {
		return "sa";
	}

	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public String getJdbcUrl() {
		return "jdbc:h2:mem:;LOG=0;LOCK_MODE=0;UNDO_LOG=0";
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
