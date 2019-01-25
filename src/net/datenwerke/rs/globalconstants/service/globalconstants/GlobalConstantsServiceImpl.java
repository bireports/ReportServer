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
 
 
package net.datenwerke.rs.globalconstants.service.globalconstants;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import net.datenwerke.rs.globalconstants.service.globalconstants.entities.GlobalConstant;
import net.datenwerke.rs.utils.simplequery.annotations.SimpleQuery;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 
 *
 */
public class GlobalConstantsServiceImpl implements GlobalConstantsService {

	private final Provider<EntityManager> entityManagerProvider;
	
	@Inject
	public GlobalConstantsServiceImpl(
		Provider<EntityManager> entityManagerProvider	
		){
		this.entityManagerProvider = entityManagerProvider;
	}

	@Override
	@SimpleQuery
	public List<GlobalConstant> getAllGlobalConstants() {
		return null; // magic
	}

	@Override
	public GlobalConstant merge(GlobalConstant constant) {
		return entityManagerProvider.get().merge(constant);
	}

	@Override
	public void remove(GlobalConstant constant) {
		EntityManager em = entityManagerProvider.get();
		em.remove(em.find(constant.getClass(), constant.getId()));
	}

	@Override
	public void removeAllConstants() {
		Collection<GlobalConstant> constants = getAllGlobalConstants();
		if(null != constants){
			for(GlobalConstant constant : constants)
				remove(constant);
		}
			
	}

	@Override
	public void persist(GlobalConstant constant) {
		entityManagerProvider.get().persist(constant);
	}
	
	@Override
	public String getConstantFor(String name) {
		if(null == name)
			return null;
		for(GlobalConstant g : getAllGlobalConstants())
			if(name.equals(g.getName()))
				return g.getValue();
		return null;
	}
}
