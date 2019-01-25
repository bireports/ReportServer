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

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

import net.datenwerke.eximport.ex.ExportConfig;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.base.ext.client.dashboardmanager.eximport.ex.rpc.DashboardManagerExportRpcService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.AbstractDatasourceManagerNode;
import net.datenwerke.rs.dashboard.client.dashboard.dto.AbstractDashboardManagerNodeDto;
import net.datenwerke.rs.dashboard.service.dashboard.entities.AbstractDashboardManagerNode;
import net.datenwerke.rs.eximport.service.eximport.ex.http.HttpExportService;
import net.datenwerke.rs.eximport.service.genrights.ExportSecurityTarget;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.annotation.GenericTargetVerification;
import net.datenwerke.security.service.security.annotation.RightsVerification;
import net.datenwerke.security.service.security.annotation.SecurityChecked;
import net.datenwerke.security.service.security.rights.Execute;
import net.datenwerke.treedb.ext.service.eximport.TreeNodeExportItemConfig;
import net.datenwerke.treedb.ext.service.eximport.helper.TreeNodeExportHelperServiceImpl;

/**
 * 
 *
 */
@Singleton
public class DashboardManagerExportRpcServiceImpl extends
		SecuredRemoteServiceServlet implements DashboardManagerExportRpcService {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1551324209469664301L;
	
	private final DtoService dtoService;
	private final Provider<HttpExportService> httpExportServiceProvider;
	private final TreeNodeExportHelperServiceImpl exportHelper;

	@Inject
	public DashboardManagerExportRpcServiceImpl(
		DtoService dtoService,
		Provider<HttpExportService> httpExportServiceProvider,
		TreeNodeExportHelperServiceImpl exportHelper
		){
		
		/* store objects */
		this.dtoService = dtoService;
		this.httpExportServiceProvider = httpExportServiceProvider;
		this.exportHelper = exportHelper;
	}
	
	@Override
	@SecurityChecked(
		genericTargetVerification = { 
			@GenericTargetVerification(
				target = ExportSecurityTarget.class, 
				verify = @RightsVerification(rights = Execute.class)) 
		})
	@Transactional(rollbackOn={Exception.class})
	public void quickExport(AbstractDashboardManagerNodeDto nodeDto) throws ServerCallFailedException {
		AbstractDashboardManagerNode node = (AbstractDashboardManagerNode) dtoService.loadPoso(nodeDto);
		
		String exportXML = exportHelper.export(node,true,"Dashboard-Export");
		
		httpExportServiceProvider.get().storeExport(exportXML, node.getName());
	}
	
	private void addChildren(ExportConfig exportConfig, AbstractDatasourceManagerNode report) {
		for(AbstractDatasourceManagerNode childNode : report.getChildren()){
			exportConfig.addItemConfig(new TreeNodeExportItemConfig(childNode));
			addChildren(exportConfig, childNode);
		}
	}
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public String loadResult() throws ServerCallFailedException {
		return httpExportServiceProvider.get().getAndRemoveStoredExport();
	}


}
