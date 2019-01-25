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
 
 
package net.datenwerke.rs.base.service.dbhelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * 
 *
 */
@Singleton
public class DBHelperServiceImpl implements DBHelperService {

	final private Provider<Set<DatabaseHelper>> helperProvider;
	
	@Inject
	public DBHelperServiceImpl(
		Provider<Set<DatabaseHelper>> helperProvider
		){
		this.helperProvider = helperProvider;
	}
	
	public DatabaseHelper getDatabaseHelper(String descriptor) {
		if(null == descriptor)
			return null;
		
		for(DatabaseHelper helper: helperProvider.get())
			if(descriptor.toLowerCase().equals(helper.getDescriptor().toLowerCase()))
				return helper;
		
		return null;
	}

	public Set<DatabaseHelper> getDatabaseHelpers() {
		return helperProvider.get();
	}

	@Override
	public DatabaseHelper getDatabaseHelper(Connection conn) {
		if(null == conn)
			return null;
		
		for(DatabaseHelper helper: helperProvider.get())
			try {
				if(conn.getMetaData().getDatabaseProductName().toLowerCase().startsWith((helper.getName().toLowerCase())))
					return helper;
			} catch (SQLException e) {
			}
		
		return null;
	}

}
