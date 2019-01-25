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
 
 
package net.datenwerke.rs.saiku.service.saiku;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import org.saiku.service.util.QueryContext;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.inject.Inject;
import com.google.inject.servlet.SessionScoped;

@SessionScoped
public class SaikuContextMap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Cache<String, QueryContext> cache;
	
	@Inject
	public SaikuContextMap(
		SaikuService saikuService	
		){
		cache = CacheBuilder.newBuilder()
	       .maximumSize(saikuService.getContextMaxSize())
	       .expireAfterWrite(saikuService.getContextExpiresAfter(), TimeUnit.MINUTES)
	       .build();
	}

	public boolean containsKey(String name) {
		return null != cache.getIfPresent(name);
	}

	public void put(String name, QueryContext qt) {
		cache.put(name, qt);
	}

	public QueryContext get(String name) {
		return cache.getIfPresent(name);
	}
}
