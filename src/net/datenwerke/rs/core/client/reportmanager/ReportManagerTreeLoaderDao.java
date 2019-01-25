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
 
 
package net.datenwerke.rs.core.client.reportmanager;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.core.client.reportmanager.dto.ReportFolderDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.reportmanager.rpc.ReportManagerTreeLoaderAsync;
import net.datenwerke.treedb.client.treedb.TreeDbFtoConverter;
import net.datenwerke.treedb.client.treedb.TreeDbLoaderDao;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.client.treedb.dto.decorator.AbstractNodeDtoDec;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class ReportManagerTreeLoaderDao extends TreeDbLoaderDao {

	
	@Inject
	public ReportManagerTreeLoaderDao(
		ReportManagerTreeLoaderAsync treeHandler, TreeDbFtoConverter treeDbFtoConverter	
		){
		super(treeHandler, treeDbFtoConverter);
	}
	

	public void getReportsInCatalog(ReportFolderDto folder, boolean showVariants, AsyncCallback<List<ReportDto>> callback){
		final AsyncCallback handledCallback = transformListCallback(callback);
		((ReportManagerTreeLoaderAsync)treeLoader).getReportsInCatalog(folder, showVariants, new AsyncCallback<String[][]>() {

			@Override
			public void onFailure(Throwable caught) {
				handledCallback.onFailure(caught);
			}

			@Override
			public void onSuccess(String[][] result) {
				List<AbstractNodeDto> nodes = new ArrayList<AbstractNodeDto>();
				
				for(String[] fto : result){
					AbstractNodeDtoDec dto = treeDbFtoConverter.convert(fto);
					nodes.add(dto);
				}
				
				handledCallback.onSuccess(nodes);
			}
		});
	}


	public void getReportsInCatalog(boolean showVariants,
			RsAsyncCallback<List<ReportDto>> rsAsyncCallback) {
		getReportsInCatalog(null, showVariants, rsAsyncCallback);
	}
}
