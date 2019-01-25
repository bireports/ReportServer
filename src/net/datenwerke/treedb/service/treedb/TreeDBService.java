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
 
 
package net.datenwerke.treedb.service.treedb;



/**
 * 
 *
 */
public interface TreeDBService {

	/**
	 * Merges the given node with the representation in the database and returns the
	 * merged node.
	 * 
	 * @param <N> The type of the node
	 * @param node The node holding the new data
	 * @return The merged node
	 */
	public <N extends AbstractNode<N>> N merge(N node);
	
	/**
	 * Saves the given node to the database
	 * 
	 * @param <N> The type of the node
	 * @param node The node itself
	 */
	public <N extends AbstractNode<N>> void persist(N node);
	
	/**
	 * Removes the given node from the database
	 * 
	 * @param <N> The type of the node
	 * @param node The node to be removed
	 */
	public <N extends AbstractNode<N>> void remove(N node);

	public <N extends AbstractNode<N>> N updateFlags(N node, long flags);

	public <A extends AbstractNode<?>> Class<? extends TreeDBManager<? extends A>> getManagerClassForNode(Class<A> nodeType);
}
