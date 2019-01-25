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
 
 
package net.datenwerke.security.service.eventlogger.aop;

import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.security.service.eventlogger.jpa.AfterRemoveEntityEvent;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Inject;

public class RemoveEntityEventInterceptor implements MethodInterceptor {

	@Inject
	private EventBus eventBus;
	
	@Override
	public Object invoke(MethodInvocation method) throws Throwable {
		if(1 != method.getArguments().length)
			throw new IllegalArgumentException("Excepted exactly one argument");
		
		Object entity = method.getArguments()[0];

		eventBus.fireEvent(new RemoveEntityEvent(entity));
		
		Object returnValue = method.proceed();
		
		eventBus.fireEvent(new AfterRemoveEntityEvent(entity));
		
		return returnValue;
	}
}
