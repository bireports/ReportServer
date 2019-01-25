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
 
 
package net.datenwerke.gf.server.upload;

import java.io.IOException;

import net.datenwerke.gf.client.upload.dto.FileToUpload;
import net.datenwerke.gf.client.upload.dto.UploadResponse;
import net.datenwerke.gf.client.upload.rpc.FileUploadRpcService;
import net.datenwerke.gf.service.upload.FileUploadService;
import net.datenwerke.gf.service.upload.UploadedFile;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

@Singleton
public class FileUploadRpcServiceImpl extends SecuredRemoteServiceServlet implements FileUploadRpcService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7513824797769393953L;
	
	private final FileUploadService fileUploadService;

	@Inject
	public FileUploadRpcServiceImpl(
			FileUploadService fileUploadService
	) {
		this.fileUploadService = fileUploadService;
	}

	@Override
	@Transactional(rollbackOn={Exception.class})
	public UploadResponse uploadInterimFile(FileToUpload file) throws ServerCallFailedException {
		/* prepare response */
		try{
			String contentType = fileUploadService.extractContentTypeFromHtml5Upload(file.getB64Data());
			byte[] data = fileUploadService.extractContentFromHtml5Upload(file.getB64Data());
			String name = file.getName();
			
			UploadedFile uf = new UploadedFile();
			uf.setContentType(contentType);
			uf.setFileName(name);
			uf.setFileBytes(data);
			
			UploadResponse response = fileUploadService.uploadInterimFile(uf);
			
			return response;
		} catch(IOException e){
			throw new ServerCallFailedException(e);
		}
	}

	

}
