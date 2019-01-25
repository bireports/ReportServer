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
 
 
package net.datenwerke.gf.service.upload.hookers;

import java.io.IOException;

import org.json.JSONObject;

import net.datenwerke.gf.client.upload.FileUploadUIModule;
import net.datenwerke.gf.client.upload.dto.UploadResponse;
import net.datenwerke.gf.service.upload.FileUploadService;
import net.datenwerke.gf.service.upload.UploadedFile;
import net.datenwerke.gf.service.upload.hooks.FileUploadHandlerHook;

import com.google.inject.Inject;

public class InterimFileUploadHandler implements FileUploadHandlerHook {

	private final FileUploadService fileUploadService;
	
	@Inject
	public InterimFileUploadHandler(
		FileUploadService fileUploadService
		) {
		this.fileUploadService = fileUploadService;
	}

	@Override
	public boolean consumes(String handler) {
		return FileUploadUIModule.INTERIM_FILE_UPLOAD_HANDLER.equals(handler);
	}
	
	@Override
	public String uploadOccured(UploadedFile uploadedFile) {
		try {
			UploadResponse uploadInterimFile = fileUploadService.uploadInterimFile(uploadedFile);
			
			String json = new JSONObject().put("id", uploadInterimFile.getId()).put("name", uploadInterimFile.getName()).toString();
			
			return json;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}


}
