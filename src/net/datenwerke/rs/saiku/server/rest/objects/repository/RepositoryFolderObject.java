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
 
 
package net.datenwerke.rs.saiku.server.rest.objects.repository;

import java.util.List;

import net.datenwerke.rs.saiku.server.rest.objects.acl.enumeration.AclMethod;


public class RepositoryFolderObject implements IRepositoryObject {

	private Type type;
	private String name;
	private String id;
	private String path;
	private List<IRepositoryObject> repoObjects;
	private List<AclMethod> acl;

	public RepositoryFolderObject(String name, String id, String path, List<AclMethod> acl, List<IRepositoryObject> repoObjects) {
		this.type = Type.FOLDER;
		this.name = name;
		this.id = id;
		this.path = path;
		this.repoObjects = repoObjects;
		this.acl = acl;
	}
	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}
	
	public String getPath() {
		return path;
	}
	
	public List<AclMethod> getAcl() {
		return acl;
	}
	
	public List<IRepositoryObject> getRepoObjects() {
		return repoObjects;
	}

}
