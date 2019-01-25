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
 
 
package net.datenwerke.rs.terminal.service.terminal;

import java.util.Collection;

import net.datenwerke.rs.terminal.service.terminal.exceptions.SessionNotFoundException;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.rs.terminal.service.terminal.vfs.exceptions.VFSException;
import net.datenwerke.rs.terminal.service.terminal.vfs.hooks.VirtualFileSystemManagerHook;


public interface TerminalService {

	/**
	 * Initializes a new {@link TerminalSession}.
	 * 
	 * @return The newly initialized {@link TerminalSession}
	 */
	public TerminalSession initTerminalSession();
	
	/**
	 * Returns the {@link TerminalSession} identified by the given <i>sessionId</i>
	 * 
	 * @param sessionId The ID of the desired {@link TerminalSession}
	 * @return The {@link TerminalSession}
	 * @throws SessionNotFoundException
	 */
	public TerminalSession getTerminalSession(String sessionId) throws SessionNotFoundException;
	
	/**
	 * Closes the {@link TerminalSession} identified by the given <i>sessionId</i>
	 * 
	 * @param sessionId The ID of the {@link TerminalSession}
	 */
	public void closeTerminalSession(String sessionId);
	
	public Object getObjectByLocation(Class<? extends VirtualFileSystemManagerHook> vfs, String location) throws VFSException;
	
	public Object getObjectByLocation(String location) throws ObjectResolverException;
	
	public Object getObjectByLocation(String location, boolean checkRights) throws ObjectResolverException;

	Object getObjectByLocation(Class<? extends VirtualFileSystemManagerHook> vfsManager,
			String location, boolean checkRights) throws VFSException;

	public Collection<Object> getObjectsByLocation(String location, boolean checkRights) throws ObjectResolverException;
	
	public Collection<Object> getObjectsByLocation(String location) throws ObjectResolverException;

	TerminalSession getUnscopedTerminalSession();

	
}
