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
 
 
package net.datenwerke.security.service.usermanager;

import java.util.List;
import java.util.Set;

import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.security.service.usermanager.entities.UserProperty;


public interface UserPropertiesService {

	public void setProperty(User user, UserProperty property);
	public void setPropertyValue(User user, String key, Object value) ;

	public UserProperty getProperty(User user, String key);
	public String getPropertyValue(User user, String key);
	
	public void removeProperty(User user, String key);
	public boolean removeProperty(User user, UserProperty property) ;
	
	public Set<UserProperty> getProperties(User user) ;
	public void setProperties(User user, Set<UserProperty> properties);
	
	public List<String> getPropertyKeys();

}
