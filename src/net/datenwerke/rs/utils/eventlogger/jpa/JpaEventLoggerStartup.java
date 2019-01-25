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
 
 
package net.datenwerke.rs.utils.eventlogger.jpa;

import javax.persistence.EntityManager;

import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.rs.utils.eventlogger.EventLoggerEventHandler;
import net.datenwerke.rs.utils.eventlogger.EventLoggerService;
import net.datenwerke.rs.utils.eventlogger.eventbus.LoggedEvent;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class JpaEventLoggerStartup {

	@Inject
	public JpaEventLoggerStartup(
		EventBus eventBus,
		final EventLoggerService eventLoggerService,
		final Injector injector,
		
		EventLoggerEventHandler eventHandler
		){
		
		eventBus.attachEventHandler(LoggedEvent.class, eventHandler);
		
		Thread sStarter = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					try{
						injector.getInstance(EntityManager.class);
						break;
					} catch(Exception e){
						
					}
				}
				eventLoggerService.start();
			}
		});
		sStarter.setDaemon(true);
		sStarter.start();
	}
}
