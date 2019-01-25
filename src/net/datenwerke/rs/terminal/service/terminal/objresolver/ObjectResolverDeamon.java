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
 
 
package net.datenwerke.rs.terminal.service.terminal.objresolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.terminal.service.terminal.TerminalSession;
import net.datenwerke.rs.terminal.service.terminal.helpers.AutocompleteHelper;
import net.datenwerke.rs.terminal.service.terminal.hooks.TerminalSessionDeamonHook;
import net.datenwerke.rs.terminal.service.terminal.objresolver.exceptions.ObjectResolverException;
import net.datenwerke.rs.terminal.service.terminal.objresolver.hooks.ObjectResolverHook;
import net.datenwerke.rs.terminal.service.terminal.vfs.VFSLocation;
import net.datenwerke.rs.terminal.service.terminal.vfs.VirtualFileSystemDeamon;
import net.datenwerke.security.service.security.Securee;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.SecurityServiceSecuree;
import net.datenwerke.security.service.security.SecurityTarget;
import net.datenwerke.security.service.security.rights.Right;

import com.google.inject.Inject;

public class ObjectResolverDeamon implements TerminalSessionDeamonHook {

	private final HookHandlerService hookHandler;
	private final SecurityService securityService;
	
	private TerminalSession terminalSession;
	
	
	@Inject
	public ObjectResolverDeamon(
		HookHandlerService hookHandler,
		SecurityService securityService
		){
		
		/* store objects */
		this.hookHandler = hookHandler;
		this.securityService = securityService;
	}
	
	@Override
	public void init(TerminalSession terminalSession) {
		this.terminalSession = terminalSession;
		
	}

	@Override
	public void autocomplete(AutocompleteHelper autoHelper) {
		// we do no autocompletion
	}

	
	public Collection<Object> getObjects(Collection<String> locationStrings, Class<? extends Securee> securee, Class<? extends Right>... rights) throws ObjectResolverException {
		Collection<Object> filtered = new ArrayList<Object>();

		for(String locationStr : locationStrings){
			for(ObjectResolverHook objectResolver : hookHandler.getHookers(ObjectResolverHook.class)){
				if(objectResolver.consumes(locationStr, terminalSession)){
					Collection<Object> objects =  objectResolver.getObjects(locationStr, terminalSession);
					if(null == objects || (objects instanceof Collection && objects.contains(null)))
						continue;
					
					if(rights.length > 0){
						for(Object obj : objects)
							if(! (obj instanceof SecurityTarget) || securityService.checkRights((SecurityTarget)obj, securee, rights))
								filtered.add(obj);
					} else
						filtered.addAll(objects);
								
				}
			}
		}
		
		return filtered;
	}
	
	public Collection<Object> getObjects(Collection<String> locationStrings, Class<? extends Right>... rights) throws ObjectResolverException {
		return getObjects(locationStrings, SecurityServiceSecuree.class, rights);
	}
	
	public Collection<Object> getObjects(String locationStr, Class<? extends Right>... rights) throws ObjectResolverException {
		return getObjects(locationStr, SecurityServiceSecuree.class, rights);
	}
	
	public Collection<Object> getObjects(String locationStr,  Class<? extends Securee> securee, Class<? extends Right>... rights) throws ObjectResolverException {
		return getObjects(Collections.singleton(locationStr), securee, rights);
	}
	
	public Object getObject(String locationStr, Class<? extends Right>... rights) throws ObjectResolverException {
		return getObject(locationStr, SecurityServiceSecuree.class, rights);
	}
	
	public Object getObject(String locationStr,  Class<? extends Securee> securee, Class<? extends Right>... rights) throws ObjectResolverException {
		Collection<Object> col = getObjects(Collections.singleton(locationStr), securee, rights);
		if(col.isEmpty())
			return null;
		return col.iterator().next();
	}
	
	
	public Collection<VFSLocation> getLocations(Collection<String> locationStrings, Class<? extends Securee> securee, Class<? extends Right>... rights) throws ObjectResolverException {
		Collection<Object> objects = getObjects(locationStrings, securee, rights);
		
		VirtualFileSystemDeamon vfs = terminalSession.getFileSystem();
		
		Collection<VFSLocation> locations = new HashSet<VFSLocation>();
		
		for(Object object : objects)
			locations.add(vfs.getLocationFor(object));
		
		return locations;
	}
	
	public Collection<VFSLocation> getLocations(Collection<String> locationStrings, Class<? extends Right>... rights) throws ObjectResolverException {
		return getLocations(locationStrings, SecurityServiceSecuree.class, rights);
	}
	
	public Collection<VFSLocation> getLocations(String locationStr, Class<? extends Right>... rights) throws ObjectResolverException {
		return getLocations(locationStr, SecurityServiceSecuree.class, rights);
	}
	
	public Collection<VFSLocation> getLocations(String locationStr,  Class<? extends Securee> securee, Class<? extends Right>... rights) throws ObjectResolverException {
		return getLocations(Collections.singleton(locationStr), securee, rights);
	}
	

}
