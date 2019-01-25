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
 
 
package net.datenwerke.rs.scheduler.service.scheduler.eventhandler;

import java.util.Iterator;
import java.util.List;

import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.filter.ReportServerJobFilter;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.rs.utils.exception.exceptions.NeedForcefulDeleteException;
import net.datenwerke.scheduler.service.scheduler.SchedulerService;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;

import com.google.inject.Inject;

public class HandleReportRemoveEventHandler implements
		EventHandler<RemoveEntityEvent> {

	@Inject private SchedulerService scheduler;
	
	@Override
	public void handle(RemoveEntityEvent event) {
		Report report = (Report) event.getObject();
		
		ReportServerJobFilter filter = new ReportServerJobFilter();
		filter.setJobType(ReportExecuteJob.class);
		filter.setActive(true);
		filter.setInActive(false);
		filter.setAnyUser();
		filter.addReport(report);
		
		List<AbstractJob> jobs = scheduler.getJobsBy(filter);
		if(null != jobs && ! jobs.isEmpty()){
			Iterator<AbstractJob> it = jobs.iterator();
			StringBuilder error = new StringBuilder("Report " + report.getId() + " is scheduled. Job Ids: " + it.next().getId());
			while(it.hasNext())
				error.append(", ").append(it.next().getId());
			
			throw new NeedForcefulDeleteException(error.toString());
		}
		
		/* take care of inactive jobs */
		filter.setActive(false);
		filter.setInActive(true);
		jobs = scheduler.getJobsBy(filter);
		if(null != jobs && ! jobs.isEmpty()){
			for(AbstractJob job : jobs){
				((ReportExecuteJob)job).setReport(null);
				scheduler.unschedule(job);
			}
		}
	}

}
