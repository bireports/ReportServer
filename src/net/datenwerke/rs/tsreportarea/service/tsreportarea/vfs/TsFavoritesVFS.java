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
 
 
package net.datenwerke.rs.tsreportarea.service.tsreportarea.vfs;

import net.datenwerke.rs.teamspace.service.teamspace.TeamSpaceService;
import net.datenwerke.rs.teamspace.service.teamspace.entities.TeamSpace;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.hooks.TreeBasedVirtualFileSystem;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.TsDiskService;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.AbstractTsDiskNode;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskFolder;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskGeneralReference;
import net.datenwerke.rs.tsreportarea.service.tsreportarea.entities.TsDiskRoot;
import net.datenwerke.security.service.security.rights.Right;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TsFavoritesVFS extends TreeBasedVirtualFileSystem<AbstractTsDiskNode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186418223163754943L;
	
	private static final String FILESYSTEM_NAME = "tsreport";

	private final Provider<TeamSpaceService> teamSpaceService;
	
	@Inject
	public TsFavoritesVFS(
		Provider<TsDiskService> favoritesServiceProvider,
		Provider<TeamSpaceService> teamSpaceService	
		){
		super(favoritesServiceProvider);
		this.teamSpaceService = teamSpaceService;
	}

	@Override
	public String getFileSystemName() {
		return FILESYSTEM_NAME;
	}

	@Override
	protected String doGetNodeName(AbstractTsDiskNode node) {
		if(node instanceof TsDiskRoot)
			return ((TsDiskRoot)node).getTeamSpace().getName();
		else
			return node.getName();
	}

	@Override
	protected AbstractTsDiskNode instantiateFolder(String folder) {
		return new TsDiskFolder(folder);
	}

	@Override
	protected boolean isFolder(AbstractTsDiskNode node) {
		return node instanceof TsDiskFolder || node instanceof TsDiskRoot;
	}

	@Override
	public boolean hasContent(VFSLocation vfsLocation) {
		AbstractTsDiskNode node = getNodeByLocation(vfsLocation);
		
		checkRead(node);
		
		if(node instanceof TsDiskGeneralReference)
			return ((TsDiskGeneralReference)node).hasData();
		
		return false;
	}
	
	@Override
	public byte[] getContent(VFSLocation vfsLocation) {
		AbstractTsDiskNode node = getNodeByLocation(vfsLocation);
		if(null == node)
			return null;
		
		checkRead(node);
		
		if(node instanceof TsDiskGeneralReference)
			return ((TsDiskGeneralReference)node).getData();
		
		return null;
	}
	
	
	@Override
	public long getSize(VFSLocation location) {
		AbstractTsDiskNode node = getNodeByLocation(location);
		if(null == node)
			return 0;
		
		checkRead(node);
		
		if(node instanceof TsDiskGeneralReference)
			return ((TsDiskGeneralReference)node).getSize();
		
		return 0;
	}
	
	@Override
	public String getContentType(VFSLocation vfsLocation) {
		AbstractTsDiskNode node = getNodeByLocation(vfsLocation);
		if(null == node)
			return null;
		
		checkRead(node);
		
		if(node instanceof TsDiskGeneralReference)
			return ((TsDiskGeneralReference)node).getDataContentType();
		
		return null;
	}

	@Override
	protected boolean canDo(AbstractTsDiskNode node,
			Class<? extends Right>... rights) {
		if(null == node){
			return true;
		}
		
		TeamSpace ts = ((TsDiskService)treeDBManagerProvider.get()).getTeamSpaceFor(node);
		if(null == ts)
			return false;
		
		return teamSpaceService.get().isUser(ts);
	}
	
	@Override
	public boolean canWriteIntoLocation(VFSLocation vfsLocation) {
		if(! vfsLocation.exists()){
			VFSLocation parentLoc = vfsLocation.getParentLocation();
			if(! parentLoc.exists())
				return false;
			
			AbstractTsDiskNode parent = getNodeByLocation(parentLoc);
			if(! isFolder(parent))
				return false;
			
			return canWrite(parent);
		} else {
			return false;
		}
	}
}
