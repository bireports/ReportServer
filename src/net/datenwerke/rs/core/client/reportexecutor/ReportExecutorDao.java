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
 
 
package net.datenwerke.rs.core.client.reportexecutor;

import net.datenwerke.gf.client.history.HistoryLocation;
import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.callback.DaoAsyncCallback;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.rs.core.client.reportexecutor.rpc.ReportExecutorRpcServiceAsync;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class ReportExecutorDao extends Dao {

	private final ReportExecutorRpcServiceAsync rpcService;

	@Inject
	public ReportExecutorDao(ReportExecutorRpcServiceAsync rpcService) {
		super();
		this.rpcService = rpcService;
	}
	
	public void createNewVariant(ReportDto reportVariantDto, String executeToken, String title, String description, AsyncCallback<ReportDto> callback){
		reportVariantDto = unproxy(reportVariantDto);
		rpcService.createNewVariant(reportVariantDto, executeToken, title, description, transformDtoCallback(callback));
	}

	public Request executeAs(String format, String executeToken, ReportDto report, DwModel config, AsyncCallback<DwModel> callback){
		report = unproxy(report);
		return rpcService.executeAs(format, executeToken, report, config, transformAndKeepCallback(callback));
	}

	public void editVariant(ReportDto reportVariantDto, String executeToken, String title,
			String description, 
			AsyncCallback<ReportDto> callback){
		reportVariantDto = unproxy(reportVariantDto);
		rpcService.editVariant(reportVariantDto, executeToken, title, description, transformDtoCallback(callback));
	}

	public void deleteVariant(ReportDto reportVariantDto, AsyncCallback<Void> callback){
		DaoAsyncCallback<Void> daoCallback = transformAndKeepCallback(callback);
		daoCallback.addDtoToDetach(reportVariantDto);
		
		rpcService.deleteVariant(reportVariantDto, daoCallback);
	}

	public Request storePNGInSession(String executorToken, ReportDto report, AsyncCallback<DwModel> callback){
		report = unproxy(report);
		return rpcService.storePNGInSession(executorToken, report, transformAndKeepCallback(callback));
	}
	
	public void loadFullReportForExecution(Long reportId, AsyncCallback<ReportDto> callback){
		HistoryLocation location = HistoryLocation.fromString("#dummy/id:" + reportId);
		
		loadReportForExecutionFrom(location, callback);
	}
	
	public void loadFullReportForExecution(ReportDto report, AsyncCallback<ReportDto> callback){
		loadFullReportForExecution(report, false, callback);
	}
	
	public void loadFullReportForExecution(ReportDto report, boolean handleFailuresMyself, AsyncCallback<ReportDto> callback){
		DaoAsyncCallback<ReportDto> transformAndKeepCallback = transformAndKeepCallback(callback);
		if(handleFailuresMyself)
			transformAndKeepCallback.ignoreExpectedExceptions(true);
		rpcService.loadFullReportForExecution(report, transformAndKeepCallback);
	}

	public void loadReportForExecutionFrom(HistoryLocation location,
			AsyncCallback<ReportDto> callback){
		rpcService.loadReportForExecutionFrom(location, transformAndKeepCallback(callback));
	}

	public void loadFullReportUnmanaged(ReportDto report, AsyncCallback<ReportDto> callback){
		rpcService.loadFullReportUnmanaged(report, callback);
	}
	
	public void getPreviewMode(AsyncCallback<String> callback){
		rpcService.getPreviewMode(callback);
	}

	public void setPreviewModeUserProperty(String value, RsAsyncCallback<Void> cb) {
		rpcService.setPreviewModeUserProperty(value, cb);
		
	}
	
}
