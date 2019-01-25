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
 
 
package net.datenwerke.scheduler.service.scheduler.hooks.adapter;

import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractAction;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.entities.history.ActionEntry;
import net.datenwerke.scheduler.service.scheduler.entities.history.ExecutionLogEntry;
import net.datenwerke.scheduler.service.scheduler.entities.history.JobEntry;
import net.datenwerke.scheduler.service.scheduler.helper.VetoJobExecution;
import net.datenwerke.scheduler.service.scheduler.hooks.SchedulerExecutionHook;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.hookservices.HookAdapterProcessor")
public class SchedulerExecutionHookAdapter implements SchedulerExecutionHook {

	@Override
	public void jobExecutionAboutToStart(AbstractJob job, ExecutionLogEntry logEntry)  {
	}


	@Override
	public void actionExecutionAboutToStart(AbstractJob job, ExecutionLogEntry logEntry)  {
	}


	@Override
	public void executionEndedSuccessfully(AbstractJob job, ExecutionLogEntry logEntry)  {
	}


	@Override
	public void executionEndedAbnormally(AbstractJob job, ExecutionLogEntry logEntry, Exception e)  {
	}


	@Override
	public VetoJobExecution doesVetoExecution(AbstractJob job, ExecutionLogEntry logEntry)  {
		return null;
	}


	@Override
	public void informAboutVeto(AbstractJob job, ExecutionLogEntry logEntry, VetoJobExecution veto)  {
	}


	@Override
	public void actionExecutionEndedAbnormally(AbstractJob job, AbstractAction action, ActionEntry actionEntry, Exception e)  {
	}


	@Override
	public void jobExecutionEndedAbnormally(AbstractJob job, JobEntry jobEntry, Exception e)  {
	}


	@Override
	public void actionExecutionEndedSuccessfully(AbstractJob job, AbstractAction action, ExecutionLogEntry logEntry)  {
	}



}
