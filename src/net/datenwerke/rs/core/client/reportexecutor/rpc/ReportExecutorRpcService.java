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


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import net.datenwerke.gf.client.history.HistoryLocation;
import net.datenwerke.gxtdto.client.model.DwModel;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;

@RemoteServiceRelativePath("executor")
public interface ReportExecutorRpcService extends RemoteService {

	public DwModel executeAs(String format, String executeToken, ReportDto report, DwModel config) throws ServerCallFailedException;
	
	public DwModel storePNGInSession(String executorToken, ReportDto report) throws ServerCallFailedException;
	
	public ReportDto createNewVariant(ReportDto reportVariantDto, String executeToken, String title, String description) throws ServerCallFailedException;
	
	public ReportDto editVariant(ReportDto reportVariantDto, String executeToken, String title, String description) throws ServerCallFailedException;
	
	public void deleteVariant(ReportDto reportVariantDto) throws ServerCallFailedException;

	public ReportDto loadFullReportForExecution(ReportDto report) throws ServerCallFailedException;
	
	public ReportDto loadReportForExecutionFrom(HistoryLocation location) throws ServerCallFailedException;

	public String getPreviewMode() throws ServerCallFailedException;

	public void setPreviewModeUserProperty(String value) throws ServerCallFailedException;

	public ReportDto loadFullReportUnmanaged(ReportDto report);
	
}
