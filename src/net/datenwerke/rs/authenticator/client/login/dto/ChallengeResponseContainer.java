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
 
 
package net.datenwerke.rs.authenticator.client.login.dto;

import java.io.Serializable;



public class ChallengeResponseContainer implements Serializable{

	private static final long serialVersionUID = -8438464227531800394L;
	
	private long id;
	private byte[] challenge;
	private byte[] response;

	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public byte[] getChallenge() {
		return challenge;
	}

	public void setChallenge(byte[] challenge) {
		this.challenge = challenge;
	}
	
	public byte[] getResponse() {
		return response;
	}
	
	public void setResponse(byte[] response) {
		this.response = response;
	}

}
