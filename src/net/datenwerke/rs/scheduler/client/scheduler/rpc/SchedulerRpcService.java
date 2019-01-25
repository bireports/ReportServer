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
 
 
package net.datenwerke.rs.scheduler.client.scheduler.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobInformation;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobListInformation;
import net.datenwerke.scheduler.client.scheduler.dto.filter.JobFilterConfigurationDto;
import net.datenwerke.scheduler.client.scheduler.dto.filter.JobFilterCriteriaDto;
import net.datenwerke.scheduler.client.scheduler.dto.history.ExecutionLogEntryDto;

@RemoteServiceRelativePath("scheduler")
public interface SchedulerRpcService extends RemoteService{

	public void schedule(ReportScheduleDefinition scheduleDTO) throws ServerCallFailedException;

	public boolean unschedule(Long jobId) throws ServerCallFailedException;

	public ReportDto getReportFor(Long jobId) throws ServerCallFailedException;
	
	PagingLoadResult<ReportScheduleJobListInformation> getReportJobList(
			JobFilterConfigurationDto jobFilterConfig, List<JobFilterCriteriaDto> addCriterions) throws ServerCallFailedException;

	ReportScheduleJobInformation loadFullScheduleInformation(ReportScheduleJobListInformation selected) throws ServerCallFailedException;

	void reschedule(Long jobId, ReportScheduleDefinition scheduleDTO) throws ServerCallFailedException;

	ReportScheduleJobInformation loadDetailsFor(ReportScheduleJobListInformation selected) throws ServerCallFailedException;
	
	ExecutionLogEntryDto loadFullDetailsFor(Long jobId, ExecutionLogEntryDto entry) throws ServerCallFailedException;
	
	void scheduleOnce(Long jobId) throws ServerCallFailedException;

	boolean remove(Long jobId) throws ServerCallFailedException;

	void clearErrorStatus(Long jobId) throws ServerCallFailedException;
	
	List<ReportScheduleJobListInformation> getReportJobList(ReportDto report);
}