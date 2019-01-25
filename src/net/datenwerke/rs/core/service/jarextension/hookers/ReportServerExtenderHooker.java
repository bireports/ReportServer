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
 
 
package net.datenwerke.rs.core.service.jarextension.hookers;

import java.util.Iterator;
import java.util.ServiceLoader;

import net.datenwerke.gf.service.lateinit.LateInitHook;
import net.datenwerke.rs.core.service.jarextension.ReportServerExtender;
import net.datenwerke.rs.core.service.jarextension.events.ReoportServerExtenderLoadFailedEvent;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.rs.utils.exception.ExceptionServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

public class ReportServerExtenderHooker implements LateInitHook {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	private final Provider<Injector> injectorProvider;
	private final Provider<EventBus> eventBusProvider;
	private final Provider<ExceptionServices> exceptionServiceProvider;

	@Inject
	public ReportServerExtenderHooker(
		Provider<Injector> injectorProvider,
		Provider<EventBus> eventBusProvider,
		Provider<ExceptionServices> exceptionServiceProvider){
		this.injectorProvider = injectorProvider;
		this.eventBusProvider = eventBusProvider;
		this.exceptionServiceProvider = exceptionServiceProvider;
	}
	
	@Override
	public void initialize() {
		logger.info( "Start loading ReportServer extensions.");
		
		ServiceLoader<ReportServerExtender> serviceLoader = ServiceLoader.load(ReportServerExtender.class);
		Iterator<ReportServerExtender> iterator = serviceLoader.iterator();
		
		Injector injector = injectorProvider.get();
		
		while(iterator.hasNext()){
			try{
				ReportServerExtender next = iterator.next();
				
				logger.info( "Start loading ReportServer extension: " + next.getClass().getName());
				
				try{
					injector.injectMembers(next);
					next.extend();
				}catch(Exception e){
					logger.warn( e.getMessage(), e);
					eventBusProvider.get().fireEvent(new ReoportServerExtenderLoadFailedEvent(next.getClass().getName(), exceptionServiceProvider.get().exceptionToString(e)));
				}
			} catch(Exception e){
				logger.warn( e.getMessage(), e);
				eventBusProvider.get().fireEvent(new ReoportServerExtenderLoadFailedEvent(exceptionServiceProvider.get().exceptionToString(e)));
			}
		}
	}

}
