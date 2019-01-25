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
 
 
package net.datenwerke.rs.core.service.reportmanager.parameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.VariableMapper;

import net.datenwerke.rs.core.service.parameters.entities.ParameterInstance;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.utils.juel.JuelService;
import net.datenwerke.rs.utils.misc.Nullable;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

/**
 * Groups parameters together.
 * 
 *
 */
public class ParameterSet implements Iterable<ParameterInstance> {

	@Inject 
	private JuelService juelService;
	
	@Inject
	private Injector injector;
	
	@Inject 
	private Provider<Collection<ParameterSetReplacementProvider>> replacementProviderProvider;
	
	private final List<ParameterInstance> parameters = new ArrayList<ParameterInstance>();
	private final User user;
	private final Report report;
	
	private final Map<String, Object> variableMap = new HashMap<String, Object>(); 
	
	public ParameterSet(
		@Nullable @Assisted User user
		){
		this.user = user;
		this.report = null;
	}
	
	public ParameterSet(
		AuthenticatorService authService){
		this.user = authService.getCurrentUser();
		this.report = null;
	}

	public ParameterSet(
		AuthenticatorService authService,
		@Nullable @Assisted User user, 
		@Nullable @Assisted Report report){
		this.user = null == user ? authService.getCurrentUser() : user;
		this.report = report;
	}
	
	public ParameterSet(
		AuthenticatorService authService,
		@Nullable @Assisted Report report) {
		this.user = authService.getCurrentUser();
		this.report = report;
	}

	public User getUser(){
		return user;
	}
	
	public Report getReport(){
		return report;
	}
	
	public void add(ParameterInstance instance){
		injector.injectMembers(instance);
		
		parameters.add(instance);
	}
	
	public int size(){
		return parameters.size();
	}
	
	public Iterator<ParameterInstance> iterator() {
		return parameters.iterator();
	}
	
	public Map<String, ParameterValue> getParameterMap(){
		Map<String,ParameterValue> parameterMap = new HashMap<String, ParameterValue>();
		
		for(ParameterSetReplacementProvider repProvider : replacementProviderProvider.get()){
			Map<String, ParameterValue> replacements = repProvider.provideReplacements(user, report);
			if(null != replacements)
				parameterMap.putAll(replacements);
		}

		for(Entry<String, Object> entry : variableMap.entrySet())
			if(null != entry.getValue())
				parameterMap.put(entry.getKey(), new ParameterValueImpl(entry.getKey(), entry.getValue(), entry.getValue().getClass()));
		
		for(ParameterInstance<?> p : this)
			p.configureParameterMap(user, parameterMap, this);
		
		return parameterMap;
	}
	
	public Map<String, Object> getParameterMapSimple(){
		Map<String,Object> parameterMap = new HashMap<String, Object>();
		
		for(Entry<String, ParameterValue> entry : getParameterMap().entrySet())
			parameterMap.put(entry.getKey(), entry.getValue().getValue());
		
		return parameterMap;
	}
	
	public List<ParameterInstance> getParameterList(){
		return parameters;
	}
	
	
	public Object evaluateExpression(String text){
		if(! juelService.isValidExpression(text))
			throw new IllegalArgumentException("Expression '" + text + "' is not a valid Juel expression.");
		
		ExpressionFactory factory = juelService.provideBasicExpressionFactory();
		ELContext context = juelService.provideBasicContext(factory);
		VariableMapper vm = context.getVariableMapper();
		
		for(ParameterSetReplacementProvider repProvider : replacementProviderProvider.get()){
			Map<String, ParameterValue> replacements = repProvider.provideReplacements(user, report);
			if(null != replacements)
				for(Entry<String, ParameterValue> entry : replacements.entrySet())
					vm.setVariable(entry.getKey(), factory.createValueExpression(entry.getValue().getValue(), entry.getValue().getType()));					
	
			repProvider.extendJuel(user, report, factory, context);
		}
		
		/* store variables */ 
		for(Entry<String, Object> entry : variableMap.entrySet())
			vm.setVariable(entry.getKey(), factory.createValueExpression(entry.getValue(), entry.getValue().getClass()));
		
		/* add parameters */
		for(ParameterInstance<?> p : this)
			p.configureEL(user, factory, context, this);
		
		return juelService.evaluate(factory, context, text);
	}
	
	public String parseExpression(String text){
		Object value = evaluateExpression(text);
		return String.valueOf(value);
	}
	
	public void addAll(Collection<ParameterInstance> parameters){
		for(ParameterInstance p : parameters)
			add(p);
	}
	
	public void add(ParameterSet parameterSet){
		for(ParameterInstance instance : parameterSet.getParameterList())
			add(instance);
		for(Entry<String, Object> entry : parameterSet.variableMap.entrySet())
			addVariable(entry.getKey(), entry.getValue());
	}
	
	public void addVariable(String key, Object value){
		variableMap.put(key, value);
	}
	
	
}
