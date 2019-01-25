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
 
 
package net.datenwerke.rs.terminal.service.terminal.objresolver.baseresolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.rs.terminal.service.terminal.objresolver.hooks.ObjectResolverHook;
import net.datenwerke.rs.utils.jpa.EntityUtils;

import org.hibernate.proxy.HibernateProxy;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class HqlResolver implements ObjectResolverHook {

	private final Provider<EntityManager> entityManagerProvider;
	private final EntityUtils entityUtils;
	
	@Inject
	public HqlResolver(
		Provider<EntityManager> entityManagerProvider,
		EntityUtils entityUtils
		){
		
		/* store objects */
		this.entityManagerProvider = entityManagerProvider;
		this.entityUtils = entityUtils;
	}
	
	
	@Override
	public boolean consumes(String locationStr, TerminalSession terminalSession) {
		return null != locationStr && locationStr.startsWith("hql:");
	}

	@Override
	public Collection<Object> getObjects(String locationStr,
			TerminalSession terminalSession) throws ObjectResolverException {
		Collection<Object> result = new ArrayList<Object>();
		
		String query = locationStr.substring(4);
		
		try{
			/* execute query */
			List resultList = entityManagerProvider.get().createQuery(query).getResultList();

			/* simple result */
			if(! resultList.isEmpty()){
				Object first = resultList.get(0);
				if(first instanceof HibernateProxy)
					first = ((HibernateProxy)first).getHibernateLazyInitializer().getImplementation();
				if(entityUtils.isEntity(first))
					for(Object obj : resultList)
						result.add(obj);
			}
			
		} catch(Exception e){
			String msg =  null != e.getMessage() ? e.getMessage() : "";
			if(null != e.getCause()){
				msg += null != e.getCause().getMessage() ? e.getCause().getMessage() : "";
				if(null != e.getCause().getCause())
					msg += null != e.getCause().getCause().getMessage() ? e.getCause().getCause().getMessage() : "";
			}
			
			throw new ObjectResolverException(msg, e);
		}
		
		return result;
	}


}
