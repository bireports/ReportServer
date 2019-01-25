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

import java.util.Date;

import net.datenwerke.scheduler.service.scheduler.entities.AbstractAction;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.entities.JobExecutionStatus;
import net.datenwerke.scheduler.service.scheduler.entities.Outcome;
import net.datenwerke.scheduler.service.scheduler.entities.history.ActionEntry;
import net.datenwerke.scheduler.service.scheduler.entities.history.ExecutionLogEntry;
import net.datenwerke.scheduler.service.scheduler.entities.history.JobEntry;
import net.datenwerke.scheduler.service.scheduler.tasks.SchedulerTask;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class JobExecutionCompanionImpl implements JobExecutionCompanion {

	@Inject protected Injector injector;
	
	@Override
	public AbstractJob initJobForExecution(AbstractJob job){
		injector.injectMembers(job);
		injector.injectMembers(job.getTrigger());
		
		job.setExecutionStatus(JobExecutionStatus.WAITING);
		
		return job;
	}
	
	@Override
	public AbstractAction initActionForExecution(AbstractAction action) {
		injector.injectMembers(action);
		
		return action;
	}
	
	@Override
	public AbstractJob beginSchedulerTask(AbstractJob job) {
		job.setExecutionStatus(JobExecutionStatus.EXECUTING);
		
		injector.injectMembers(job);
		injector.injectMembers(job.getTrigger());
		
		return job;
	}
	
	@Override
	public void endSchedulerTask(AbstractJob job, ExecutionLogEntry logEntry, boolean badFailure) {
		if(badFailure)
			job.setExecutionStatus(JobExecutionStatus.BAD_FAILURE);
		else 
			job.setExecutionStatus(JobExecutionStatus.INACTIVE);
	}
	
	@Override
	public void failExecutingJob(AbstractJob job) {
		job.setExecutionStatus(JobExecutionStatus.INACTIVE);
		
		for(ExecutionLogEntry logEntry : job.getHistory().getExecutionLogEntries()){
			if(Outcome.EXECUTING.equals(logEntry.getOutcome())){
				JobEntry jobEntry = initJobEntry(logEntry);
				jobEntry.setOutcome(Outcome.FAILURE);
				jobEntry.setErrorDescription("Server restarted while job was still running");
				job.adjustJobEntryForFailure(jobEntry);
				
				logEntry.setJobEntry(jobEntry);
				logEntry.setOutcome(Outcome.FAILURE);
				logEntry.setEnd(new Date());
				logEntry.setBadErrorDescription("Server restarted while job was still running");
			}
		}
		
		/* only restart jobs that were not deleted/archived since their last start */
		if(null != job.getTrigger().getNextScheduledFireTime()){
			job.getTrigger().setExecuteOnce(true);
		}else{
			job.getTrigger().setExecuteOnce(false);
		}
	}
	
	@Override
	public void resetWaitingJob(AbstractJob job) {
		job.setExecutionStatus(JobExecutionStatus.INACTIVE);
	}

	@Override
	public ExecutionLogEntry initHistoryEntry(AbstractJob job) {
		return null;
	}

	@Override
	public void updateStateAfterJobExecution(AbstractJob job, ExecutionLogEntry entry,
			JobEntry jobEntry, boolean success) {
		job.setExecutionStatus(JobExecutionStatus.EXECUTING_ACTIONS);
	}
	
	@Override
	public ActionEntry initActionEntry(ExecutionLogEntry logEntry) {
		ActionEntry entry = new ActionEntry();
		logEntry.addActionEntry(entry);
		
		return entry;
	}

	@Override
	public JobEntry initJobEntry(ExecutionLogEntry logEntry) {
		JobEntry entry = new JobEntry();
		logEntry.setJobEntry(entry);
		
		return entry;
	}
	
	@Override
	public void updateStateAfterActionExecution(AbstractJob job, ExecutionLogEntry entry,
			ActionEntry actionEntry, boolean success) {
		
	}

	@Override
	public void beginInnerTransaction(SchedulerTask schedulerTask) {
		
	}
	
	@Override
	public void finishInnerTransaction(SchedulerTask schedulerTask, boolean success) {
		
	}

	@Override
	public void flush() {
		
	}
	
}
