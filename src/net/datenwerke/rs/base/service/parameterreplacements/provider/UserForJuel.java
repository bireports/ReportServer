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
 
 
package net.datenwerke.rs.base.service.parameterreplacements.provider;

import net.datenwerke.security.service.usermanager.entities.User;

public class UserForJuel {

	private String firstname = "";
	private String lastname = "";
	private String email = "";
	private String username = "";
	private long id = -1;
	private String title = "";
	
	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}
	
	public String getTitle() {
		return title;
	}

	public long getId() {
		return id;
	}

	public static UserForJuel createInstance(User user) {
		UserForJuel juelUser = new UserForJuel();
		
		if(null != user){
			if(null != user.getFirstname())
				juelUser.firstname = user.getFirstname();
			if(null != user.getLastname())
				juelUser.lastname = user.getLastname();
			if(null != user.getEmail())
				juelUser.email = user.getEmail();
			if(null != user.getUsername())
				juelUser.username = user.getUsername();
			if(null != user.getId())
				juelUser.id = user.getId();
			if(null != user.getTitle())
				juelUser.title = user.getTitle();
		}
		
		return juelUser;
	}

	
}
