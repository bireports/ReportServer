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

import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.security.service.eventlogger.annotations.FireForceRemoveEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireMergeEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FirePersistEntityEvents;
import net.datenwerke.security.service.eventlogger.annotations.FireRemoveEntityEvents;
import net.datenwerke.security.service.treedb.events.MoveNodeEvent;
import net.datenwerke.treedb.service.treedb.AbstractNode;
import net.datenwerke.treedb.service.treedb.TreeDBManagerImpl;

import com.google.inject.Inject;

public abstract class LoggedTreeDbManagerImpl<A extends AbstractNode<A>> extends TreeDBManagerImpl<A> {

	@Inject
	protected EventBus eventBus; 
	
	public void move(A node, A newParent) {
		eventBus.fireEvent(new MoveNodeEvent(node, newParent));
		super.move(node, newParent);
	}
	
	public void move(A node, A newParent, int index) {
		eventBus.fireEvent(new MoveNodeEvent(node, newParent, index));
		super.move(node, newParent, index);
	}
	
	@FirePersistEntityEvents
	public void persist(A node) {
		super.persist(node);
	}
	
	@FireMergeEntityEvents
	public A merge(A node) {
		return super.merge(node);
	}
	
	@FireRemoveEntityEvents
	public void remove(A node) {
		super.remove(node);
	}
	
	@FireForceRemoveEntityEvents
	public void forceRemove(A node) {
		super.forceRemove(node);
	}
}
