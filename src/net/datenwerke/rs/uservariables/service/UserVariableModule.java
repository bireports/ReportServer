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
 
 
package net.datenwerke.rs.uservariables.service;

import java.util.HashSet;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Scopes;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;
import net.datenwerke.rs.uservariables.service.genrights.ClientUserVariableModule;
import net.datenwerke.rs.uservariables.service.parameters.UserVariableParameterInstance;
import net.datenwerke.rs.uservariables.service.uservariables.UserVariableService;
import net.datenwerke.rs.uservariables.service.uservariables.UserVariableServiceImpl;
import net.datenwerke.rs.uservariables.service.uservariables.annotations.UserVariableTypes;
import net.datenwerke.rs.uservariables.service.uservariables.entities.UserVariableDefinition;
import net.datenwerke.rs.uservariables.service.uservariables.hooks.UserVariableProviderHook;

public class UserVariableModule extends AbstractReportServerModule {

	@Override
	protected void configure() {
		/* bind service */
		bind(UserVariableService.class).to(UserVariableServiceImpl.class).in(Scopes.SINGLETON);
		
		/* bind startup */
		bind(UserVariableStartup.class).asEagerSingleton();
		
		install(new ClientUserVariableModule());
		
		requestStaticInjection(			// should not be here
			UserVariableParameterInstance.class
		);
	}

	
	@Provides @UserVariableTypes @Inject
	public Set<UserVariableDefinition> provideUserVariableTypes(HookHandlerService hookHandler){
		Set<UserVariableDefinition> definitions = new HashSet<UserVariableDefinition>();
		
		for(UserVariableProviderHook hooker : hookHandler.getHookers(UserVariableProviderHook.class))
			definitions.addAll(hooker.getVariables());
		
		return definitions;
	}
}
