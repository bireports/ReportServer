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
 
 
package net.datenwerke.rs.base.service.datasources.events;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.rs.utils.exception.ExceptionServices;
import net.datenwerke.security.service.eventlogger.DwLoggedEvent;

import com.google.inject.Inject;
import com.jolbox.bonecp.ConnectionHandle;

public class ConnectionExceptionEvent extends DwLoggedEvent {

	@Inject
	private static ExceptionServices exceptionServices;
	
	public ConnectionExceptionEvent(ConnectionHandle connection, String state, Throwable t) {
		super(linearize(connection, state, t));
	}

	private static String[] linearize(ConnectionHandle connection, String state,
			Throwable t) {
		List<String> list = new ArrayList<String>();

		list.add("JdbcUrl");
		try {
			list.add(connection.getPool().getConfig().getJdbcUrl());
		} catch (Exception e) {
			list.add(e.getMessage());
		}
		
		list.add("state");
		list.add(state);
		list.add("exception");
		if(null != t.getMessage())
			list.add(t.getMessage());
		else
			list.add("null");
		list.add("stacktrace");
		list.add(exceptionServices.exceptionToString(t));
		
		return list.toArray(new String[]{});
	}

	@Override
	public String getLoggedAction() {
		return "CONNECTION_EXCEPTION";
	}

}
