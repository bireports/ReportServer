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
 
 
package net.datenwerke.rs.eximport.service.eximport.ex.http;

public class HttpExportServiceImpl implements HttpExportService {

	private String storedExport;
	private String name;
	
	@Override
	public void storeExport(String exportXML, String name) {
		this.storedExport = exportXML;
		this.name = name;
	}

	@Override
	public String getAndRemoveStoredExport() {
		String result = storedExport;
		storedExport = null;
		return result;
	}
	
	@Override
	public String getExportName() {
		return name;
	}
	
}
