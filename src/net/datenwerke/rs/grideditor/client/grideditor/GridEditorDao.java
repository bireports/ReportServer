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
 
 
package net.datenwerke.rs.grideditor.client.grideditor;

import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.grideditor.client.grideditor.dto.GridEditorRecordDto;
import net.datenwerke.rs.grideditor.client.grideditor.rpc.GridEditorRpcServiceAsync;

import com.google.inject.Inject;

public class GridEditorDao extends Dao {

	private final GridEditorRpcServiceAsync rpcService;

	@Inject
	public GridEditorDao(GridEditorRpcServiceAsync rpcService) {
		super();
		this.rpcService = rpcService;
	}

	public void commitChanges(ReportDto report, String executeToken,
			List<GridEditorRecordDto> modified,
			List<GridEditorRecordDto> modifiedOriginals,
			List<GridEditorRecordDto> deletedRecords, List<GridEditorRecordDto> newRecords, RsAsyncCallback<Void> callback) {
		rpcService.commitChanges(report, executeToken, modified, modifiedOriginals, deletedRecords, newRecords, transformAndKeepCallback(callback)); 
	}

	
}
