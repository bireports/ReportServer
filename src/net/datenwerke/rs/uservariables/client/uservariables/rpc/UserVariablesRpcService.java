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
 
 
package net.datenwerke.rs.uservariables.client.uservariables.rpc;

import java.util.Collection;
import java.util.List;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.uservariables.client.uservariables.dto.UserVariableDefinitionDto;
import net.datenwerke.rs.uservariables.client.uservariables.dto.UserVariableInstanceDto;
import net.datenwerke.security.client.usermanager.dto.AbstractUserManagerNodeDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.ListLoadResult;

/**
 * 
 *
 */
@RemoteServiceRelativePath("uservariables")
public interface UserVariablesRpcService extends RemoteService {

	public List<UserVariableInstanceDto> addUserVariableInstances(List<UserVariableDefinitionDto>  definitionDto, AbstractUserManagerNodeDto nodeDto) throws ServerCallFailedException;
	
	public UserVariableInstanceDto updateUserVariableInstance(UserVariableInstanceDto instanceDto) throws ServerCallFailedException;
	
	public void removeUserVariableInstances(Collection<UserVariableInstanceDto> instanceDto) throws ServerCallFailedException;
	
	/**
	 * Adds a new user variable to the list of available variables.
	 * 
	 * @param definition
	 * @return
	 * @throws ServerCallFailedException
	 */
	public UserVariableDefinitionDto addUserVariableDefinition(UserVariableDefinitionDto definition) throws ServerCallFailedException;
	
	/**
	 * Changes the definition of a user variable in the list of available variables.
	 * 
	 * @param definition
	 * @return
	 * @throws ServerCallFailedException
	 */
	public UserVariableDefinitionDto updateUserVariableDefinition(UserVariableDefinitionDto definition) throws ServerCallFailedException;
	
	void removeUserVariableDefinitions(
			Collection<UserVariableDefinitionDto> definition, boolean force) throws ServerCallFailedException;
	
	/**
	 * Returns all defined variables.
	 * 
	 * @return
	 * @throws ServerCallFailedException
	 */
	public ListLoadResult<UserVariableDefinitionDto> getDefinedUserVariableDefinitions() throws ServerCallFailedException;
	
	/**
	 * Returns the instances for a given node.
	 * 
	 * @param nodeDto
	 * @return
	 * @throws ServerCallFailedException
	 */
	public List<UserVariableInstanceDto> getDefinedUserVariableInstances(AbstractUserManagerNodeDto nodeDto) throws ServerCallFailedException;
	
	/**
	 * Returns the inherited instances for a given node.
	 * 
	 * @param nodeDto
	 * @return
	 * @throws ServerCallFailedException
	 */
	public List<UserVariableInstanceDto> getInheritedUserVariableInstances(AbstractUserManagerNodeDto nodeDto) throws ServerCallFailedException;
}
