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
 
 
package net.datenwerke.gf.server.config;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import com.google.inject.Singleton;

import net.datenwerke.gf.client.config.ClientConfigModule;
import net.datenwerke.gf.client.config.rpc.ClientConfigRpcService;
import net.datenwerke.gf.service.config.ClientConfigExposerHook;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;

@Singleton
public class ClientConfigRpcServiceImpl extends SecuredRemoteServiceServlet implements ClientConfigRpcService {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -191463296295625883L;
	
	private final ConfigService configService;
	private final HookHandlerService hookHandler;

	@Inject
	public ClientConfigRpcServiceImpl(
		ConfigService configService,
		HookHandlerService hookHandler
		) {
		this.configService = configService;
		this.hookHandler = hookHandler;
	}

	@Override
	public String getConfigFile(String identifier) throws ServerCallFailedException {
		/* currently only allowed for ui */
		if(! ClientConfigModule.MAIN_CLIENT_CONFIG.equals(identifier))
			throw new IllegalArgumentException("Currently not available for just any config!");
		
		try{
			return configService.getConfigAsJson(identifier);
		} catch(Exception e) {
			throw new ServerCallFailedException(e);
		}
	}

	@Override
	public HashMap<String, String> getConfigProperties(String identifier) {
		HashMap<String, String> properties = new HashMap<>();
		
		for(ClientConfigExposerHook exposer : hookHandler.getHookers(ClientConfigExposerHook.class)){
			Map<String,String> addProperties = exposer.exposeConfig(identifier);
			if(null != addProperties)
				properties.putAll(addProperties);
		}
		
		return properties;
	}


	
}
