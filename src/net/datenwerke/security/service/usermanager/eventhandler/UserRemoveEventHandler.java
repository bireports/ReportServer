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
 
 
package net.datenwerke.security.service.usermanager.eventhandler;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.security.service.eventlogger.jpa.AfterMergeEntityEvent;
import net.datenwerke.security.service.eventlogger.jpa.MergeEntityEvent;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;
import net.datenwerke.security.service.treedb.entities.SecuredAbstractNode;
import net.datenwerke.security.service.treedb.entities.SecuredAbstractNode__;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserRemoveEventHandler implements EventHandler<RemoveEntityEvent> {

	private final Provider<EntityManager> entityManagerProvider;
	private final EventBus eventBus;
	
	@Inject
	public UserRemoveEventHandler(
		Provider<EntityManager> entityManagerProvider,
		EventBus eventBus) {
		this.entityManagerProvider = entityManagerProvider;
		this.eventBus = eventBus;
	}



	@Override
	public void handle(RemoveEntityEvent event) {
		User user = (User) event.getObject();
		
		EntityManager em = entityManagerProvider.get();
		
		Query query = em.createQuery("FROM " + SecuredAbstractNode.class.getName() + " WHERE " + SecuredAbstractNode__.owner + " = :owner");
		query.setParameter("owner", user);
		List<SecuredAbstractNode> nodes = query.getResultList();
		if(null != nodes && ! nodes.isEmpty()){
			for(SecuredAbstractNode node : nodes){
				node.setOwner(null);
				
				eventBus.fireEvent(new MergeEntityEvent(node));
				
				node = em.merge(node);
				
				eventBus.fireEvent(new AfterMergeEntityEvent(node));
			}
				
		}
	}

}
