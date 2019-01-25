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
import java.util.List;

import net.datenwerke.gf.base.client.dtogenerator.RsDto;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.scheduler.client.scheduler.dto.JobExecutionStatusDto;
import net.datenwerke.scheduler.client.scheduler.dto.history.ExecutionLogEntryDto;

public class ReportScheduleJobInformation extends RsDto{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2710512228549048347L;

	private ReportScheduleDefinition scheduleDefinition;
	
	private List<Date> nextPlannedEntries;
	private List<ExecutionLogEntryDto> lastExecutedEntries;
	
	private int nrOfSuccessfulExecutions = 0;
	private int nrOfVetoedExecutions = 0;
	private int nrOfFailedExecutions = 0;
	
	private JobExecutionStatusDto executionStatus;
	
	private Long ownerId;
	
	public void setScheduleDefinition(ReportScheduleDefinition scheduleDefinition) {
		this.scheduleDefinition = scheduleDefinition;
	}

	public ReportScheduleDefinition getScheduleDefinition() {
		return scheduleDefinition;
	}

	public void setNextPlannedEntries(List<Date> nextPlannedEntries) {
		this.nextPlannedEntries = nextPlannedEntries;
	}

	public List<Date> getNextPlannedEntries() {
		return nextPlannedEntries;
	}

	public void setLastExecutedEntries(List<ExecutionLogEntryDto> lastExecutedEntries) {
		this.lastExecutedEntries = lastExecutedEntries;
	}

	public List<ExecutionLogEntryDto> getLastExecutedEntries() {
		return lastExecutedEntries;
	}

	public int getNrOfSuccessfulExecutions() {
		return nrOfSuccessfulExecutions;
	}

	public void setNrOfSuccessfulExecutions(int nrOfSuccessfulExecutions) {
		this.nrOfSuccessfulExecutions = nrOfSuccessfulExecutions;
	}

	public int getNrOfVetoedExecutions() {
		return nrOfVetoedExecutions;
	}

	public void setNrOfVetoedExecutions(int nrOfVetoedExecutions) {
		this.nrOfVetoedExecutions = nrOfVetoedExecutions;
	}

	public int getNrOfFailedExecutions() {
		return nrOfFailedExecutions;
	}

	public void setNrOfFailedExecutions(int nrOfFailedExecutions) {
		this.nrOfFailedExecutions = nrOfFailedExecutions;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setExecutionStatus(JobExecutionStatusDto executionStatus) {
		this.executionStatus = executionStatus;
	}

	public JobExecutionStatusDto getExecutionStatus() {
		return executionStatus;
	}

	
}
