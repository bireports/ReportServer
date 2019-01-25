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
 
 
package net.datenwerke.gf.client.treedb.dnd;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.sencha.gxt.dnd.core.client.DndDropEvent;
import com.sencha.gxt.dnd.core.client.DropTarget;
import com.sencha.gxt.dnd.core.client.TreeDropTarget;
import com.sencha.gxt.fx.client.DragCancelEvent;
import com.sencha.gxt.fx.client.DragCancelEvent.DragCancelHandler;
import com.sencha.gxt.fx.client.DragEndEvent;
import com.sencha.gxt.fx.client.DragEndEvent.DragEndHandler;


public class UITreeDragSource extends RsTreePanelDragSource<AbstractNodeDto> {

	private final UITree tree;
	
	public UITreeDragSource(final UITree tree) {
		super(tree);
		
		this.tree = tree;
		
		draggable.addDragCancelHandler(new DragCancelHandler() {
			@Override
			public void onDragCancel(DragCancelEvent event) {
				tree.setInDrag(false);
			}
		});
		
		draggable.addDragEndHandler(new DragEndHandler() {
			
			@Override
			public void onDragEnd(DragEndEvent event) {
				tree.setInDrag(false);
			}
		});
	}
	
	@Override
	protected void onDragDrop(DndDropEvent event) {
		tree.setInDrag(false);
		DropTarget dt = event.getDropTarget();
		if(dt instanceof TreeDropTarget && ((TreeDropTarget)dt).getWidget().equals(this.tree))
			super.onDragDrop(event);
	}
	
	@Override
	protected void onDragFail(DndDropEvent event) {
		tree.setInDrag(false);
		super.onDragFail(event);
	}
	
}
