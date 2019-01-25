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
 
 
package net.datenwerke.rs.fileserver.service.fileserver.hookers;

import java.util.Map;

import net.datenwerke.gf.service.upload.UploadedFile;
import net.datenwerke.gf.service.upload.hooks.FileUploadHandlerHook;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReport;
import net.datenwerke.rs.base.service.reportengines.jasper.entities.JasperReportJRXMLFile;
import net.datenwerke.rs.base.service.reportengines.jasper.util.JasperUtilsService;
import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.rs.fileserver.client.fileserver.FileServerUiModule;
import net.datenwerke.rs.fileserver.service.fileserver.FileServerService;
import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.treedb.actions.UpdateAction;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class FileServerFileUploadHooker implements FileUploadHandlerHook {
	
	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final Provider<SecurityService> securityServiceProvider;
	private final Provider<FileServerService> fileServiceProvider;

	@Inject
	public FileServerFileUploadHooker(
			Provider<AuthenticatorService> authenticatorServiceProvider, 
			Provider<SecurityService> securityServiceProvider, 
			Provider<FileServerService> fileServiceProvider
	) {
		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.securityServiceProvider = securityServiceProvider;
		this.fileServiceProvider  = fileServiceProvider;
	}
	
	@Override
	public boolean consumes(String handler) {
		return FileServerUiModule.FILE_UPLOAD_HANDLER_ID.equals(handler);
	}

	@Override
	public String uploadOccured(UploadedFile uploadedFile) {
		Map<String, String> metadataMap = uploadedFile.getMetadata();
		
		long fileId = Long.valueOf(metadataMap.get(FileServerUiModule.UPLOAD_FILE_ID_FIELD));
		String fileName = uploadedFile.getFileName();
		byte[] fileContents = uploadedFile.getFileBytes();
		
		if(null == fileName || null == fileContents || fileContents.length == 0 || fileName.isEmpty())
			return null;

		if(null == authenticatorServiceProvider.get().getCurrentUser())
			throw new ViolatedSecurityException();

		SecurityService securityService = securityServiceProvider.get();

		FileServerService service = fileServiceProvider.get();
		AbstractFileServerNode fileNode = service.getNodeById(fileId);
		
		if(! securityService.checkActions(fileNode, UpdateAction.class))
			throw new ViolatedSecurityException();

		if(fileNode instanceof FileServerFile){
			FileServerFile file = (FileServerFile) fileNode; 
			
			file.setContentType(uploadedFile.getContentType());
			file.setData(fileContents);
			if(null == file.getName() || "".equals(file.getName()))
				file.setName(fileName);
			
			service.merge(file);
		}
		
		return null;
	}


}
