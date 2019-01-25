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
 
 
package net.datenwerke.security.client.login.resultinfos;

import java.util.Date;

import net.datenwerke.security.client.login.AuthenticateResultInfo;

public class AccountExpiredAuthenticateResultInfo extends AuthenticateResultInfo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3828289383438268043L;
	
	private Date expiredSince;
	
	public AccountExpiredAuthenticateResultInfo() {
		super();
	}
	
	public AccountExpiredAuthenticateResultInfo(Date expiredSince){
		super();
		setExpiredSince(expiredSince);
	}

	public Date getExpiredSince() {
		return expiredSince;
	}

	public void setExpiredSince(Date expiredSince) {
		this.expiredSince = expiredSince;
	}
	
	
}
