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
 
 
package net.datenwerke.rs.eximport.service.eximport.hookers;

import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;

import net.datenwerke.eximport.exceptions.InvalidImportDocumentException;
import net.datenwerke.gf.service.upload.UploadedFile;
import net.datenwerke.gf.service.upload.hooks.FileUploadHandlerHook;
import net.datenwerke.rs.eximport.client.eximport.RsExImportUiModule;
import net.datenwerke.rs.eximport.service.eximport.im.http.HttpImportService;
import net.datenwerke.rs.eximport.service.genrights.ExportSecurityTarget;
import net.datenwerke.rs.utils.zip.ZipExtractionConfig;
import net.datenwerke.rs.utils.zip.ZipUtilsService;
import net.datenwerke.security.service.authenticator.AuthenticatorService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Execute;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ImportUploadHooker implements FileUploadHandlerHook {
	
	private final Provider<AuthenticatorService> authenticatorServiceProvider;
	private final Provider<SecurityService> securityServiceProvider;
	private final Provider<HttpImportService> httpImportServiceProvider;
	private final Provider<ZipUtilsService> zipUtilsProvider;

	@Inject
	public ImportUploadHooker(
			Provider<AuthenticatorService> authenticatorServiceProvider, 
			Provider<SecurityService> securityServiceProvider, 
			Provider<HttpImportService> httpImportServiceProvider,
			Provider<ZipUtilsService> zipUtilsProvider
			
	) {
		this.authenticatorServiceProvider = authenticatorServiceProvider;
		this.securityServiceProvider = securityServiceProvider;
		this.httpImportServiceProvider  = httpImportServiceProvider;
		this.zipUtilsProvider  = zipUtilsProvider;
	}
	
	@Override
	public boolean consumes(String handler) {
		return RsExImportUiModule.UPLOAD_HANDLER_ID.equals(handler);
	}

	@Override
	public String uploadOccured(UploadedFile uploadedFile) {
		Map<String, String> metadataMap = uploadedFile.getMetadata();
		
		String fileName = uploadedFile.getFileName();
		byte[] fileContents = uploadedFile.getFileBytes();
		
		if(null == fileName || null == fileContents || fileContents.length == 0 || fileName.isEmpty())
			throw new IllegalArgumentException("File seems to be empty");
		
		if(! fileName.endsWith(".xml") && ! fileName.endsWith(".zip"))
			throw new IllegalArgumentException("Expected zip or xml file");
		

		if(null == authenticatorServiceProvider.get().getCurrentUser())
			throw new ViolatedSecurityException();

		SecurityService securityService = securityServiceProvider.get();

		if(! securityService.checkRights(ExportSecurityTarget.class, Execute.class))
			throw new ViolatedSecurityException();
		
		final HttpImportService importService = httpImportServiceProvider.get();
		importService.createNewConfig();
		
		if(fileName.endsWith(".xml")){
			try{
				importService.setData(new String(fileContents));
			} catch (InvalidImportDocumentException e) {
				throw new IllegalArgumentException(e.getMessage());
			}
		} else { /* zip */
			ZipUtilsService zipUtils = zipUtilsProvider.get();
			try {
				zipUtils.extractZip(fileContents, new ZipExtractionConfig() {
					boolean processed = false;
					@Override
					public void processContent(ZipEntry entry, byte[] content){
						if(processed)
							throw new IllegalArgumentException("Zip should only contain one file");
						try{
							importService.setData(new String(content));
						} catch (InvalidImportDocumentException e) {
							throw new IllegalStateException(e);
						}
						
						processed = true;
					}
				});
			} catch (IOException e) {
				throw new IllegalStateException(e);
			}
		}
		
		return null;
	}


}
