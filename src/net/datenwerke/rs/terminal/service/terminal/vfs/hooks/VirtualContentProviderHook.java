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
 
 
package net.datenwerke.rs.terminal.service.terminal.vfs.hooks;

import java.util.Date;

import net.datenwerke.hookhandler.shared.hookhandler.interfaces.Hook;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocationInfo;
import net.datenwerke.rs.terminal.service.terminal.vfs.exceptions.VFSException;
import net.datenwerke.rs.terminal.service.terminal.vfs.helper.PathHelper;

public interface VirtualContentProviderHook extends Hook {

	boolean consumes(VFSLocation momentaryLocation) throws VFSException;
	
	boolean consumes(VFSLocation momentaryLocation, PathHelper path) throws VFSException;

	VFSLocation getLocation(VFSLocation momentaryLocation, PathHelper path)throws VFSException;

	String getName();

	VFSLocationInfo getLocationInfo(VFSLocation vfsLocation);

	boolean isFolder(VFSLocation vfsLocation);

	String prettyPrint(String pathInVirtualSystem);

	boolean hasContent(VFSLocation vfsLocation) throws VFSException;

	byte[] getContent(VFSLocation vfsLocation) throws VFSException;

	void setContent(VFSLocation vfsLocation, byte[] content)  throws VFSException;

	String getContentType(VFSLocation vfsLocation)  throws VFSException;

	String translatePathWay(VFSLocation location);

	public boolean enhanceNonVirtual(VFSLocation location) throws VFSException;

	Date getLastModified(VFSLocation vfsLocation);

	boolean exists(VFSLocation vfsLocation);

	long getSize(VFSLocation vfsLocation);

	boolean canWriteIntoLocation(VFSLocation vfsLocation);

	boolean isLocationDeletable(VFSLocation vfsLocation);

	void delete(VFSLocation vfsLocation) throws VFSException;

	VFSLocation create(VFSLocation vfsLocation) throws VFSException;

	void writeIntoLocation(VFSLocation vfsLocation, byte[] uploadData) throws VFSException;

	Object getObjectFor(VFSLocation vfsLocation) throws VFSException;


}
