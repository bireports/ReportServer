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
 
 
package net.datenwerke.rs.base.ext.service.datasourcemanager.vfs;

import net.datenwerke.rs.core.service.datasourcemanager.DatasourceService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.AbstractDatasourceManagerNode;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceFolder;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.hooks.TreeBasedVirtualFileSystem;
import net.datenwerke.security.service.usermanager.entities.OrganisationalUnit;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DatasourceManagerVFS extends TreeBasedVirtualFileSystem<AbstractDatasourceManagerNode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186418223163754943L;
	
	private static final String FILESYSTEM_NAME = "datasources";
	
	@Inject
	public DatasourceManagerVFS(
		Provider<DatasourceService> datasourceServiceProvider	
		){
		super(datasourceServiceProvider);
	}

	@Override
	public String getFileSystemName() {
		return FILESYSTEM_NAME;
	}

	@Override
	protected String doGetNodeName(AbstractDatasourceManagerNode node) {
		if(node instanceof DatasourceFolder)
			return ((DatasourceFolder)node).getName();
		else
			return ((DatasourceDefinition)node).getName();
	}

	@Override
	protected AbstractDatasourceManagerNode instantiateFolder(String folder) {
		return new DatasourceFolder(folder);
	}

	@Override
	protected boolean isFolder(AbstractDatasourceManagerNode node) {
		return node instanceof DatasourceFolder;
	}
	
	@Override
	public boolean canWriteIntoLocation(VFSLocation vfsLocation) {
		if(! vfsLocation.exists()){
			VFSLocation parentLoc = vfsLocation.getParentLocation();
			if(! parentLoc.exists())
				return false;
			
			AbstractDatasourceManagerNode parent = getNodeByLocation(parentLoc);
			if(! (parent instanceof DatasourceFolder))
				return false;
			
			return canWrite(parent);
		} else {
			return false;
		}
	}


}
