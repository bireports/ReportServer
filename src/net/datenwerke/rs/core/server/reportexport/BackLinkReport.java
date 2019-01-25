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
 
 
package net.datenwerke.rs.core.server.reportexport;

import java.io.Serializable;
import java.util.UUID;

import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;

public class BackLinkReport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6423821722701693015L;
	
	private String id;
	private long reportId;
	private Report adjustedReport;
	private String outputFormat;
	
	private String predecessor;

	public BackLinkReport(long reportId, Report adjustedReport,String outputFormat) {
		this.reportId = reportId;
		this.adjustedReport = adjustedReport;
		this.outputFormat = outputFormat;
		this.id = UUID.randomUUID().toString().substring(0,8);
	}

	public String getId() {
		return id;
	}

	public long getReportId() {
		return reportId;
	}

	public Report getAdjustedReport() {
		return adjustedReport;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public void setPredecessor(String predecessor) {
		this.predecessor = predecessor;
	}

	public String getPredecessor() {
		return predecessor;
	}
}