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

import net.datenwerke.scheduler.service.scheduler.entities.AbstractAction;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.entities.history.ActionEntry;
import net.datenwerke.scheduler.service.scheduler.entities.history.ExecutionLogEntry;
import net.datenwerke.scheduler.service.scheduler.entities.history.JobEntry;
import net.datenwerke.scheduler.service.scheduler.tasks.SchedulerTask;

public interface JobExecutionCompanion {

	public AbstractJob initJobForExecution(AbstractJob job);

	public AbstractJob beginSchedulerTask(AbstractJob job);
	
	public void endSchedulerTask(AbstractJob job, ExecutionLogEntry logEntry, boolean badFailure);

	public void updateStateAfterJobExecution(AbstractJob job, ExecutionLogEntry entry, JobEntry jobEntry, boolean success);

	public ExecutionLogEntry initHistoryEntry(AbstractJob job);

	public void updateStateAfterActionExecution(AbstractJob job, ExecutionLogEntry entry,
			ActionEntry actionEntry, boolean success);

	public AbstractAction initActionForExecution(AbstractAction action);

	public void beginInnerTransaction(SchedulerTask schedulerTask);
	
	void finishInnerTransaction(SchedulerTask schedulerTask, boolean success);

	public void failExecutingJob(AbstractJob job);

	public void resetWaitingJob(AbstractJob job);

	public void flush();

	public ActionEntry initActionEntry(ExecutionLogEntry logEntry);

	public JobEntry initJobEntry(ExecutionLogEntry entry);
	
}
