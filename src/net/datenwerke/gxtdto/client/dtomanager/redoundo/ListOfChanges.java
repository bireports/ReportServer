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
 
 
package net.datenwerke.gxtdto.client.dtomanager.redoundo;

import java.util.ArrayList;
import java.util.List;

public class ListOfChanges<T> {

	private final List<ChangeTracker<T, ?>> changes;
	private final T object;
	
	private Integer index;
	private boolean inUnRedo;

	protected ListOfChanges(){
		object = null;
		changes = null;
		//default constructor do not use
	}
	
	public ListOfChanges(T object) {
		this.object = object;
		changes = new ArrayList<ChangeTracker<T,?>>();
	}
	
	private ListOfChanges(T object, List<ChangeTracker<T, ?>> changes) {
		this.object = object;
		this.changes = changes;
	}

	public void add(ChangeTracker<T, ?> change){
		if(inUnRedo)
			return;
		
		if(null != index){
			for(int i = index; i < changes.size(); i++)
				changes.remove(index);
			index = null;
		}
		changes.add(change);
	}
	
	public void undo(){
		undo(1);
	}

	public void undoAll(){
		undo(null == index ? changes.size() : index);
	}
	
	public void undo(int steps){
		if(steps <= 0)
			return;
		
		inUnRedo = true;
		if(null == index)
			index = changes.size() - 1;
		
		for(int i = 0; i < steps && index >= 0; i++){
			changes.get(index).undo(object);
			index--;
		}
		inUnRedo = false;
	}
	
	public void redo(){
		redo(1);
	}
	
	public void redoAll(){
		if(null == index)
			return;
		
		redo(changes.size() - index);
	}
	
	public void redo(int steps){
		if(null == index)
			return;
		
		inUnRedo = true;
		
		for(int i = 0; i < steps && index >= 0 && index < changes.size(); i++){
			changes.get(index).redo(object);
			index++;
		}
		
		if(index == changes.size())
			index = null;
		
		inUnRedo = false;
	}

	public Integer size() {
		return changes.size();
	}

	public ListOfChanges doClone() {
		return new ListOfChanges<T>(object, new ArrayList(this.changes));
	}

	public void undoUpto(int rollBackStep) {
		if(null == index)
			undo(changes.size() - rollBackStep);
		else
			undo(index - rollBackStep);
	}
}
