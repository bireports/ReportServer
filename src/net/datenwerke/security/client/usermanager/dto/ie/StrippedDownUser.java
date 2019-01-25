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
 
 
package net.datenwerke.security.client.usermanager.dto.ie;

import net.datenwerke.gf.base.client.dtogenerator.RsDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;

public class StrippedDownUser extends RsDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7289428683601291812L;

	private Long id;
	private String firstname;
	private String lastname;
	private String ou;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getOu() {
		return ou;
	}

	public void setOu(String ou) {
		this.ou = ou;
	}

	public static StrippedDownUser fromUser(UserDto user){
		StrippedDownUser sUser = new StrippedDownUser();
		
		sUser.setId(user.getId());
		sUser.setFirstname(user.getFirstname());
		sUser.setLastname(user.getLastname());
		
		return sUser;
	}
	
	@Override
	public int hashCode() {
		if(null != getId())
			return getId().hashCode();
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof StrippedDownUser))
			return false;
		
		if(null == getId())
			return super.equals(obj);
		
		return getId().equals(((StrippedDownUser)obj).getId());
	}
	
}
