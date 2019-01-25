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
 
 
package net.datenwerke.rs.eximport.service.eximport.im.http;

import java.io.Serializable;

import net.datenwerke.eximport.ExportDataProvider;
import net.datenwerke.eximport.im.ImportConfig;

public class HttpImportConfiguration implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7043340618318521368L;

	private ExportDataProvider importData;
	private ImportConfig importConfig;
	
	
	public ExportDataProvider getImportData() {
		return importData;
	}

	public void setImportData(ExportDataProvider importData) {
		this.importData = importData;
	}

	public void setImportConfig(ImportConfig importConfig) {
		this.importConfig = importConfig;
	}

	public ImportConfig getImportConfig() {
		return importConfig;
	}




}
