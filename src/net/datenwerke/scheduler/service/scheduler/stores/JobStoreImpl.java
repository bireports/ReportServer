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
 
 
package net.datenwerke.scheduler.service.scheduler.stores;

import java.util.Collection;

import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractTrigger;
import net.datenwerke.scheduler.service.scheduler.entities.JobExecutionStatus;

import com.google.inject.Inject;
import com.google.inject.Injector;

public abstract class JobStoreImpl implements JobStore {

	@Inject protected Injector injector;
	
	@Override
	public void scheduleJob(AbstractJob job, AbstractTrigger trigger) {
		job.setTrigger(trigger);
		
		/* notify job */
		job.hasBeenScheduled();
		
		/* init trigger */
		if(! trigger.isInitialized())
			trigger.initialize();
		
		if(null == trigger.getNextScheduledFireTime())
			throw new IllegalArgumentException("Trigger will never fire"); 
	}
	
	@Override
	public JobExecutionCompanion getExecutionCompanion() {
		return injector.getInstance(JobExecutionCompanionImpl.class);
	}
	
	@Override
	public void initJob(AbstractJob job) {
		if(null != job)
			injector.injectMembers(job);
	}
	
	@Override
	public void initJobs(Collection<AbstractJob> jobs) {
		for(AbstractJob job : jobs)
			initJob(job);
	}
	
	@Override
	public void clearErrorState(AbstractJob job) {
		job.setExecutionStatus(JobExecutionStatus.INACTIVE);
	}
	
	@Override
	public void merge(AbstractJob job) {
	}

	
}
