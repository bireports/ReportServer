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
 
 
package net.datenwerke.rs.terminal.service.terminal.vfs.hooks;

import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.exceptions.VFSException;

public abstract class VirtualFileSystemManagerHookImpl implements
		VirtualFileSystemManagerHook {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2358496393323557347L;
	
	protected TerminalSession terminalSession;

	protected boolean readOnly;
	
	@Override
	public void init(TerminalSession terminalSession) {
		this.terminalSession = terminalSession;
	}
	
	@Override
	public TerminalSession getSession() {
		return terminalSession;
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	@Override
	public boolean isReadOnly() {
		return readOnly;
	}
	
	@Override
	public final VFSLocation createFolder(VFSLocation location, String folder){
		if(isReadOnly())
			throw new IllegalStateException("filesystem is read only");
		return doCreateFolder(location, folder);
	}

	protected abstract VFSLocation doCreateFolder(VFSLocation location, String folder);
	
	@Override
	public final void moveFilesTo(VFSLocation sources, VFSLocation target) {
		if(isReadOnly())
			throw new IllegalStateException("filesystem is read only");
		doMoveFilesTo(sources, target);
	}

	protected abstract void doMoveFilesTo(VFSLocation sources, VFSLocation target);
	
	@Override
	public void copyFilesTo(VFSLocation sources, VFSLocation target, boolean deep) throws VFSException {
		if(isReadOnly())
			throw new IllegalStateException("filesystem is read only");
		doCopyFilesTo(sources, target, deep);
	}

	protected abstract void doCopyFilesTo(VFSLocation sources, VFSLocation target, boolean deep) throws VFSException;

	public boolean hasContent(VFSLocation vfsLocation){
		return false;
	}

	public byte[] getContent(VFSLocation vfsLocation){
		return null;
	}

	public void setContent(VFSLocation vfsLocation, byte[] content){}

	public String getContentType(VFSLocation vfsLocation){
		return null;
	}
	
	@Override
	public long getSize(VFSLocation vfsLocation) {
		return 0;
	}
	
	@Override
	public boolean canWriteIntoLocation(VFSLocation vfsLocation) {
		return false;
	}
	
	@Override
	public boolean isLocationDeletable(VFSLocation vfsLocation) {
		return false;
	}
	
	@Override
	public void delete(VFSLocation vfsLocation) {
		
	}
	
	@Override
	public VFSLocation create(VFSLocation vfsLocation) throws VFSException {
		return null;
	}
	
	@Override
	public void writeIntoLocation(VFSLocation vfsLocation, byte[] uploadData) throws VFSException {
		throw new VFSException("Cannot write into: " + getFileSystemName()); 
	}

}
