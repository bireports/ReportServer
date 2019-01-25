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
 
 
package net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto;

import java.util.Date;

import net.datenwerke.gf.base.client.dtogenerator.RsDto;

public class ReportScheduleJobListInformation extends RsDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2673814530254653060L;

	private Long reportId;
	private String reportName;
	private String reportDescription;
	private Date lastScheduled;
	private Date nextScheduled;
	private String ownerFirstName;
	private String ownerLastName;
	
	private Long jobId;
	private boolean deleted;
	
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportDescription() {
		return reportDescription;
	}

	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}

	public Date getLastScheduled() {
		return lastScheduled;
	}

	public void setLastScheduled(Date lastScheduled) {
		this.lastScheduled = lastScheduled;
	}

	public Date getNextScheduled() {
		return nextScheduled;
	}

	public void setNextScheduled(Date nextScheduled) {
		this.nextScheduled = nextScheduled;
	}

	public String getOwnerFirstName() {
		return ownerFirstName;
	}

	public void setOwnerFirstName(String ownerFirstName) {
		this.ownerFirstName = ownerFirstName;
	}

	public String getOwnerLastName() {
		return ownerLastName;
	}

	public void setOwnerLastName(String ownerLastName) {
		this.ownerLastName = ownerLastName;
	}

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getOwnerName(){
		StringBuffer name = new StringBuffer();
		
		if(null != getOwnerFirstName())
			name.append(getOwnerFirstName()).append(" ");
		
		if(null != getOwnerLastName())
			name.append(getOwnerLastName());
		
		return name.toString();
	}
}
