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

import net.datenwerke.gf.service.properties.entities.Property;

/**
 * A service that provides a way to store simple properties (key/value pairs)
 * persistently in the database.
 * 
 *
 */
public interface PropertiesService {

	/**
	 * Retrieves a property by ID (note that the id is not the property's key)
	 * 
	 * @see #getPropertyByKey(String)
	 * @param id
	 * @return
	 */
	Property getPropertyById(Long id);
	
	/**
	 * Retrieves a property by key.
	 * 
	 * @param name
	 * @return
	 */
	Property getPropertyByKey(String name);
	
	/**
	 * Returns true, if a property with the given key exists.
	 * 
	 * @param key
	 * @return
	 */
	boolean containsKey(String key);
	
	/**
	 * Removes a property by key.
	 * 
	 * @param key
	 */
	void removeByKey(String key);
	
	/**
	 * Updates the property with the given key, or creates a new one if none exists.
	 * 
	 * @param key
	 * @param value
	 */
	Property setProperty(String key, String value);
	
	/**
	 * Returns the property value or null.
	 * 
	 * @param key
	 * @return
	 */
	public String get(String key);
	
	/**
	 * Persists a property
	 * 
	 * @param property
	 */
	void persist(Property property);
	
	/**
	 * Merges a property
	 * 
	 * @param property
	 * @return
	 */
	Property merge(Property property);
	
	/**
	 * Removes a property
	 * 
	 * @param property
	 */
	void remove(Property property);

	/**
	 * Returns all properties
	 * 
	 * @return
	 */
	List<Property> getAllProperties();
}
