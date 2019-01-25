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
 
 
package net.datenwerke.security.service.treedb.events;

import net.datenwerke.security.service.eventlogger.DwLoggedEvent;
import net.datenwerke.treedb.service.treedb.AbstractNode;

public class MoveNodeEvent extends DwLoggedEvent {

	public MoveNodeEvent(AbstractNode node, AbstractNode newParent) {
		super(
			"node_id", null != node ? node.getId() : "NULL",
			"node_type", null != node ? node.getClass() : "NULL",
			"new_parent_id", null != node ? newParent.getId() : "NULL",
			"new_parent_type", null != node ? newParent.getClass() : "NULL"
		);
	}
	
	public MoveNodeEvent(AbstractNode node, AbstractNode newParent, int index) {
		super(
			"node_id", null != node ? node.getId() : "NULL",
			"node_type", null != node ? node.getClass() : "NULL",
			"new_parent_id", null != node ? newParent.getId() : "NULL",
			"new_parent_type", null != node ? newParent.getClass() : "NULL",
			"index", index
		);
	}

	@Override
	public String getLoggedAction() {
		return "NODE_MOVED";
	}

}
