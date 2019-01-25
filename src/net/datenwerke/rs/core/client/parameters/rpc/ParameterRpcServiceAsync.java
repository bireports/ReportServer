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
 
 
package net.datenwerke.rs.core.client.parameters.rpc;

import java.util.Collection;
import java.util.List;

import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ParameterRpcServiceAsync {

	void addParameter(ParameterDefinitionDto parameter, AbstractNodeDto correspondingNode,
			AsyncCallback<ReportDto> callback);

	void updateParameter(ParameterDefinitionDto parameter,
			AsyncCallback<ReportDto> callback);

	void removeParameters(Collection<ParameterDefinitionDto> parameters,
			AsyncCallback<ReportDto> callback);

	void movedParameter(ParameterDefinitionDto parameter, int to,
			AsyncCallback<ReportDto> callback);

	void updateParameterInstances(
			Collection<ParameterDefinitionDto> parameters,
			AsyncCallback<ReportDto> callback);

	void duplicateParameters(List<ParameterDefinitionDto> params,
			AbstractNodeDto correspondingNode, AsyncCallback<ReportDto> callback);

}
