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
 
 
package net.datenwerke.security.service.treedb;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.security.service.eventlogger.annotations.FireForceRemoveEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FirePersistEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireRemoveEntityEvents;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.security.annotation.UpdateOwner;
import net.datenwerke.security.service.security.exceptions.ViolatedSecurityException;
import net.datenwerke.security.service.security.rights.Delete;
import net.datenwerke.security.service.security.rights.Read;
import net.datenwerke.security.service.security.rights.Write;
import net.datenwerke.security.service.treedb.entities.SecuredAbstractNode;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public abstract class SecuredTreeDBManagerImpl<A extends SecuredAbstractNode<A>> extends LoggedTreeDbManagerImpl<A> {

	@Inject
	protected SecurityService securityService;
	
	@Override
	public List<A> getVirtualRoots() {
		List<A> virtualRoots = new ArrayList<A>();
		
		fillVirtualRoots(getRoots(), virtualRoots);
		
		return virtualRoots;
	}
	
	protected void fillVirtualRoots(List<A> nodes, List<A> virtualRoots) {
		for(A node : nodes){
			if(! isFolder(node))
				continue;
			
			if(securityService.checkRights(node, Read.class))
				virtualRoots.add(node);
			else
				fillVirtualRoots(node.getChildren(), virtualRoots);
		}
	}

	@Override
	public void move(A node, A newParent) {
		move(node, newParent, true);
	}
	
	public void move(A node, A newParent, boolean checkRights) {
		if(null == node || null == newParent)
			throw new IllegalArgumentException();

		if(checkRights)
			testMoveRights(node,newParent);
	
		super.move(node, newParent);
	}
	
	@Override
	public void move(A node, A newParent, int index) {
		move(node, newParent, index, true);
	}
	
	public void move(A node, A newParent, int index, boolean checkRights) {
		if(null == node || null == newParent)
			throw new IllegalArgumentException();

		if(checkRights)
			testMoveRights(node,newParent);
	
		super.move(node, newParent, index);
	}

	
	protected void testMoveRights(A node, A newParent) {
		if(! securityService.checkRights(newParent, Read.class, Write.class) ||
		   ! securityService.checkRights(node, Read.class, Write.class) )
			throw new ViolatedSecurityException();
	}

	@Override
	protected A copy(A node, A newParent) {
		testCopyRights(node, newParent);
		
		return super.copy(node, newParent);
	}
	
	public  void copy(A source, A target, boolean deep, boolean checkRights) {
		if(checkRights)
			testCopyRights(source, target);
		A copiedNode = super.copy(source, target);
		
		if(deep)
			for(A child : source.getChildren())
				copy(child, copiedNode, true, checkRights);
	}
	
	
	protected void testCopyRights(A node, A newParent) {
		if(! securityService.checkRights(newParent, Read.class, Write.class) ||
		   ! securityService.checkRights(node, Read.class))
				throw new ViolatedSecurityException();
	}

	@Override
	@FireRemoveEntityEvents
	public void remove(A node) {
		super.remove(node);
	};
	
	@Override
	@FireForceRemoveEntityEvents
	 public void forceRemove(A node) {
		super.forceRemove(node);
	};
	

	@Override
	@UpdateOwner(name="node")
	@FirePersistEntityEvents
	public void persist(@Named("node")A node){
		super.persist(node);
	}
	
}
