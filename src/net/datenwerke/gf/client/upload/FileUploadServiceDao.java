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
 
 
package net.datenwerke.gf.client.upload;

import net.datenwerke.gf.client.upload.dto.FileToUpload;
import net.datenwerke.gf.client.upload.dto.UploadResponse;
import net.datenwerke.gf.client.upload.rpc.FileUploadRpcServiceAsync;
import net.datenwerke.gxtdto.client.dtomanager.Dao;

import com.google.gwt.http.client.Request;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class FileUploadServiceDao extends Dao{
	
	FileUploadRpcServiceAsync rpcService;
	
	@Inject
	public FileUploadServiceDao(
			FileUploadRpcServiceAsync fileUploadRpcServiceAsync
	) {
		this.rpcService = fileUploadRpcServiceAsync;
	}
	
	
	public Request uploadInterimFile(FileToUpload file, AsyncCallback<UploadResponse> callback){
		return rpcService.uploadInterimFile(file, callback);
	}

}
