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

import net.datenwerke.gxtdto.client.dtomanager.PropertyAccessor;

import com.sencha.gxt.core.client.ValueProvider;

public class ChangeTracker<T, V> {
	
	private final PropertyAccessor<T, V> vp;
	private final V oldValue;
	private final V newValue;
	private final boolean oldModified;

	public ChangeTracker(PropertyAccessor<T, V> vp, V oldValue, V newValue, boolean oldModified){
		this.vp = vp;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.oldModified = oldModified;
	}

	public ValueProvider<T, V> getVp() {
		return vp;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public Object getNewValue() {
		return newValue;
	}

	public void undo(T object) {
		vp.setValue(object, oldValue);
		vp.setModified(object, oldModified);
	}

	public void redo(T object) {
		vp.setValue(object, newValue);
	}
	
}
