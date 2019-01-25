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
 
 
package net.datenwerke.rs.core.service.datasourcemanager.eventhandlers;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.datenwerke.rs.core.service.datasourcemanager.DatasourceService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer__;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.security.service.eventlogger.jpa.ForceRemoveEntityEvent;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class HandleDatasourceForceRemoveEventHandler implements
		EventHandler<ForceRemoveEntityEvent> {

	@Inject private Provider<EntityManager> entityManagerProvider;
	@Inject private DatasourceService datasourceService;
	
	@Override
	public void handle(ForceRemoveEntityEvent event) {
		DatasourceDefinition ds = (DatasourceDefinition) event.getObject();
		
		EntityManager em = entityManagerProvider.get();
		Query query = em.createQuery("FROM " + DatasourceContainer.class.getSimpleName() + " WHERE " + DatasourceContainer__.datasource + " = :ds");
		query.setParameter("ds", ds);
		List<DatasourceContainer> containers = query.getResultList();
		
		if(null != containers && ! containers.isEmpty()){
			for(DatasourceContainer container : containers){
				container.setDatasource(null);
				datasourceService.merge(container);
			}
		}
	}

}
