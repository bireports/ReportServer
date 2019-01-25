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
 
 
package net.datenwerke.rs.base.ext.service.dashboardmanager.eximport;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.eximport.exceptions.IllegalImportConfigException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.dashboard.client.dashboard.dto.AbstractDashboardManagerNodeDto;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardFolderDto;
import net.datenwerke.rs.dashboard.service.dashboard.entities.AbstractDashboardManagerNode;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportConfigDto;
import net.datenwerke.rs.eximport.service.eximport.im.http.HttpImportService;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.treedb.ext.client.eximport.im.dto.TreeImportConfigDto;
import net.datenwerke.treedb.ext.service.eximport.http.HttpImportConfigurationProviderHookImplForTrees;

public class HttpDashboardManagerImportConfigurationHooker extends
		HttpImportConfigurationProviderHookImplForTrees<AbstractDashboardManagerNode, AbstractDashboardManagerNodeDto> {

	@Inject
	public HttpDashboardManagerImportConfigurationHooker(DtoService dtoService,
			Provider<HttpImportService> httpImportServiceProvider, SecurityService securityService) {
		super(dtoService, httpImportServiceProvider, securityService);
	}

	@Override
	public boolean consumes(String id) {
		return DashboardManagerImporter.IMPORTER_ID.equals(id);
	}

	@Override
	public void validate(ImportConfigDto config) throws IllegalImportConfigException {
		TreeImportConfigDto<AbstractDashboardManagerNodeDto> treeConfig = (TreeImportConfigDto<AbstractDashboardManagerNodeDto>) config;

		if (null != treeConfig.getParent() && !(treeConfig.getParent() instanceof DashboardFolderDto))
			throw new IllegalImportConfigException("Illegal dadget library destination. Has to be a folder, but was: "
					+ treeConfig.getParent().getClass());
	}
}
