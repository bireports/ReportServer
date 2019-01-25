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
 
 
package net.datenwerke.rs.terminal.service.terminal.vfs;


public class VFSObjectInfo {

	private Class<?> type;
	private String name;
	private String id;
	private boolean isFolder;

	public VFSObjectInfo(){
	}
	
	public VFSObjectInfo(Class<?> type, String name, String id, boolean isFolder) {
		setType(type);
		setName(name);
		setId(id);
		setFolder(isFolder);
	}
	
	public Class<?> getType() {
		return type;
	}
	
	public void setType(Class<?> type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof VFSObjectInfo))
			return false;
		
		VFSObjectInfo info = (VFSObjectInfo) obj;
		
		if(null != id)
			return id.equals(info.getId()) && name.equals(info.getName()) && type.equals(info.getType());
		else if(null != info.getId())
			return false;
		
		return name.equals(info.getName()) && type.equals(info.getType());
	}

	public void setFolder(boolean isFolder) {
		this.isFolder = isFolder;
	}

	public boolean isFolder() {
		return isFolder;
	}
	
}
