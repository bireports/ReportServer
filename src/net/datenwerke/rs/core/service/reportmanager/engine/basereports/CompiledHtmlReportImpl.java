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
 
 
package net.datenwerke.rs.core.service.reportmanager.engine.basereports;

public class CompiledHtmlReportImpl implements CompiledHtmlReport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7954630970640096393L;
	
	private final String report;
	
	public CompiledHtmlReportImpl(String report) {
		this.report = report;
	}

	@Override
	public String getMimeType() {
		return "text/html";
	}

	@Override
	public String getFileExtension() {
		return "html";
	}

	@Override
	public String getReport() {
		return report;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((report == null) ? 0 : report.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CompiledHtmlReportImpl)) {
			return false;
		}
		CompiledHtmlReportImpl other = (CompiledHtmlReportImpl) obj;
		if (report == null) {
			if (other.report != null) {
				return false;
			}
		} else if (!report.equals(other.report)) {
			return false;
		}
		return true;
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
