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
 
 
package net.datenwerke.gf;

import javax.servlet.ServletContextEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import net.datenwerke.gf.service.lifecycle.LifecycleBindingListener;
import net.datenwerke.gf.service.lifecycle.events.InitSessionEvent;
import net.datenwerke.gf.service.lifecycle.events.ShutdownEvent;
import net.datenwerke.gf.service.lifecycle.events.StartupEvent;
import net.datenwerke.gf.service.lifecycle.hooks.ConfigDoneHook;
import net.datenwerke.gf.service.lifecycle.hooks.ContextHook;
import net.datenwerke.gf.service.lifecycle.hooks.SessionHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.utils.eventbus.EventBus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.servlet.GuiceServletContextListener;


public abstract class DwGwtFrameworkBase extends GuiceServletContextListener implements HttpSessionListener {

	public static final String BASE_URL = "/reportserver/"; //$NON-NLS-1$
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());
	
	@Inject
	protected HookHandlerService hookHandler;
	
	@Inject
	protected EventBus eventBus;
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		/* make sure listener is listening for proper closing events */
		event.getSession().setAttribute("__xx_dw_gwt_fr_sessionListener", new LifecycleBindingListener(hookHandler, eventBus));
		
		eventBus.fireEvent(new InitSessionEvent());
		
		for(SessionHook hook : hookHandler.getHookers(SessionHook.class))
			hook.sessionCreated(event);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		// event in lifecycle listener
		
		for(SessionHook hook : hookHandler.getHookers(SessionHook.class))
			hook.sessionDestroyed(event);
	}
	
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		super.contextInitialized(servletContextEvent);
		
		try{
			String systemUser = "";
			try{
				systemUser = System.getProperty("user.name");
			}catch(Exception e){}
			if(null != eventBus)
				eventBus.fireEvent(new StartupEvent("system_user", systemUser));
			
			if(null != hookHandler)
				for(ContextHook hook : hookHandler.getHookers(ContextHook.class))
					hook.contextInitialized(servletContextEvent);
		} catch (Exception e) {
			logger.info( "context initialization error", e);
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		try{
			if(null == eventBus){
				logger.debug("context destroy before init");
				return;
			}
			
			String systemUser = "";
			try{
				systemUser = System.getProperty("user.name");
			}catch(Exception e){}
			eventBus.fireEvent(new ShutdownEvent("system_user", systemUser));
			
			if(null != hookHandler)
				for(ContextHook hook : hookHandler.getHookers(ContextHook.class))
					hook.contextDestroyed(servletContextEvent);
		} catch (Exception e) {
			logger.info( "error on context destroy", e);
		} finally {
			super.contextDestroyed(servletContextEvent);
		}
	}
	
	protected void injectorInitialized(){
		for(ConfigDoneHook hook : hookHandler.getHookers(ConfigDoneHook.class))
			hook.configDone();
	}
}
