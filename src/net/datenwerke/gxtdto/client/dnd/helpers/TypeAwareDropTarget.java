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
 
 
package net.datenwerke.gxtdto.client.dnd.helpers;

import java.util.List;

import com.sencha.gxt.data.shared.TreeStore.TreeNode;
import com.sencha.gxt.dnd.core.client.DndDragMoveEvent;
import com.sencha.gxt.dnd.core.client.DropTarget;
import com.sencha.gxt.widget.core.client.Component;

/**
 * 
 *
 */
public class TypeAwareDropTarget extends DropTarget {
	private final Class<?>[] types;
	
	public TypeAwareDropTarget(Component cmp, Class<?>... types) {
		super(cmp);

		this.types = types;
	}
	
	@Override
	protected void onDragMove(DndDragMoveEvent event) {
		if(! (event.getData() instanceof List))
			return;
		
		List list = (List) event.getData();
		if(list.size() > 0){
			/* search for object */
			Object data = list.get(0);
			
			if(data instanceof TreeNode)
				data = ((TreeNode)data).getData();
			
			boolean foundType = searchForType(data.getClass());
			
			for(Class<?> type : types){
				if(type.equals(data.getClass())){
					foundType = true;
					break;
				}
			}
			
			if( foundType ){
				super.onDragMove(event);
			} else {
				event.setCancelled(true);
				event.getStatusProxy().setStatus(false);
				return;
			}
		}
	}

	private boolean searchForType(Class<?> needle) {
		while(null != needle){
			for(Class<?> type : types)
				if(type.equals(needle))
					return true;
			needle = needle.getSuperclass();
		}
		return false;
	}

}
