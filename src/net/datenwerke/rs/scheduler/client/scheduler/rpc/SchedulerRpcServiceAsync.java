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

import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobInformation;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobListInformation;
import net.datenwerke.scheduler.client.scheduler.dto.filter.JobFilterConfigurationDto;
import net.datenwerke.scheduler.client.scheduler.dto.filter.JobFilterCriteriaDto;
import net.datenwerke.scheduler.client.scheduler.dto.history.ExecutionLogEntryDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public interface SchedulerRpcServiceAsync {

	void schedule(ReportScheduleDefinition scheduleDTO, AsyncCallback<Void> callback);


	void loadFullScheduleInformation(ReportScheduleJobListInformation selected,
			AsyncCallback<ReportScheduleJobInformation> callback);

	public void getReportJobList(
			JobFilterConfigurationDto jobFilterConfig,
			List<JobFilterCriteriaDto> addCriterions, AsyncCallback<PagingLoadResult<ReportScheduleJobListInformation>> callback);


	void unschedule(Long jobId, AsyncCallback<Boolean> callback);
	
	void remove(Long jobId, AsyncCallback<Boolean> callback);
	
	void clearErrorStatus(Long jobId, AsyncCallback<Void> callback);

	void getReportFor(Long jobId, AsyncCallback<ReportDto> callback);


	void reschedule(Long jobId, ReportScheduleDefinition scheduleDTO,
			AsyncCallback<Void> transformAndKeepCallback);


	void loadDetailsFor(ReportScheduleJobListInformation selected,
			AsyncCallback<ReportScheduleJobInformation> callback);


	void loadFullDetailsFor(Long jobId, ExecutionLogEntryDto entry,
			AsyncCallback<ExecutionLogEntryDto> callback);


	void scheduleOnce(Long jobId, AsyncCallback<Void> callback);
}
