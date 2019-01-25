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
 
 
package net.datenwerke.rs.core.service.reportmanager.engine;

import java.io.Serializable;

/**
 * 
 *
 */
public interface CompiledReport extends Serializable {

	/**
	 * Returns the actual report.
	 * @return
	 */
	public Object getReport();
	
	/**
	 * Returns the mime type of this report.
	 * @return
	 */
	public String getMimeType();
	
	/**
	 * Returns a file extension for files of this type
	 * @return
	 */
	public String getFileExtension();

	/**
	 * Returns true if this object contains reporting data.
	 * @return
	 */
	public boolean hasData();
	
	/**
	 * Returns true if the report is String based (in contrast to binary)
	 * @return
	 */
	public boolean isStringReport();
}
