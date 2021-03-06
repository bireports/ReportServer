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
 
 
package net.datenwerke.rs.core.service.reportmanager.engine.basereports;

import net.datenwerke.rs.core.service.reportmanager.engine.CompiledReport;

/**
 * 
 *
 */
public class CompiledJsonReport implements CompiledReport{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 485512409594043368L;
	
	final private String report;
	
	public CompiledJsonReport(String report) {
		this.report = report;
	}

	public String getReport() {
		return report;
	}

	public String getFileExtension() {
		return "json"; //$NON-NLS-1$
	}

	public String getMimeType() {
		return "application/json"; //$NON-NLS-1$
	}

	@Override
	public boolean hasData() {
		return report != null;
	}

	@Override
	public boolean isStringReport() {
		return true;
	}
	
}
