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
 
 
package net.datenwerke.gf.client.fileselection;

import java.util.ArrayList;

import net.datenwerke.gf.client.fileselection.dto.SelectedFileWrapper;
import net.datenwerke.gf.client.fileselection.rpc.FileSelectionRpcServiceAsync;
import net.datenwerke.gxtdto.client.dtomanager.Dao;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class FileSelectionDao extends Dao {

	private final FileSelectionRpcServiceAsync rpcService;

	@Inject
	public FileSelectionDao(FileSelectionRpcServiceAsync rpcService) {
		super();
		this.rpcService = rpcService;
	}

	public void submit(ArrayList<SelectedFileWrapper> data,	FileSelectionConfig config, AsyncCallback<Void> callback) {
		rpcService.submit(data, config, callback);
	}
	
	
}
