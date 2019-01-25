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
 
 
package net.datenwerke.rs.fileserver.service.fileserver;

import net.datenwerke.rs.fileserver.service.fileserver.entities.AbstractFileServerNode;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.treedb.service.treedb.TreeDBManager;

public interface FileServerService extends TreeDBManager<AbstractFileServerNode> {

	/**
	 * Checks rights.
	 * 
	 * @param location
	 * @return
	 */
	FileServerFile createFileAtLocation(String location);
	
	FileServerFile createFileAtLocation(String location, boolean checkRights);

	/**
	 * Checks rights.
	 * 
	 * @param location
	 * @return
	 */
	FileServerFile createFileAtLocation(VFSLocation location);
	
	FileServerFile createFileAtLocation(VFSLocation location, boolean checkRights);
	
	AbstractFileServerNode getNodeByPath(String parameter);

	AbstractFileServerNode getNodeByPath(String path, boolean checkRights);



}
