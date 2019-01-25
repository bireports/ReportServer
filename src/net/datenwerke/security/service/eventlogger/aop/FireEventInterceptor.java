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
 
 
package net.datenwerke.security.service.eventlogger.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.datenwerke.rs.utils.eventbus.Event;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.rs.utils.eventlogger.eventbus.LoggedEvent;
import net.datenwerke.security.service.eventlogger.DwLoggedEvent;
import net.datenwerke.security.service.eventlogger.annotations.EventProperty;
import net.datenwerke.security.service.eventlogger.annotations.FireEvent;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class FireEventInterceptor implements MethodInterceptor {

	@Inject
	private EventBus eventBus;
	
	public Object invoke(MethodInvocation invocation) throws Throwable {
		/* get annotation */
		FireEvent fireEventAnno = invocation.getMethod().getAnnotation(FireEvent.class);
		if(null == fireEventAnno)
			throw new IllegalStateException("This should actually never happen."); //$NON-NLS-1$
		
		Class<? extends Event> eventType = fireEventAnno.event();
		Event event = eventType.newInstance();
		
		if(! LoggedEvent.class.isAssignableFrom(eventType))
			eventBus.fireEvent(event);
		else if(event instanceof DwLoggedEvent){
			Map<String, String> properties = getProperties(fireEventAnno, invocation);
			((DwLoggedEvent) event).addLoggedProperties(properties);
		}
		
		return invocation.proceed();
	}

	private Map<String, String> getProperties(FireEvent fireEventAnno, MethodInvocation invocation) {
		Map<String, String> properties = new HashMap<String, String>();
		
		for(EventProperty property : fireEventAnno.properties()){
			String key = property.key();
			String valueKey = property.valueKey();
			String value = property.value();
			
			if("".equals(value) && null == valueKey){
				if(invocation.getArguments().length > 0 && null != invocation.getArguments()[0])
					value = invocation.getArguments()[0].toString();
				else
					value = "NULL";
			} else if("".equals(value) && null != valueKey){
				Object arg = getArgumentByName(invocation, valueKey);
				if(null != arg)
					value = arg.toString();
				else
					value = "NULL";
			}
			
			properties.put(key, value);
		}
			
		return properties;
	}
	
	private Object getArgumentByName(MethodInvocation invocation, String parameterName) {
		Method method = invocation.getMethod();
		
		Annotation[][] allParamAnnotations = method.getParameterAnnotations();
		for(int i = 0; i < allParamAnnotations.length; i++){
			List<Annotation> paramAnnotations = Arrays.asList(method.getParameterAnnotations()[i]);
			for(Annotation paramAnno : paramAnnotations){
				if(paramAnno.annotationType().equals(Named.class)){
					Named namedAnnotation = (Named) paramAnno;
					
					if(parameterName.equals(namedAnnotation.value()))
						return invocation.getArguments()[i];
				}
			}
		}
		throw new IllegalArgumentException("Could not find named Parameter: " + parameterName + " on method " + method.getName() + ". Are you missing an @Named annotation?"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}
	


	
}
