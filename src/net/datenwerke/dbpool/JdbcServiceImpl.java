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
 
 
package net.datenwerke.dbpool;

import com.google.inject.Inject;

import net.datenwerke.dbpool.hooks.JdbcUrlAdapterHook;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

public class JdbcServiceImpl implements JdbcService {

	private final HookHandlerService hookHandler;
	
	@Inject
	public JdbcServiceImpl(HookHandlerService hookHandler) {
		this.hookHandler = hookHandler;
	}


	@Override
	public String adaptJdbcUrl(String jdbcUrl){
		if(null == jdbcUrl)
			return null;
		
		for(JdbcUrlAdapterHook adapter : hookHandler.getHookers(JdbcUrlAdapterHook.class))
			jdbcUrl = adapter.adaptJdbcUrl(jdbcUrl);
		
		return jdbcUrl;
	}
}
