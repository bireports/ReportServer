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
 
 
package net.datenwerke.rs.core.client.reportexporter;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.core.client.reportexporter.dto.ReportExecutionConfigDto;
import net.datenwerke.rs.core.client.reportexporter.rpc.ReportExporterRpcServiceAsync;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUser;

public class ReportExporterDao extends Dao {

	private final ReportExporterRpcServiceAsync rpcService;

	@Inject
	public ReportExporterDao(ReportExporterRpcServiceAsync rpcService) {
		super();
		this.rpcService = rpcService;
	}

	public Request storeInSessionForExport(ReportDto reportDto, String executorToken,
			String format, AsyncCallback<Void> callback){
		List<ReportExecutionConfigDto> configs = new ArrayList<ReportExecutionConfigDto>();
		return storeInSessionForExport(reportDto, executorToken, format, configs, callback);
	}
	
	public Request storeInSessionForExport(ReportDto reportDto, String executorToken,
			String format, ReportExecutionConfigDto config,
			AsyncCallback<Void> callback){
		List<ReportExecutionConfigDto> configs = new ArrayList<ReportExecutionConfigDto>();
		configs.add(config);
		return storeInSessionForExport(reportDto, executorToken, format, configs, callback);
	}
	
	public Request storeInSessionForExport(ReportDto reportDto, String executorToken,
			String format, List<ReportExecutionConfigDto> configs,
			AsyncCallback<Void> callback){
		reportDto = unproxy(reportDto);
		return rpcService.storeInSessionForExport(reportDto, executorToken, format, configs, transformAndKeepCallback(callback));
	}

	
	public void exportViaMail(ReportDto reportDto, String executorToke, String format,
	List<ReportExecutionConfigDto> configs, String subject,
	String message, List<StrippedDownUser> recipients,
	AsyncCallback<Void> callback){
		rpcService.exportViaMail(reportDto, executorToke, format, configs, subject, message, recipients, transformAndKeepCallback(callback));
	}
}
