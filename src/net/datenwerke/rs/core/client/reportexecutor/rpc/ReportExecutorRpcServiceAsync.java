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
 
 
package net.datenwerke.rs.core.client.reportexecutor.rpc;

import net.datenwerke.gf.client.history.HistoryLocation;
import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReportExecutorRpcServiceAsync {

	void createNewVariant(ReportDto reportVariantDto, String executeToken, String title, String description, AsyncCallback<ReportDto> callback);

	void editVariant(ReportDto reportVariantDto, String executeToken, String title,
			String description, AsyncCallback<ReportDto> callback);

	void deleteVariant(ReportDto reportVariantDto, AsyncCallback<Void> callback);

	Request storePNGInSession(String executorToken, ReportDto report, AsyncCallback<DwModel> callback);

	Request executeAs(String format, String executeToken, ReportDto report, DwModel config,
			AsyncCallback<DwModel> callback);
	
	void loadFullReportForExecution(ReportDto report,
			AsyncCallback<ReportDto> callback);

	void loadReportForExecutionFrom(HistoryLocation location,
			AsyncCallback<ReportDto> callback);

	void getPreviewMode(AsyncCallback<String> callback);

	void setPreviewModeUserProperty(String value, AsyncCallback<Void> callback);

	void loadFullReportUnmanaged(ReportDto report, AsyncCallback<ReportDto> callback);

}
