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
 
 
package net.datenwerke.rs.utils.misc;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * A Hashmap keeping entries no longer than a certain time
 * 
 *
 */
public class TimeoutHashMap<K, V> extends HashMap<K, V>{
	
	private class Entry<K>{
		K key;
		long created;
		
		public Entry(long created, K key) {
			this.key = key;
			this.created = created;
		}
	}
	
	private LinkedList<Entry<K>> entries = new LinkedList<Entry<K>>();

	private long timeout;
	
	
	public TimeoutHashMap(long timeout) {
		super();
		this.setTimeout(timeout);
	}
	
	public void beforeRemove(K key, V value){
		
	}
	
	private void doMaintainance(){
		long now = System.currentTimeMillis();
		
		Iterator<Entry<K>> it = entries.iterator();
		while(it.hasNext()){
			Entry<K> entry = it.next();
			if(entry.created < now - getTimeout()){
				beforeRemove(entry.key, super.get(entry.key));
				super.remove(entry.key);
				it.remove();
			}else{
				return;
			}
		}
	}
	
	
	@Override
	public boolean containsKey(Object key) {
		doMaintainance();
		return super.containsKey(key);
	}
	
	@Override
	public boolean containsValue(Object value) {
		doMaintainance();
		return super.containsValue(value);
	}
	
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		doMaintainance();
		return super.entrySet();
	}
	
	@Override
	public V get(Object key) {
		doMaintainance();
		return super.get(key);
	}
	
	@Override
	public boolean isEmpty() {
		doMaintainance();
		return super.isEmpty();
	}
	
	@Override
	public Set<K> keySet() {
		doMaintainance();
		return super.keySet();
	}
	
	public V put(K key, V value) {
		doMaintainance();
		this.entries.add(new Entry<K>(System.currentTimeMillis(), key));
		return super.put(key, value);
	};
	
	@Override
	public int size() {
		doMaintainance();
		return super.size();
	}
	
	@Override
	public Collection<V> values() {
		doMaintainance();
		return super.values();
	}
	
	@Override
	public V remove(Object key) {
		beforeRemove((K) key, super.get(key));
		doMaintainance();
		return super.remove(key);
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}
}
