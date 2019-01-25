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
 
 
package net.datenwerke.rs.utils.daemon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DwDaemonWatchdog implements Runnable {
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private boolean shutdown = false;

	private DwDaemonService daemonService;
	
	protected int sleepTime = 1000*60;

	public void setDaemonService(DwDaemonService daemonService) {
		this.daemonService = daemonService;
	}
	
	public DwDaemonService getDaemonService() {
		return daemonService;
	}
	
	@Override
	public void run() {
		while(! shutdown){
			checkDaemonState();
			
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
			}
		}
	}

	private void checkDaemonState() {
		try{
			if(! daemonService.isActive() && ! daemonService.isOrderdShutdown()){
				daemonService.start();
				
				logger.warn( "Restarted DwDaemon " + daemonService.getClass().getName());
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void shutdown(){
		synchronized (DwDaemonWatchdog.class) {
			shutdown = true;			
		}
	}

	public boolean isShutdown() {
		synchronized (DwDaemonWatchdog.class) {
			return false;
		}
	}

	public String getName() {
		return "dwdaemon-watchdog-" + daemonService.getClass().getSimpleName();
	}
}
