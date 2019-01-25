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
 
 
package net.datenwerke.rs.core.server.datasources;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.gxtdto.server.dtomanager.DtoService;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionDto;
import net.datenwerke.rs.core.client.datasourcemanager.rpc.DatasourceRpcService;
import net.datenwerke.rs.core.client.datasourcemanager.rpc.DatasourceTreeLoader;
import net.datenwerke.rs.core.client.datasourcemanager.rpc.DatasourceTreeManager;
import net.datenwerke.rs.core.service.datasourcemanager.DatasourceService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.AbstractDatasourceManagerNode;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.utils.entitycloner.EntityClonerService;
import net.datenwerke.security.server.TreeDBManagerTreeHandler;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.rights.Read;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;

/**
 * 
 *
 */
@Singleton
public class DatasourceManagerTreeHandlerRpcServiceImpl 
	extends TreeDBManagerTreeHandler<AbstractDatasourceManagerNode>
	implements DatasourceTreeLoader, DatasourceTreeManager, DatasourceRpcService {


	/**
	 * 
	 */
	private static final long serialVersionUID = -455777535667237770L;

	private final DatasourceService datasourceService;

	
	@Inject
	public DatasourceManagerTreeHandlerRpcServiceImpl(
			DatasourceService datasourceService,
			DtoService dtoGenerator,
			SecurityService securityService,
			EntityClonerService entityClonerService
		) {
	
		super(datasourceService, dtoGenerator, securityService, entityClonerService);
		
		/* store objects */
		this.datasourceService = datasourceService;
	}
	
	@Override
	@Transactional(rollbackOn={Exception.class})
	public DatasourceDefinitionDto getDefaultDatasource() throws ServerCallFailedException {
		DatasourceDefinition ds = datasourceService.getDefaultDatasource();
		
		if(null == ds)
			return null;
		
		if(! securityService.checkRights(ds, Read.class))
			return null;
		
		return (DatasourceDefinitionDto)dtoService.createDto(ds);
	}

	@Override
	protected boolean allowDuplicateNode(AbstractDatasourceManagerNode node) {
		return node instanceof DatasourceDefinition;
	}

	@Override
	protected void nodeCloned(AbstractDatasourceManagerNode clonedNode) {
		if(! (clonedNode instanceof DatasourceDefinition))
			throw new IllegalArgumentException();
		DatasourceDefinition datasource = (DatasourceDefinition) clonedNode;
		
		datasource.setName(datasource.getName() == null ? "copy" : datasource.getName() + " (copy)");
	}

	
}
