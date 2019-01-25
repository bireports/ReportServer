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
 
 
package net.datenwerke.rs.base.service.dbhelper.hookers;

import java.util.ArrayList;
import java.util.Collection;

import com.google.inject.Inject;

import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;
import net.datenwerke.rs.base.service.dbhelper.db.Firebird;
import net.datenwerke.rs.base.service.dbhelper.db.H2;
import net.datenwerke.rs.base.service.dbhelper.db.HSQL;
import net.datenwerke.rs.base.service.dbhelper.db.MariaDB;
import net.datenwerke.rs.base.service.dbhelper.db.MonetDB;
import net.datenwerke.rs.base.service.dbhelper.db.MySQL;
import net.datenwerke.rs.base.service.dbhelper.db.PostgreSQL;
import net.datenwerke.rs.base.service.dbhelper.db.Sybase;
import net.datenwerke.rs.base.service.dbhelper.db.Vertica;
import net.datenwerke.rs.base.service.dbhelper.db.db2.DB2;
import net.datenwerke.rs.base.service.dbhelper.db.informix.Informix;
import net.datenwerke.rs.base.service.dbhelper.db.mssql.MsSQL;
import net.datenwerke.rs.base.service.dbhelper.db.oracle.Oracle;
import net.datenwerke.rs.base.service.dbhelper.hooks.DatabaseHelperProviderHook;

public class ProvideBaseDatabaseHelpersHookers implements
		DatabaseHelperProviderHook {

	private final Collection<DatabaseHelper> helpers;
	
	@Inject
	public ProvideBaseDatabaseHelpersHookers(
			DB2 db2,
			H2 h2,
			Firebird firebird,
			MariaDB mariaDb,
			MsSQL mssql,
			MySQL mysql,
			MonetDB monetdb,
			Oracle oracle,
			HSQL hsql,
			PostgreSQL postgres, 
			Informix informix,
			Vertica vertica,
			Sybase sybase
		){
		
		 helpers = new ArrayList<DatabaseHelper>();
		 helpers.add(db2);
		 helpers.add(h2);
		 helpers.add(mssql);
		 helpers.add(mysql);
		 helpers.add(mariaDb);
		 helpers.add(monetdb);
		 helpers.add(oracle);
		 helpers.add(firebird);
		 helpers.add(hsql);
		 helpers.add(postgres);
		 helpers.add(informix);
		 helpers.add(vertica);
		 helpers.add(sybase);
	}
	
	@Override
	public Collection<DatabaseHelper> provideDatabaseHelpers() {
		return helpers;
	}

}
