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
 
 
package net.datenwerke.rs.core.service.reportmanager.exceptions;

import org.apache.commons.lang.StringUtils;

public class DatabaseConnectionException extends ReportManagerRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2032418006003893781L;

	public DatabaseConnectionException(String url, String username, Throwable e){
		super("Could not open connection to: " + url + " with user: " + username + ". " + StringUtils.left(e.getMessage(), 200)); //$NON-NLS-1$ //$NON-NLS-2$
		initCause(e);
	}
	
	public DatabaseConnectionException(String message){
		super(message);
	}
}
