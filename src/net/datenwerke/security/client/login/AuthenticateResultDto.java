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
 
 
package net.datenwerke.security.client.login;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gf.base.client.dtogenerator.RsDto;
import net.datenwerke.security.client.usermanager.dto.UserDto;

public class AuthenticateResultDto extends RsDto{
	
	private static final long serialVersionUID = 184720943530827079L;
	
	private UserDto user;
	private boolean valid;
	private List<AuthenticateResultInfo> info;
	
	public AuthenticateResultDto() {
		super();
	}
	
	public AuthenticateResultDto(boolean valid, UserDto user) {
		super();
		setUser(user);
		setValid(valid);
	}
	
	public UserDto getUser() {
		return user;
	}
	
	public void setUser(UserDto user){
		this.user = user;
	}
	
	public boolean isValid(){
		return valid;
	}
	
	public void setValid(boolean valid){
		this.valid = valid;
	}
	
	public void setInfo(List<AuthenticateResultInfo> info){
		this.info = info;;
	}
	
	public List<AuthenticateResultInfo> getInfo(){
		return info;
	}
	
	public void addInfo(AuthenticateResultInfo info){
		if(null == getInfo()){
			setInfo(new ArrayList<AuthenticateResultInfo>());
		}
		
		getInfo().add(info);
	}
	
	AuthenticateResultInfo wl_1;
}
