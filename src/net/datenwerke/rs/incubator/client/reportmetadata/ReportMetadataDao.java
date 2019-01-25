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
 
 
package net.datenwerke.rs.incubator.client.reportmetadata;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportMetadataDto;
import net.datenwerke.rs.incubator.client.reportmetadata.rpc.ReportMetadataRpcServiceAsync;

public class ReportMetadataDao extends Dao{

	private final ReportMetadataRpcServiceAsync rpcService;
	
	@Inject
	public ReportMetadataDao(
		ReportMetadataRpcServiceAsync rpcService	
		){
	
		/* store objects */
		this.rpcService = rpcService;
	}
	
	public void getPropertyKeys(AsyncCallback<List<String>> callback){
		rpcService.getMetadataKeys(transformAndKeepCallback(callback));
	}

	public void updateProperties(ReportDto report, List<ReportMetadataDto> addedProperties, List<ReportMetadataDto> modifiedProperties, List<ReportMetadataDto> removedProperties,
			AsyncCallback<ReportDto> callback) {
		rpcService.updateMetadata(report, addedProperties, modifiedProperties, removedProperties, transformDtoCallback(callback));
	}
}
