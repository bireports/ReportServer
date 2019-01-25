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
 
 
package net.datenwerke.rs.incubator.service.versioning.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.datenwerke.rs.incubator.service.versioning.RevisionEntityListener;
import net.datenwerke.security.service.authenticator.AuthenticatorService;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import com.google.inject.Inject;
import com.google.inject.Provider;

@Entity
@Table(name="REVISION")
@RevisionEntity(RevisionEntityListener.class)
public class Revision extends DefaultRevisionEntity implements Comparable<Revision>{

	@Inject
	private static Provider<AuthenticatorService> authenticatorServiceProvider;
	
	private static final long serialVersionUID = 6478004157779519705L;

	private long userId;
	
	public Revision() {
		try{
			this.userId = authenticatorServiceProvider.get().getCurrentUser().getId();
		}catch (Exception e) {
		}
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Override
	public int compareTo(Revision o) {
		return Long.valueOf(o.getId()).compareTo(Long.valueOf(getId()));
	}
	
}
