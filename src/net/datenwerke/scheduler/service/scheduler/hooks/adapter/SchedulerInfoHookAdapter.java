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
 
 
package net.datenwerke.scheduler.service.scheduler.hooks.adapter;

import net.datenwerke.dtoservices.dtogenerator.annotations.GeneratedType;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractJob;
import net.datenwerke.scheduler.service.scheduler.entities.AbstractTrigger;
import net.datenwerke.scheduler.service.scheduler.hooks.SchedulerInfoHook;

/**
 * This file was automatically created by DtoAnnotationProcessor, version 0.1
 */
@GeneratedType("net.datenwerke.hookservices.HookAdapterProcessor")
public class SchedulerInfoHookAdapter implements SchedulerInfoHook {

	@Override
	public void beforeJobSchedule(AbstractJob job, AbstractTrigger trigger)  {
	}


	@Override
	public void jobScheduled(AbstractJob job, AbstractTrigger trigger)  {
	}


	@Override
	public void beforeJobRescheduled(AbstractJob job, AbstractTrigger trigger, AbstractJob previous)  {
	}


	@Override
	public void beforeJobUnschedule(AbstractJob job)  {
	}


	@Override
	public void jobRescheduled(AbstractJob job, AbstractTrigger trigger)  {
	}


	@Override
	public void jobUnscheduled(AbstractJob job)  {
	}


	@Override
	public void beforeJobRemove(AbstractJob job)  {
	}


	@Override
	public void jobRemoved(AbstractJob job)  {
	}



}
