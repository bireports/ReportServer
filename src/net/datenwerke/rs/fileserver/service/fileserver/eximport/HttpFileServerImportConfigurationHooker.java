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
 
 
package net.datenwerke.rs.fileserver.service.fileserver.eximport;

import net.datenwerke.eximport.exceptions.IllegalImportConfigException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportConfigDto;
import net.datenwerke.rs.eximport.service.eximport.im.http.HttpImportService;
import net.datenwerke.rs.fileserver.client.fileserver.dto.AbstractFileServerNodeDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.FileServerFolderDto;
import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.treedb.ext.client.eximport.im.dto.ImportTreeModel;
import net.datenwerke.treedb.ext.client.eximport.im.dto.TreeImportConfigDto;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeImportItemConfig;
import net.datenwerke.treedb.ext.service.eximport.http.HttpImportConfigurationProviderHookImplForTrees;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class HttpFileServerImportConfigurationHooker extends
	HttpImportConfigurationProviderHookImplForTrees<AbstractFileServerNode, AbstractFileServerNodeDto> {

	@Inject
	public HttpFileServerImportConfigurationHooker(
		DtoService dtoService,
		Provider<HttpImportService> httpImportServiceProvider, 
		SecurityService securityService
		){
		super(dtoService, httpImportServiceProvider, securityService);
		
	}
	
	@Override
	public boolean consumes(String id) {
		return FileServerImporter.IMPORTER_ID.equals(id);
	}

	@Override
	protected void doConfigureNodeConfig(
			TreeNodeImportItemConfig realConfigNode, ImportTreeModel model,
			TreeImportConfigDto<AbstractFileServerNodeDto> treeConfig) {
	}

	@Override
	public void validate(ImportConfigDto config)
			throws IllegalImportConfigException {
		TreeImportConfigDto<AbstractFileServerNodeDto> treeConfig = (TreeImportConfigDto<AbstractFileServerNodeDto>) config;
		if(null != treeConfig.getParent() && ! (treeConfig.getParent() instanceof FileServerFolderDto))
			throw new IllegalImportConfigException("Illegal file import destination. Has to be a folder, but was: " + treeConfig.getParent().getClass());
	}

	
}
