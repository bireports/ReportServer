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
 
 
package net.datenwerke.eximport.ids;

import javax.persistence.Entity;

import net.datenwerke.eximport.hooks.ExImportIdProviderHook;
import net.datenwerke.rs.utils.jpa.EntityUtils;

import com.google.inject.Inject;

/**
 * Provides IDs for entities.
 * 
 *
 */
public class EntityIdProviderHooker implements ExImportIdProviderHook {

	private final EntityUtils jpaServices;
	
	@Inject
	public EntityIdProviderHooker(
			EntityUtils jpaServices	
		){
		
		/* store objects */
		this.jpaServices = jpaServices;
	}
	
	@Override
	public String provideIdFor(Object object) {
		if(null == object)
			return null;
		
		if(! object.getClass().isAnnotationPresent(Entity.class))
			return null;
		
		Object id = jpaServices.getId(object);
		
		if(null != id)
			return object.getClass().getName() + "-" + id.toString();
		
		return null;
	}

}
