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
 
 
package net.datenwerke.rs.core.service.datasourcemanager;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import net.datenwerke.rs.core.service.datasourcemanager.annotations.DefaultDatasource;
import net.datenwerke.rs.core.service.datasourcemanager.annotations.ReportServerDatasourceDefinitions;
import net.datenwerke.rs.core.service.datasourcemanager.entities.AbstractDatasourceManagerNode;
import net.datenwerke.rs.core.service.datasourcemanager.entities.AbstractDatasourceManagerNode__;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinitionConfig;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition__;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceFolder;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceFolder__;
import net.datenwerke.rs.utils.simplequery.PredicateType;
import net.datenwerke.rs.utils.simplequery.annotations.QueryByAttribute;
import net.datenwerke.rs.utils.simplequery.annotations.QueryById;
import net.datenwerke.rs.utils.simplequery.annotations.SimpleQuery;
import net.datenwerke.security.service.eventlogger.annotations.FireMergeEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireRemoveEntityEvents;
import net.datenwerke.security.service.treedb.SecuredTreeDBManagerImpl;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

/**
 * 
 *
 */
public class DatasourceServiceImpl extends SecuredTreeDBManagerImpl<AbstractDatasourceManagerNode> implements DatasourceService {

	private final Provider<Set<Class<? extends DatasourceDefinition>>> installedDataSourceDefinitions;
	private final Provider<String> defaultDatasourceProvider;
	private final Provider<EntityManager> entityManagerProvider;
	
	@Inject
	public DatasourceServiceImpl(
		Provider<EntityManager> entityManagerProvider,
		@ReportServerDatasourceDefinitions Provider<Set<Class<? extends DatasourceDefinition>>> installedDataSourceDefinitions,
		@DefaultDatasource Provider<String> defaultDatasourceProvider
		){
		
		/* store objects */
		this.entityManagerProvider = entityManagerProvider;
		this.installedDataSourceDefinitions = installedDataSourceDefinitions;
		this.defaultDatasourceProvider = defaultDatasourceProvider;
	}

	public Set<Class<? extends DatasourceDefinition>> getInstalledDataSourceDefinitions() {
		return installedDataSourceDefinitions.get();
	}
	
	@QueryByAttribute(where=DatasourceDefinition__.name)
	@Override
	public DatasourceDefinition getDatasourceByName(String name) {
		return null; // by magic
	}

	@QueryByAttribute(where=DatasourceFolder__.name)
	@Override
	public DatasourceFolder getDatasourceFolderByName(@Named("name") String name) {
		return null; // by magic
	} 
	
	@Override
	public String getDefaultDatasourceId() {
		return defaultDatasourceProvider.get();
	}
	
	
	@Override
	public DatasourceDefinition getDefaultDatasource() {
		String id = getDefaultDatasourceId();
		if(null == id)
			return null;
		
		try{
			AbstractDatasourceManagerNode node = getNodeById(Long.valueOf(id));
			if(node instanceof DatasourceDefinition)
				return (DatasourceDefinition) node;
		} catch(Exception e){
		}
		
		return null;
	}
	
	@Override
	@QueryByAttribute(where=AbstractDatasourceManagerNode__.parent,type=PredicateType.IS_NULL)
	public List<AbstractDatasourceManagerNode> getRoots() {
		return null; // magic
	}
	
	@Override
	@SimpleQuery
	public List<AbstractDatasourceManagerNode> getAllNodes(){
		return null;
	}
	
	@Override
	@QueryById
	public AbstractDatasourceManagerNode getNodeById(long id) {
		return null; // magic
	}

	@Override
	@FireMergeEntityEvents
	public DatasourceContainer merge(DatasourceContainer container) {
		return entityManagerProvider.get().merge(container);
	}

	@Override
	@FireRemoveEntityEvents
	public void remove(DatasourceDefinitionConfig datasourceConfig) {
		if(null == datasourceConfig)
			return;
		EntityManager em = entityManagerProvider.get();
		datasourceConfig = em.find(datasourceConfig.getClass(), datasourceConfig.getId());
		if(null != datasourceConfig)
			em.remove(datasourceConfig);
	}

}
