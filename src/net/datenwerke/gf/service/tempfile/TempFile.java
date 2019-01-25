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
 
 
package net.datenwerke.gf.service.tempfile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

import net.datenwerke.security.service.usermanager.entities.User;

public class TempFile {
	
	private String webId;
	private File file;
	private Collection<User> permittedUsers;
	
	private String mimeType;
	private String downloadName;
	
	private Date created;
	private Date lastAccessed;
	
	
	public TempFile(java.io.File file) {
		this(file, UUID.randomUUID().toString());
	}
	
	public TempFile(File file, String webId) {
		this(file, webId, null);
	}

	public TempFile(File file, User permittedUser) {
		this(file, UUID.randomUUID().toString(), Collections.singleton(permittedUser));
	}
	
	public TempFile(File file, Collection<User> permittedUsers) {
		this(file, UUID.randomUUID().toString(), permittedUsers);
	}

	public TempFile(File file, String webId, Collection<User> permittedUsers) {
		this.file = file;
		this.webId = webId;
		this.permittedUsers = permittedUsers;
		
		this.created = new Date();
	}


	public String getWebId() {
		return webId;
	}

	public File getFile() {
		return file;
	}

	public Collection<User> getPermittedUsers() {
		if(null == permittedUsers)
			permittedUsers = new ArrayList<User>();
		return permittedUsers;
	}

	public boolean isPermittedUser(User user){
		if(null == permittedUsers || permittedUsers.isEmpty())
			return true;
		
		return permittedUsers.contains(user);
	}
	
	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getDownloadName() {
		return downloadName;
	}

	public void setDownloadName(String downloadName) {
		this.downloadName = downloadName;
	}

	public Date getCreated() {
		return created;
	}

	public Date getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(Date lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
	
	public void setLastAccessed() {
		this.lastAccessed = new Date();
	}
	
}
