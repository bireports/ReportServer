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
 
 
package net.datenwerke.scheduler.service.scheduler.hooks;

import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.hookservices.annotations.HookConfig;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractAction;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.entities.history.ActionEntry;
import net.datenwerke.scheduler.service.scheduler.entities.history.ExecutionLogEntry;
import net.datenwerke.scheduler.service.scheduler.entities.history.JobEntry;
import net.datenwerke.scheduler.service.scheduler.helper.VetoJobExecution;

@HookConfig
public interface SchedulerExecutionHook extends Hook {

	/**
	 * happens outside a transaction ..  
	 * 
	 * @param job
	 * @param logEntry
	 */
	void jobExecutionAboutToStart(AbstractJob job, ExecutionLogEntry logEntry);

	/**
	 * happens outside a transaction ..  
	 * 
	 * @param job
	 * @param logEntry
	 */
	void actionExecutionAboutToStart(AbstractJob job, ExecutionLogEntry logEntry);

	/**
	 * wrapped in transaction
	 * 
	 * @param job
	 * @param logEntry
	 */
	void executionEndedSuccessfully(AbstractJob job, ExecutionLogEntry logEntry);

	/**
	 * wrapped in transaction
	 * 
	 * @param job
	 * @param logEntry
	 * @param e
	 */
	void executionEndedAbnormally(AbstractJob job, ExecutionLogEntry logEntry, Exception e);

	/**
	 * happens outside a transaction ..
	 *   
	 * @param job
	 * @param logEntry
	 */
	VetoJobExecution doesVetoExecution(AbstractJob job, ExecutionLogEntry logEntry);

	/**
	 * wrapped in transaction
	 *   
	 * @param job
	 * @param logEntry
	 */
	void informAboutVeto(AbstractJob job, ExecutionLogEntry logEntry,
			VetoJobExecution veto);

	/**
	 * within transaction
	 * 
	 * @param job
	 * @param logEntry
	 * @param e
	 */
	void actionExecutionEndedAbnormally(AbstractJob job, AbstractAction action,
			ActionEntry actionEntry, Exception e);

	/**
	 * within transaction
	 * 
	 * @param job
	 * @param logEntry
	 * @param e
	 */
	void jobExecutionEndedAbnormally(AbstractJob job, JobEntry jobEntry, Exception e);

	/**
	 * wrapped in transaction
	 * 
	 * @param job
	 * @param action
	 * @param logEntry
	 */
	void actionExecutionEndedSuccessfully(AbstractJob job, AbstractAction action, ExecutionLogEntry logEntry);


}
