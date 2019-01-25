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
 
 
package net.datenwerke.rs.uservariables.client.variabletypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.uservariables.client.uservariables.UserVariableConfigurator;
import net.datenwerke.rs.uservariables.client.uservariables.hooks.UserVariableProviderHook;
import net.datenwerke.rs.uservariables.client.variabletypes.list.ListConfigurator;
import net.datenwerke.rs.uservariables.client.variabletypes.string.StringConfigurator;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class RSUserVariablesUIStartup implements UserVariableProviderHook {

	private final Provider<StringConfigurator> stringConfigurator;
	private final Provider<ListConfigurator> listConfigurator;
	
	
	
	@Inject
	public RSUserVariablesUIStartup(
		HookHandlerService hookHandler,
		
		Provider<StringConfigurator> stringConfigurator,
		Provider<ListConfigurator> listConfigurator
		){
		
		this.stringConfigurator = stringConfigurator;
		this.listConfigurator = listConfigurator;
		
		hookHandler.attachHooker(UserVariableProviderHook.class, this);
	}

	public Collection<UserVariableConfigurator> userVariableProviderHook_getConfigurators() {
		List<UserVariableConfigurator> list = new ArrayList<UserVariableConfigurator>();
		
		list.add(stringConfigurator.get());
		list.add(listConfigurator.get());
		
		return list;
	}
}
