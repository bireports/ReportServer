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

import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.scheduler.service.scheduler.events.SchedulerDaemonRessurectionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

public class SchedulerWatchdog implements Runnable {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private final SchedulerService schedulerService;
	private final EventBus eventBus;

	private boolean shutdown = false;

	
	@Inject
	public SchedulerWatchdog(
		EventBus eventBus,
		SchedulerService schedulerService
		) {
		this.eventBus = eventBus;
		this.schedulerService = schedulerService;
	}
	
	@Override
	public void run() {
		while(! shutdown){
			checkDaemonState();
			
			try {
				Thread.sleep(1000*60);
			} catch (InterruptedException e) {
			}
		}
	}

	private void checkDaemonState() {
		try{
			if(! schedulerService.isActive() && ! schedulerService.isOrderdShutdown()){
				eventBus.fireEvent(new SchedulerDaemonRessurectionEvent());
				
				schedulerService.start();
			}
		}catch (Exception e) {
			logger.warn( e.getMessage(), e);
		}
	}

	public void shutdown(){
		synchronized (SchedulerWatchdog.class) {
			shutdown = true;			
		}
	}

	public boolean isShutdown() {
		synchronized (SchedulerWatchdog.class) {
			return shutdown;
		}
	}
}
