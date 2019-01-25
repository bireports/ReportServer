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
 
 
package net.datenwerke.gf.server.theme;

import net.datenwerke.gf.client.theme.dto.ThemeUiConfig;
import net.datenwerke.gf.client.theme.rpc.ThemeRpcService;
import net.datenwerke.gf.service.theme.ThemeService;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.annotation.SecurityChecked;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class ThemeRpcServiceImpl  extends SecuredRemoteServiceServlet implements ThemeRpcService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private final Provider<ThemeService> themeServiceProvider;

	@Inject
	public ThemeRpcServiceImpl(
			Provider<ThemeService> themeServiceProvider
			){
		this.themeServiceProvider = themeServiceProvider;
	}

	@SecurityChecked(bypass=true)
	public ThemeUiConfig loadUiConfig() throws ServerCallFailedException {
		return themeServiceProvider.get().loadUiConfig();
	}



}
