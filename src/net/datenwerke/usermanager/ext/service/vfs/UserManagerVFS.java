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
 
 
package net.datenwerke.usermanager.ext.service.vfs;

import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.hooks.TreeBasedVirtualFileSystem;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;
import net.datenwerke.security.service.usermanager.entities.Group;
import net.datenwerke.security.service.usermanager.entities.OrganisationalUnit;
import net.datenwerke.security.service.usermanager.entities.User;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class UserManagerVFS extends TreeBasedVirtualFileSystem<AbstractUserManagerNode> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1891427771226007347L;
	
	private static final String FILESYSTEM_NAME = "usermanager";
	
	@Inject
	public UserManagerVFS(
		Provider<UserManagerService> userServiceProvider	
		){
		super(userServiceProvider);
	}

	@Override
	public String getFileSystemName() {
		return FILESYSTEM_NAME;
	}

	@Override
	protected String doGetNodeName(AbstractUserManagerNode node) {
		if(node instanceof OrganisationalUnit)
			return ((OrganisationalUnit)node).getName();
		else if(node instanceof Group)
			return ((Group)node).getName();
		else
			return ((User)node).getUsername();
	}

	@Override
	protected AbstractUserManagerNode instantiateFolder(String folder) {
		return new OrganisationalUnit(folder);
	}

	@Override
	protected boolean isFolder(AbstractUserManagerNode node) {
		return node instanceof OrganisationalUnit;
	}
	
	@Override
	public boolean canWriteIntoLocation(VFSLocation vfsLocation) {
		if(! vfsLocation.exists()){
			VFSLocation parentLoc = vfsLocation.getParentLocation();
			if(! parentLoc.exists())
				return false;
			
			AbstractUserManagerNode parent = getNodeByLocation(parentLoc);
			if(! (parent instanceof OrganisationalUnit))
				return false;
			
			return canWrite(parent);
		} else {
			return false;
		}
	}

}
