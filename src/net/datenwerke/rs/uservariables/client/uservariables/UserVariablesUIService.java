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
 
 
package net.datenwerke.rs.uservariables.client.uservariables;

import java.util.Collection;

import net.datenwerke.gxtdto.client.stores.LoadableListStore;
import net.datenwerke.rs.uservariables.client.uservariables.dto.UserVariableDefinitionDto;
import net.datenwerke.rs.uservariables.client.uservariables.dto.UserVariableInstanceDto;

import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoader;

public interface UserVariablesUIService {

	public Collection<UserVariableConfigurator> getAllVariableConfigurators();

	public UserVariableConfigurator getConfigurator(UserVariableInstanceDto instance);
	
	public UserVariableConfigurator getConfigurator(UserVariableDefinitionDto definition);

	public ListLoader<ListLoadConfig, ListLoadResult<UserVariableDefinitionDto>> getDefinedVariableDefinitionsLoader();
	
	public LoadableListStore<ListLoadConfig, UserVariableDefinitionDto, ListLoadResult<UserVariableDefinitionDto>> getDefinedVariableDefinitionsStore();
}
