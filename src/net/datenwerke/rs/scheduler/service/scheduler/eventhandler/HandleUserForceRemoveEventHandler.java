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
 
 
package net.datenwerke.rs.scheduler.service.scheduler.eventhandler;

import java.util.List;

import net.datenwerke.rs.scheduler.service.scheduler.jobs.filter.ReportServerJobFilter;
import net.datenwerke.rs.scheduler.service.scheduler.jobs.report.ReportExecuteJob;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.scheduler.service.scheduler.SchedulerService;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.security.service.eventlogger.jpa.ForceRemoveEntityEvent;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;

public class HandleUserForceRemoveEventHandler implements
		EventHandler<ForceRemoveEntityEvent> {

	private final SchedulerService schedulerService;
	
	@Inject
	public HandleUserForceRemoveEventHandler(SchedulerService schedulerService) {
		this.schedulerService = schedulerService;
	}

	@Override
	public void handle(ForceRemoveEntityEvent event) {
		User user = (User) event.getObject();
		
		ReportServerJobFilter filter = new ReportServerJobFilter();
		filter.setJobType(ReportExecuteJob.class);
		filter.setActive(true);
		filter.setInActive(false);
		filter.setFromUser(user);
		
		List<AbstractJob> jobs = schedulerService.getJobsBy(filter);
		if(null != jobs && ! jobs.isEmpty()){
			for(AbstractJob job : jobs){
				((ReportExecuteJob)job).setUser(null);
				schedulerService.unschedule(job);
			}
		}
	}

}
