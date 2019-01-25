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
 
 
package net.datenwerke.treedb.service.treedb;

import java.util.List;

public interface TreeDBManager<A extends AbstractNode<A>> {

	/**
	 * Searches for a node by its id
	 * 
	 * @param name
	 * @return
	 */
	public A getNodeById(long id);
	
	public List<A> getRoots();
	
	public List<A> getVirtualRoots();
	
	public List<A> getAllNodes();
	
	public void persist(A node);
	
	public A merge(A node);
	
	public void remove(A node);
	
	public void forceRemove(A node);
	
	public void move(A node, A newParent);
	
	public void move(A node, A newParent, int index);
	
	public void copy(A node, A newParent, boolean deep);

	boolean allowsMultipleRoots();

	Class<AbstractNode<?>> getBaseType();

	A updateFlags(A node, long flags);
	
	public boolean isFolder(A node); 
}
