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
 
 
package net.datenwerke.rs.core.service.reportserver;

import javax.servlet.http.HttpServletRequest;

import net.datenwerke.rs.utils.config.ConfigService;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ReportServerServiceImpl implements ReportServerService {


	public static final String HIBERNATE_PROPERTY_CONNECTION_DRIVER = "hibernate.connection.driver_class";
	
	private final ConfigService configService;
	private final Provider<ServerInfoContainer> serverInfoContainerProvider; 
	
	@Inject
	public ReportServerServiceImpl(
		ConfigService configService,
		Provider<ServerInfoContainer> serverInfoContainerProvider
		){
		
		this.configService = configService;
		this.serverInfoContainerProvider = serverInfoContainerProvider;
	}
	
	@Override
	public String getCharset() {
		return configService.getConfigFailsafe(CONFIG_FILE).getString("default.charset",DEFAULT_CHARSET);
	}

	void init(HttpServletRequest httpServletRequest) {
		ServerInfoContainer serverInfoContainer = serverInfoContainerProvider.get();
		if(null != serverInfoContainer)
			serverInfoContainer.init(httpServletRequest);
	}
	
	@Override
	public ServerInfoContainer getServerInfo() {
		return serverInfoContainerProvider.get();
	}

}
