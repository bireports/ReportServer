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
 
 
package net.datenwerke.scheduler.service.scheduler;

import java.util.List;

import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobListInformation;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractTrigger;
import net.datenwerke.scheduler.service.scheduler.exceptions.SchedulerStartupException;
import net.datenwerke.scheduler.service.scheduler.stores.JobStore;
import net.datenwerke.scheduler.service.scheduler.stores.jpa.filter.JobFilterConfiguration;

public interface SchedulerService {

	
	public void start() throws SchedulerStartupException;

	public boolean isActive();
	public boolean isShutdown();
	public void shutdown();
	public List<Runnable> shutdownNow();
	boolean isTerminated();
	
	public void enable();
	public void disable();
	public boolean isEnabled();

	JobStore getJobStore();

	public void schedule(AbstractJob job, AbstractTrigger trigger);
	public void unschedule(AbstractJob job);
	public void remove(AbstractJob job);

	public AbstractJob getJobById(Long object);

	public void schedule(AbstractJob job, AbstractTrigger trigger, AbstractJob previous);

	public void merge(AbstractJob job);

	public SchedulerDaemon getDeamon();
	
	public List<AbstractJob> getJobsBy(JobFilterConfiguration filterConfig);

	void forceRemove(AbstractJob job);

	boolean isWatchdogActive();

	void restart() throws SchedulerStartupException;

	void shutdownWatchdog();

	public boolean isOrderdShutdown();

	void clearErrorState(AbstractJob job);

	void restartWatchdog();

	void startWatchdog();

	List<ReportScheduleJobListInformation> getReportJobList(Report report);
	
}
