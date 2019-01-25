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
 
 
package net.datenwerke.rs.base.service.dbhelper.db;

import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;

public class Vertica  extends DatabaseHelper {

	public static final String DB_NAME = "Vertica";
	public static final String DB_DRIVER = "com.vertica.jdbc.Driver";
	public static final String DB_DESCRIPTOR = "DBHelper_Vertica";

	@Override
	public String getDescriptor() {
		return DB_DESCRIPTOR;
	}

	@Override
	public String getDriver() {
		return DB_DRIVER;
	}

	@Override
	public String getName() {
		return DB_NAME;
	}

	@Override
	public String createDummyQuery() {
		return "select 1 from dual";
	}

}
