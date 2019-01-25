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
 
 
package net.datenwerke.rs.uservariables.service.uservariables.hookers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.datenwerke.rs.uservariables.service.uservariables.entities.UserVariableDefinition;
import net.datenwerke.rs.uservariables.service.uservariables.hooks.UserVariableProviderHook;
import net.datenwerke.rs.uservariables.service.variabletypes.list.ListUserVariableDefinition;
import net.datenwerke.rs.uservariables.service.variabletypes.string.StringUserVariableDefinition;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BaseUserVariableProvider implements UserVariableProviderHook {

	@Inject private Provider<StringUserVariableDefinition> stringProvider;
	@Inject private Provider<ListUserVariableDefinition> listProvider;
	
	@Override
	public Collection<? extends UserVariableDefinition> getVariables() {
		List<UserVariableDefinition> list = new ArrayList<UserVariableDefinition>();
		
		list.add(stringProvider.get());
		list.add(listProvider.get());
		
		return list;
	}

}
