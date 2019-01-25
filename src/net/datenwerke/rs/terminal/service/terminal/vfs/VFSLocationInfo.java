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
 
 
package net.datenwerke.rs.terminal.service.terminal.vfs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VFSLocationInfo {

	private List<VFSObjectInfo> childInfos = new ArrayList<VFSObjectInfo>();

	private VFSObjectInfo info;
	private VFSLocation location;
	
	public VFSLocationInfo(VFSLocation location, VFSObjectInfo info){
		this.location = location;
		this.info = info;
	}
	
	public VFSLocation getLocation() {
		return location;
	}
	
	public List<VFSObjectInfo> getChildInfos() {
		return childInfos;
	}

	public void setChildInfos(List<VFSObjectInfo> childInfos) {
		this.childInfos = childInfos;
	}
	
	public void addChildInfo(VFSObjectInfo childInfo){
		childInfos.add(childInfo);
	}
	
	public void addChildInfos(Collection<VFSObjectInfo> childInfos){
		this.childInfos.addAll(childInfos);
	}
	
	public void setInfo(VFSObjectInfo info) {
		this.info = info;
	}

	public VFSObjectInfo getInfo() {
		return info;
	}
	
	@Override
	public int hashCode() {
		return location.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(! (obj instanceof VFSLocationInfo))
			return false;
		return location.equals(((VFSLocationInfo)obj).getLocation());
	}


}
