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
 
 
package net.datenwerke.rs.base.ext.service.reportmanager.vfs;

import net.datenwerke.rs.core.service.reportmanager.ReportService;
import net.datenwerke.rs.core.service.reportmanager.entities.AbstractReportManagerNode;
import net.datenwerke.rs.core.service.reportmanager.entities.ReportFolder;
import net.datenwerke.rs.core.service.reportmanager.entities.reports.Report;
import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFolder;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.hooks.TreeBasedVirtualFileSystem;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ReportManagerVFS extends TreeBasedVirtualFileSystem<AbstractReportManagerNode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7186418223163754943L;
	
	private static final String FILESYSTEM_NAME = "reportmanager";
	
	@Inject
	public ReportManagerVFS(
		Provider<ReportService> reportServiceProvider	
		){
		super(reportServiceProvider);
	}

	@Override
	public String getFileSystemName() {
		return FILESYSTEM_NAME;
	}

	@Override
	protected String doGetNodeName(AbstractReportManagerNode node) {
		if(node instanceof ReportFolder)
			return ((ReportFolder)node).getName();
		else
			return ((Report)node).getName();
	}

	@Override
	protected AbstractReportManagerNode instantiateFolder(String folder) {
		return new ReportFolder(folder);
	}

	@Override
	protected boolean isFolder(AbstractReportManagerNode node) {
		return node instanceof ReportFolder;
	}
	
	@Override
	public boolean canWriteIntoLocation(VFSLocation vfsLocation) {
		if(! vfsLocation.exists()){
			VFSLocation parentLoc = vfsLocation.getParentLocation();
			if(! parentLoc.exists())
				return false;
			
			AbstractReportManagerNode parent = getNodeByLocation(parentLoc);
			if(! (parent instanceof ReportFolder))
				return false;
			
			return canWrite(parent);
		} else {
			return false;
		}
	}

}
