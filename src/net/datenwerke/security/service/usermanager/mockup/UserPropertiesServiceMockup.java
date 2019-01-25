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
 
 
package net.datenwerke.security.service.usermanager.mockup;

import java.util.List;
import java.util.Set;

import net.datenwerke.security.service.usermanager.UserPropertiesService;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.security.service.usermanager.entities.UserProperty;

public class UserPropertiesServiceMockup implements UserPropertiesService {

	@Override
	public void setProperty(User user, UserProperty property) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPropertyValue(User user, String key, Object value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserProperty getProperty(User user, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPropertyValue(User user, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeProperty(User user, String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeProperty(User user, UserProperty property) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Set<UserProperty> getProperties(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperties(User user, Set<UserProperty> properties) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getPropertyKeys() {
		return null;
	}

}
