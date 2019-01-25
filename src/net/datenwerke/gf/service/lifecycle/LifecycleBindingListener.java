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
 
 
package net.datenwerke.gf.service.lifecycle;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import net.datenwerke.gf.service.lifecycle.events.InvalidateSessionEvent;
import net.datenwerke.gf.service.lifecycle.hooks.SessionHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.utils.eventbus.EventBus;

public class LifecycleBindingListener implements HttpSessionBindingListener {

	private final HookHandlerService hookHandler;
	private final EventBus eventBus;
	
	public LifecycleBindingListener(HookHandlerService hookHandler, EventBus eventBus) {
		this.hookHandler = hookHandler;
		this.eventBus = eventBus;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		for(SessionHook hook : hookHandler.getHookers(SessionHook.class))
			hook.beforeSessionInvalidate(event);
		
		eventBus.fireEvent(new InvalidateSessionEvent());
	}

}
