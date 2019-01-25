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
 
 
package net.datenwerke.rs.core.client.parameters.rpc;

import java.util.Collection;
import java.util.List;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("reportmanager_parameter")
public interface ParameterRpcService extends RemoteService {

	public ReportDto addParameter(ParameterDefinitionDto parameter, AbstractNodeDto correspondingNode) throws ServerCallFailedException;
	
	public ReportDto updateParameter(ParameterDefinitionDto parameter) throws ServerCallFailedException, ExpectedException;
	
	public ReportDto removeParameters(Collection<ParameterDefinitionDto> parameters) throws ServerCallFailedException;
	
	public ReportDto updateParameterInstances(Collection<ParameterDefinitionDto> parameters) throws ServerCallFailedException;
	
	public ReportDto movedParameter(ParameterDefinitionDto parameter, int to) throws ServerCallFailedException;
	
	public ReportDto duplicateParameters(List<ParameterDefinitionDto> params, AbstractNodeDto correspondingNode) throws ServerCallFailedException, ExpectedException;
}
