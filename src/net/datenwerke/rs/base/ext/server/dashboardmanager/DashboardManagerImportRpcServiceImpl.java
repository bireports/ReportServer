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
 
 
package net.datenwerke.rs.base.ext.server.dashboardmanager;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.base.ext.client.dashboardmanager.eximport.im.rpc.DashboardManagerImportRpcService;
import net.datenwerke.rs.base.ext.service.dashboardmanager.eximport.DashboardManagerExporter;
import net.datenwerke.rs.eximport.service.genrights.ImportSecurityTarget;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.annotation.GenericTargetVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.rights.Execute;
import net.datenwerke.treedb.ext.client.eximport.im.dto.ImportTreeModel;
import net.datenwerke.treedb.ext.service.eximport.http.HttpTreeImportService;

/**
 * 
 *
 */
@Singleton
public class DashboardManagerImportRpcServiceImpl extends
		SecuredRemoteServiceServlet implements DashboardManagerImportRpcService {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7633633174743769046L;
	
	private final Provider<HttpTreeImportService> httpImportServiceProvider;
	
	@Inject
	public DashboardManagerImportRpcServiceImpl(
		Provider<HttpTreeImportService> httpImportServiceProvider
		) {
		
		/* store objects */
		this.httpImportServiceProvider = httpImportServiceProvider;
	}


	@Override
	@SecurityChecked(
			genericTargetVerification = { 
				@GenericTargetVerification(
					target = ImportSecurityTarget.class, 
					verify = @RightsVerification(rights = Execute.class)) 
			})
	@Transactional(rollbackOn={Exception.class})
	public List<ImportTreeModel> loadTree() throws ServerCallFailedException {
		HttpTreeImportService httpImportService = httpImportServiceProvider.get();

		List<ImportTreeModel> tree = httpImportService.loadTreeDto(DashboardManagerExporter.class);
		
		return tree;
	}



}
