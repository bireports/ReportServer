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
 
 
package net.datenwerke.gxtdto.client.dtomanager.dtomod.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ChangeMonitoredSet<E> extends MonitoredCollection<E,Set<E>> implements Set<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6254780343696211158L;

	public ChangeMonitoredSet(){
		super(new HashSet<E>());
	}
	
	
	public ChangeMonitoredSet(Set<E> underlyingCollection){
		super(null == underlyingCollection ? new HashSet<E>() : underlyingCollection);
	}
	
	@Override
	public boolean add(E e) {
		boolean retValue = underlyingCollection.add(e);
		if(retValue)
			markModified();
		return retValue;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean retValue =  underlyingCollection.addAll(c);
		if(retValue)
			markModified();
		return retValue;
	}

	@Override
	public void clear() {
		if(! underlyingCollection.isEmpty()){
			underlyingCollection.clear();
			markModified();
		}
	}

	@Override
	public boolean contains(Object o) {
		return underlyingCollection.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return underlyingCollection.containsAll(c);
	}

	@Override
	public boolean isEmpty() {
		return underlyingCollection.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return underlyingCollection.iterator();
	}

	@Override
	public boolean remove(Object o) {
		boolean retValue = underlyingCollection.remove(o);
		if(retValue)
			markModified();
		return retValue;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean retValue = underlyingCollection.removeAll(c);
		if(retValue)
			markModified();
		return retValue;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean retValue = underlyingCollection.retainAll(c);
		if(retValue)
			markModified();
		return retValue;
	}

	@Override
	public int size() {
		return underlyingCollection.size();
	}

	@Override
	public Object[] toArray() {
		return underlyingCollection.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return underlyingCollection.toArray(a);
	}


}
