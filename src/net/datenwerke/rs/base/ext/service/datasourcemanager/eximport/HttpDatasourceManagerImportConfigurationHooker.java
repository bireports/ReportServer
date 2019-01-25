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
 
 
package net.datenwerke.rs.base.ext.service.datasourcemanager.eximport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.datenwerke.eximport.exceptions.IllegalImportConfigException;
import net.datenwerke.eximport.im.ImportMode;
import net.datenwerke.eximport.obj.ExportedItem;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.ext.client.datasourcemanager.eximport.im.dto.DatasourceManagerImportConfigDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.AbstractDatasourceManagerNodeDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceFolderDto;
import net.datenwerke.rs.core.service.datasourcemanager.entities.AbstractDatasourceManagerNode;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportConfigDto;
import net.datenwerke.rs.eximport.service.eximport.im.http.HttpImportService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.treedb.ext.client.eximport.im.dto.ImportTreeModel;
import net.datenwerke.treedb.ext.client.eximport.im.dto.TreeImportConfigDto;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeImportItemConfig;
import net.datenwerke.treedb.ext.service.eximport.http.HttpImportConfigurationProviderHookImplForTrees;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class HttpDatasourceManagerImportConfigurationHooker extends
	HttpImportConfigurationProviderHookImplForTrees<AbstractDatasourceManagerNode, AbstractDatasourceManagerNodeDto> {

	@Inject
	public HttpDatasourceManagerImportConfigurationHooker(
		DtoService dtoService,
		Provider<HttpImportService> httpImportServiceProvider, 
		SecurityService securityService
		){
		super(dtoService, httpImportServiceProvider, securityService);
	}
	
	@Override
	public boolean consumes(String id) {
		return DatasourceManagerImporter.IMPORTER_ID.equals(id);
	}
	
	@Override
	protected void finishUpConfiguration(
			TreeImportConfigDto<AbstractDatasourceManagerNodeDto> treeConfig) {
		finishUpConfiguration(
			new HashMap<String, ImportTreeModel>(), 
			new HashMap<String, TreeNodeImportItemConfig>(), 
			treeConfig);
	}
	
	@Override
	protected void finishUpConfiguration(
			Map<String, ImportTreeModel> lookupMap,
			Map<String, TreeNodeImportItemConfig> lookupConfigMap,
			TreeImportConfigDto<AbstractDatasourceManagerNodeDto> treeConfig) {
		DatasourceManagerImportConfigDto dsConfig = (DatasourceManagerImportConfigDto)treeConfig;
		
		if(null == dsConfig.getDefaultDatasource())
			return;
		
		DatasourceDefinition defaultDatasource = (DatasourceDefinition) dtoService.loadPoso(dsConfig.getDefaultDatasource());
		if(null == defaultDatasource)
			throw new RuntimeException("Could not load default datasource");
		
		HttpImportService importService = httpImportServiceProvider.get();

		try{
			List<ExportedItem> exportedItems = importService.getExportedItemsFor(DatasourceManagerExporter.class);
			
			for(ExportedItem ei : exportedItems)
				if(! lookupMap.containsKey(ei.getId()))
					importService.addItemConfig(new TreeNodeImportItemConfig(ei.getId(),ImportMode.REFERENCE,defaultDatasource));

		} catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void validate(ImportConfigDto config) throws IllegalImportConfigException {
		TreeImportConfigDto<AbstractDatasourceManagerNodeDto> treeConfig = (TreeImportConfigDto<AbstractDatasourceManagerNodeDto>) config;
		
		if(null != treeConfig.getParent() && ! (treeConfig.getParent() instanceof DatasourceFolderDto))
			throw new IllegalImportConfigException("Illegal datasource import destination. Has to be a folder, but was: " + treeConfig.getParent().getClass());
	}

}
