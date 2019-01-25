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
 
 
package net.datenwerke.security.service.security.eventhandler;

import java.util.List;

import javax.persistence.EntityManager;

import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.rs.utils.simplequery.annotations.QueryByAttribute;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;
import net.datenwerke.security.service.security.entities.Ace;
import net.datenwerke.security.service.security.entities.Ace__;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 
 *
 */
public class SecurityIntegrityValidator implements EventHandler<RemoveEntityEvent>  {

	private final Provider<EntityManager> entityManagerProvider;
	
	@Inject
	public SecurityIntegrityValidator(
		Provider<EntityManager> entityManagerProvider	
		){
		
		/* store objects */
		this.entityManagerProvider = entityManagerProvider;
	}
	
	@Override
	public void handle(RemoveEntityEvent event) {
		AbstractUserManagerNode folk = (AbstractUserManagerNode) event.getObject();
		
		EntityManager em = entityManagerProvider.get();

		/* delete ace's */
		for(Ace ace : getAcesByFolk(folk))
			em.remove(ace);
	}

	@QueryByAttribute(where=Ace__.folk)
	public List<Ace> getAcesByFolk(AbstractUserManagerNode folk){
		return null; // by magic
	}

}
