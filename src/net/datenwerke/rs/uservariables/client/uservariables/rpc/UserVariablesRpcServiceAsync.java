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
 
 
package net.datenwerke.rs.uservariables.client.uservariables.rpc;

import java.util.Collection;
import java.util.List;

import net.datenwerke.rs.uservariables.client.uservariables.dto.UserVariableDefinitionDto;
import net.datenwerke.rs.uservariables.client.uservariables.dto.UserVariableInstanceDto;
import net.datenwerke.security.client.usermanager.dto.AbstractUserManagerNodeDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.ListLoadResult;

public interface UserVariablesRpcServiceAsync {

	void addUserVariableDefinition(UserVariableDefinitionDto definition,
			AsyncCallback<UserVariableDefinitionDto> callback);

	void addUserVariableInstances(List<UserVariableDefinitionDto>  definitionDto,
			AbstractUserManagerNodeDto nodeDto,
			AsyncCallback<List<UserVariableInstanceDto>> callback);

	void getDefinedUserVariableDefinitions(
			AsyncCallback<ListLoadResult<UserVariableDefinitionDto>> callback);

	void getDefinedUserVariableInstances(AbstractUserManagerNodeDto nodeDto,
			AsyncCallback<List<UserVariableInstanceDto>> callback);

	void getInheritedUserVariableInstances(AbstractUserManagerNodeDto nodeDto,
			AsyncCallback<List<UserVariableInstanceDto>> callback);

	void removeUserVariableDefinitions(
			Collection<UserVariableDefinitionDto> definition,
			boolean force,
			AsyncCallback<Void> callback);

	void removeUserVariableInstances(
			Collection<UserVariableInstanceDto> instanceDto,
			AsyncCallback<Void> callback);

	void updateUserVariableDefinition(UserVariableDefinitionDto definition,
			AsyncCallback<UserVariableDefinitionDto> callback);

	void updateUserVariableInstance(UserVariableInstanceDto instanceDto,
			AsyncCallback<UserVariableInstanceDto> callback);

}
