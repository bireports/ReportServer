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
 
 
package net.datenwerke.rs.base.ext.client.reportmanager.eximport.ex;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.base.ext.client.reportmanager.eximport.ex.rpc.ReportManagerExportRpcServiceAsync;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.AbstractReportManagerNodeDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class ReportManagerExportDao extends Dao  {

	private final ReportManagerExportRpcServiceAsync rpcService;

	@Inject
	public ReportManagerExportDao(ReportManagerExportRpcServiceAsync rpcService) {
		this.rpcService = rpcService;
	}
	
	public void quickExport(AbstractReportManagerNodeDto dto, boolean includeVariants,
			AsyncCallback<Void> callback){
		rpcService.quickExport(dto, includeVariants, transformAndKeepCallback(callback));
	}
	
	public void loadResult(AsyncCallback<String> callback){
		rpcService.loadResult(transformAndKeepCallback(callback));
	}
	
}
