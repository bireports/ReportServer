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
 
 
package net.datenwerke.rs.core.server.urlview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.core.client.urlview.rpc.UrlViewRpcService;
import net.datenwerke.rs.core.service.urlview.annotations.UrlViewConfig;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class UrlViewRpcServiceImpl extends SecuredRemoteServiceServlet implements UrlViewRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3819431223215867342L;
	
	private final Provider<Map<String,Map<String, List<String[]>>>> configProvider;

	@Inject
	public UrlViewRpcServiceImpl(
		@UrlViewConfig Provider<Map<String,Map<String, List<String[]>>>> configProvider
		){
			this.configProvider = configProvider;
		
	}

	@Override
	public Map<String,Map<String, List<String[]>>> loadViewConfiguration() throws ServerCallFailedException {
		try{
			return configProvider.get();
		}catch(IllegalArgumentException e){
			return new HashMap<String, Map<String,List<String[]>>>();
		}
	}
}
