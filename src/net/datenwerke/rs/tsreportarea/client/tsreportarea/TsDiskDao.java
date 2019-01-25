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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskFolderDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.container.TsDiskItemList;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.rpc.TsDiskRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

/**
 * 
 *
 */
public class TsDiskDao extends Dao {

	private final TsDiskRpcServiceAsync rpcService;
	
	@Inject
	public TsDiskDao(
		TsDiskRpcServiceAsync rpcservice	
		){
		/* store objects */
		this.rpcService = rpcservice;
	}

	public void getItemsIn(TeamSpaceDto teamSpace, TsDiskFolderDto folder,
			AsyncCallback<TsDiskItemList> callback) {
		rpcService.getItemsIn(teamSpace, folder, transformContainerCallback(callback));
	}
	
	public void createFolder(TeamSpaceDto teamSpaceDto,
			TsDiskFolderDto parent, TsDiskFolderDto dummy,
			AsyncCallback<TsDiskFolderDto> callback){
		rpcService.createFolder(teamSpaceDto, parent, dummy, transformDtoCallback(callback));
	}
	
	public void importReports(TeamSpaceDto teamSpaceDto, TsDiskFolderDto parent, List<ReportDto> reports, boolean copy, boolean reference, 
			AsyncCallback<List<TsDiskReportReferenceDto>> callback){
		rpcService.importReports(teamSpaceDto, parent, reports, copy, reference, transformListCallback(callback));
	}
	
	public void importReport(TeamSpaceDto teamSpaceDto, TsDiskFolderDto parent, ReportDto report, boolean copy, boolean reference,
			AsyncCallback<List<TsDiskReportReferenceDto>> callback){
		List<ReportDto> reports = new ArrayList<ReportDto>();
		reports.add(report);
		rpcService.importReports(teamSpaceDto, parent, reports, copy, reference, transformListCallback(callback));
	}
	
	public void getTeamSpacesWithFavoriteApp(AsyncCallback<List<TeamSpaceDto>> callback){
		rpcService.getTeamSpacesWithTsDiskApp(transformListCallback(callback));
	}
	
	public void getReportsInCatalog(AsyncCallback<List<ReportDto>> callback){
		rpcService.getReportsInCatalog(transformListCallback(callback));
	}
	
	public void getReferencesInApp(TeamSpaceDto teamSpace,
			AsyncCallback<List<TsDiskReportReferenceDto>> callback){
		getReferencesInApp(teamSpace, null, callback);
	}
	
	public void getReferencesInApp(TeamSpaceDto teamSpace, TsDiskFolderDto folder,
			AsyncCallback<List<TsDiskReportReferenceDto>> callback){
		rpcService.getReferencesInApp(teamSpace, folder, transformListCallback(callback));
	}
	
	public void sendUserViewChangedNotice(String viewId,
			AsyncCallback<Void> callback){
		rpcService.sendUserViewChangedNotice(viewId, transformAndKeepCallback(callback));
	}
	
	public void getTeamSpacesWithReferenceTo(ReportDto report,
			AsyncCallback<List<TeamSpaceDto>> callback){
		rpcService.getTeamSpacesWithReferenceTo(report, transformListCallback(callback));
	}

	public void createAndImportVariant(TeamSpaceDto currentSpace,
			TsDiskFolderDto currentFolder, ReportDto report,
			String executeToken, String name, String desc,
			RsAsyncCallback<TsDiskReportReferenceDto> callback) {
		ReportDto reportVariantDto = unproxy(report);
		rpcService.createAndImportVariant(currentSpace, currentFolder, reportVariantDto, executeToken, name, desc, transformDtoCallback(callback));
	}

	public void updateReferenceAndReport(TsDiskReportReferenceDto reference,
			ReportDto report, String executeToken, String name, String description, RsAsyncCallback<TsDiskReportReferenceDto> callback) {
		rpcService.updateReferenceAndReport(reference, report, executeToken, name, description, transformDtoCallback(callback));
	}
}

