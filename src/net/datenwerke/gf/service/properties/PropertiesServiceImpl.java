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
 
 
package net.datenwerke.gf.service.properties;

import java.util.List;

import javax.persistence.EntityManager;

import net.datenwerke.gf.service.properties.entities.Property;
import net.datenwerke.gf.service.properties.entities.Property__;
import net.datenwerke.rs.utils.simplequery.annotations.QueryByAttribute;
import net.datenwerke.rs.utils.simplequery.annotations.QueryById;
import net.datenwerke.rs.utils.simplequery.annotations.SimpleQuery;
import net.datenwerke.security.service.eventlogger.annotations.FireMergeEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FirePersistEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireRemoveEntityEvents;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class PropertiesServiceImpl implements PropertiesService {

	private final Provider<EntityManager> entityManagerProvider;
	
	@Inject
	public PropertiesServiceImpl(Provider<EntityManager> entityManagerProvider) {
		this.entityManagerProvider = entityManagerProvider;
	}

	@Override
	@QueryById
	public Property getPropertyById(Long id) {
		return null; // magic
	}

	@Override
	@QueryByAttribute(where=Property__.key)
	public Property getPropertyByKey(String key) {
		return null; // magic
	}
	
	@Override
	@SimpleQuery
	public List<Property> getAllProperties(){
		return null; // magic
	}
	
	@Override
	public String get(String key) {
		Property prop = getPropertyByKey(key);
		return null == prop ? null : prop.getValue();
	}

	@Override
	@FirePersistEntityEvents
	public void persist(Property property) {
		entityManagerProvider.get().persist(property);
	}

	@Override
	@FireMergeEntityEvents
	public Property merge(Property property) {
		return entityManagerProvider.get().merge(property);
	}

	@Override
	@FireRemoveEntityEvents
	public void remove(Property property) {
		EntityManager em = entityManagerProvider.get();
		property = em.find(Property.class, property.getId());
		if(null != property)
			em.remove(property);
	}

	@Override
	public void removeByKey(String key) {
		Property property = getPropertyByKey(key);
		if(null != property)
			remove(property);
	}
	
	@Override
	public boolean containsKey(String key) {
		Property property = getPropertyByKey(key);
		return null != property;
	}

	@Override
	public Property setProperty(String key, String value) {
		Property property = getPropertyByKey(key);
		if(null != property){
			property.setValue(value);
			merge(property);
		} else {
			property = new Property(key, value);
			persist(property);
		}
		return property;
	}

}
