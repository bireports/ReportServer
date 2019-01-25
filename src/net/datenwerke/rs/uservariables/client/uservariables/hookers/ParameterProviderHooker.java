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
 
 
package net.datenwerke.rs.uservariables.client.uservariables.hookers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.datenwerke.rs.core.client.parameters.config.ParameterConfigurator;
import net.datenwerke.rs.core.client.parameters.hooks.ParameterProviderHook;
import net.datenwerke.rs.uservariables.client.parameters.UserVariableParameterConfigurator;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ParameterProviderHooker implements ParameterProviderHook {

	@Inject Provider<UserVariableParameterConfigurator> parameter;
	
	@Override
	public Collection<ParameterConfigurator> parameterProviderHook_getConfigurators() {
		List<ParameterConfigurator> list = new ArrayList<ParameterConfigurator>();
		
		list.add(parameter.get());
		
		return list;
	}

}
