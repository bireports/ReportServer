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
 
 
package net.datenwerke.rs.core.server.reportexport.helper;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class ReportSessionCache {

	private Cache<String, ReportSessionCacheEntry> cache;
	
	private int maximumSize = 100;
	private int expiresAfter = 120;
	
	public ReportSessionCache(){
		cache = CacheBuilder.newBuilder()
			       .maximumSize(getMaximumSize())
			       .expireAfterWrite(getExpiresAfter(), TimeUnit.MINUTES)
			       .build();
	}
	
	public void put(String key, ReportSessionCacheEntry entry){
		cache.put(key, entry);
	}
	
	public ReportSessionCacheEntry get(String key){
		return cache.getIfPresent(key);
	}
	
	public void setMaximumSize(int maximumSize) {
		this.maximumSize = maximumSize;
	}
	
	public int getMaximumSize() {
		return maximumSize;
	}
	
	public void setExpiresAfter(int expiresAfter) {
		this.expiresAfter = expiresAfter;
	}
	
	public int getExpiresAfter() {
		return expiresAfter;
	}
	
}
