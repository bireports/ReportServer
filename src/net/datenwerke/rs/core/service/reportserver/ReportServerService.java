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
 
 
package net.datenwerke.rs.core.service.reportserver;

import com.google.inject.OutOfScopeException;

/**
 * 
 *
 */
public interface ReportServerService {
	public static final String CONFIG_FILE = "main/main.cf";
	public static final String DEFAULT_CHARSET = "UTF-8";
	
	/**
	 * 
	 * @return
	 */
	public String getCharset();
	
	/**
	 * Provides information on the server. This information is only available if a user is
	 * logged in.
	 * 
	 * @return
	 * @throws OutOfScopeException
	 */
	public ServerInfoContainer getServerInfo();
}
