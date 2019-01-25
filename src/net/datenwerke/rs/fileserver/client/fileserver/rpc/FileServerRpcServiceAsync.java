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
 
 
package net.datenwerke.rs.fileserver.client.fileserver.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import net.datenwerke.gf.client.upload.dto.FileToUpload;
import net.datenwerke.rs.fileserver.client.fileserver.dto.AbstractFileServerNodeDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFileDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFolderDto;


public interface FileServerRpcServiceAsync {

	void updateFile(FileServerFileDto file, String data, AsyncCallback<Void> callback);

	void loadFileDataAsString(FileServerFileDto file,
			AsyncCallback<String> callback);

	void uploadFiles(FileServerFolderDto folder, List<FileToUpload> files, AsyncCallback<List<FileServerFileDto>> callback);

	void uploadAndExtract(FileServerFolderDto folder, FileToUpload fileToUpload,
			AsyncCallback<List<AbstractFileServerNodeDto>> callback);

}
