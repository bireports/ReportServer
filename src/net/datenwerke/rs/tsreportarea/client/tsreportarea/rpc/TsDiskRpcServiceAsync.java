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
 
 
package net.datenwerke.rs.tsreportarea.client.tsreportarea.rpc;

import java.util.List;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.teamspace.client.teamspace.dto.TeamSpaceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.AbstractTsDiskNodeDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskFolderDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsDiskReportReferenceDto;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.TsReferenceInfo;
import net.datenwerke.rs.tsreportarea.client.tsreportarea.dto.container.TsDiskItemList;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.client.treedb.rpc.RPCTreeLoaderAsync;
import net.datenwerke.treedb.client.treedb.rpc.RPCTreeManagerAsync;

public interface TsDiskRpcServiceAsync extends RPCTreeLoaderAsync, RPCTreeManagerAsync {

	void getItemsIn(TeamSpaceDto teamSpace, TsDiskFolderDto folder,
			AsyncCallback<TsDiskItemList> callback);

	void createFolder(TeamSpaceDto teamSpaceDto,
			TsDiskFolderDto parent, TsDiskFolderDto dummy,
			AsyncCallback<TsDiskFolderDto> callback);

	void importReports(TeamSpaceDto teamSpaceDto, TsDiskFolderDto parent, List<ReportDto> reports, boolean copyReport, boolean reference,
			AsyncCallback<List<TsDiskReportReferenceDto>> callback);

	void getTeamSpacesWithTsDiskApp(AsyncCallback<List<TeamSpaceDto>> callback);

	void getReportsInCatalog(AsyncCallback<List<ReportDto>> callback);

	void getReferencesInApp(TeamSpaceDto teamSpace, TsDiskFolderDto folder,
			AsyncCallback<List<TsDiskReportReferenceDto>> callback);

	void sendUserViewChangedNotice(String viewId,
			AsyncCallback<Void> callback);

	void getTeamSpacesWithReferenceTo(ReportDto report,
			AsyncCallback<List<TeamSpaceDto>> callback);
	
	void getTeamSpacesWithPathsThatLinkTo(ReportDto report,
			AsyncCallback<Map<TeamSpaceDto, List<List<AbstractTsDiskNodeDto>>>> callback);

	void updateNode(AbstractNodeDto nodeDto, boolean changeUnderlyingReport,
			String name, String description, Dto state, AsyncCallback<AbstractNodeDto> callback);

	void createAndImportVariant(TeamSpaceDto currentSpace,
			TsDiskFolderDto currentFolder, ReportDto reportVariantDto,
			String executeToken, String name, String desc,
			AsyncCallback<TsDiskReportReferenceDto> callback);

	void updateReferenceAndReport(TsDiskReportReferenceDto reference, ReportDto report,	String executeToken, String name, String description, AsyncCallback<TsDiskReportReferenceDto> callback);

	void deleteNodes(List<AbstractNodeDto> nodes, Dto state,
			AsyncCallback<Void> callback);

	void deleteNodesWithForce(List<AbstractNodeDto> nodes, Dto state,
			AsyncCallback<Void> callback);

	void getReferenceInfosFor(ReportDto report, AsyncCallback<List<TsReferenceInfo>> callback);
}
