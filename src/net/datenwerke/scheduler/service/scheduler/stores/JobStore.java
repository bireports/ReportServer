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
import java.util.Date;
import java.util.List;

import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractTrigger;
import net.datenwerke.scheduler.service.scheduler.stores.jpa.filter.JobFilterConfiguration;
import net.datenwerke.scheduler.service.scheduler.stores.jpa.filter.JobFilterCriteria;

public interface JobStore {

	public void scheduleJob(AbstractJob job, AbstractTrigger trigger);
	
	public Collection<AbstractJob> getActiveJobs();
	
	public Collection<AbstractJob> getAllJobs();
	
	public Collection<AbstractJob> getMisfiredJobs();
	
	public Collection<AbstractJob> getMisfiredJobs(Date before);
	
	public Collection<AbstractJob> getExecutingJobs();

	public Collection<AbstractJob> getWaitingJobs();
	
	public void unschedule(AbstractJob job);
	
	public void remove(AbstractJob job); 
	
	public JobExecutionCompanion getExecutionCompanion();

	void initJob(AbstractJob job);

	void initJobs(Collection<AbstractJob> jobs);

	public AbstractJob getJobById(long id);

	public void merge(AbstractJob job);

	List<AbstractJob> getJobsBy(JobFilterConfiguration filterConfig, JobFilterCriteria... addConfigs);

	public void clearErrorState(AbstractJob job);


}
