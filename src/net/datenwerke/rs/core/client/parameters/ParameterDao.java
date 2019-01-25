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
 
 
package net.datenwerke.rs.core.client.parameters;

import java.util.Collection;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.core.client.parameters.dto.ParameterDefinitionDto;
import net.datenwerke.rs.core.client.parameters.rpc.ParameterRpcServiceAsync;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

/**
 *
 * 
 * 
 *
 */
public class ParameterDao extends Dao {

	private final ParameterRpcServiceAsync rpcService;

	@Inject
	public ParameterDao(ParameterRpcServiceAsync rpcService) {
		super();
		this.rpcService = rpcService;
	}
	
	public void addParameter(ReportDto report, ParameterDefinitionDto parameter, AbstractNodeDto correspondingNode,
			AsyncCallback<ReportDto> callback){
		rpcService.addParameter(parameter, correspondingNode, transformDtoCallback(callback));
	}

	public void updateParameter(ReportDto report, ParameterDefinitionDto parameter, AsyncCallback<ReportDto> callback){
		rpcService.updateParameter(parameter, transformDtoCallback(callback));
	}

	public void removeParameters(ReportDto report, Collection<ParameterDefinitionDto> parameters,
			AsyncCallback<ReportDto> callback){
		rpcService.removeParameters(parameters, transformDtoCallback(callback));
	}

	public void movedParameter(ParameterDefinitionDto parameter, int to,
			AsyncCallback<ReportDto> callback){
		rpcService.movedParameter(parameter, to, transformDtoCallback(callback));
	}
	
	public void updateParameterInstances(
			ReportDto report,
			Collection<ParameterDefinitionDto> parameters,
			AsyncCallback<ReportDto> callback){
		rpcService.updateParameterInstances(parameters, transformDtoCallback(callback));
	}
	
	public 	void duplicateParameters(List<ParameterDefinitionDto> params,
			AbstractNodeDto correspondingNode, AsyncCallback<ReportDto> callback){
		rpcService.duplicateParameters(params, correspondingNode, transformDtoCallback(callback));
	}
}
