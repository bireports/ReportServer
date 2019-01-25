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

public class CompiledReportImpl implements CompiledReport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1427546313375018829L;
	
	private Object report;
	private String mimeType;
	private String fileExtension;
	private boolean data;
	private boolean stringReport;
	
	public CompiledReportImpl(Object report, String mimeType,
			String fileExtension, boolean data, boolean stringReport) {
		this.report = report;
		this.mimeType = mimeType;
		this.fileExtension = fileExtension;
		this.data = data;
		this.stringReport = stringReport;
	}

	@Override
	public Object getReport() {
		return report;
	}

	@Override
	public String getMimeType() {
		return mimeType;
	}

	@Override
	public String getFileExtension() {
		return fileExtension;
	}

	@Override
	public boolean hasData() {
		return data;
	}

	@Override
	public boolean isStringReport() {
		return stringReport;
	}

	public void setHasData(boolean hasData) {
		this.data = hasData;
	}


}
