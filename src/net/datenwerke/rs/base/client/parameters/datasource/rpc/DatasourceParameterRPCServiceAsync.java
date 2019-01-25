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
 
 
package net.datenwerke.rs.base.client.parameters.datasource.rpc;

import java.util.Collection;

import net.datenwerke.rs.base.client.parameters.datasource.dto.DatasourceParameterDataDto;
import net.datenwerke.rs.base.client.parameters.datasource.dto.DatasourceParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.dto.ParameterInstanceDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.ListLoadResult;

public interface DatasourceParameterRPCServiceAsync {

	void loadDatasourceParameterData(DatasourceParameterDefinitionDto queryParameterDTO,  Collection<ParameterInstanceDto> dependsOnParameterDTOs,
			boolean mergeDefinition, AsyncCallback<ListLoadResult<DatasourceParameterDataDto>> callback);

	void allowDatasourceParameterPostProcessing(AsyncCallback<Boolean> callback);

}
