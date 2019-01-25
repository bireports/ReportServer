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
 
 
package net.datenwerke.gf.server.homepage;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.datenwerke.gf.client.homepage.rpc.HomepageRpcService;
import net.datenwerke.gf.service.DwGwtFrameworkModule;
import net.datenwerke.gf.service.lateinit.LateInitStartup;
import net.datenwerke.rs.utils.config.ConfigService;
import net.datenwerke.security.server.SecuredRemoteServiceServlet;
import net.datenwerke.security.service.security.annotation.SecurityChecked;

import com.google.inject.Provider;

@Singleton
public class HomepageRpcServiceImpl extends SecuredRemoteServiceServlet implements HomepageRpcService {

	private static final long serialVersionUID = 870178758140654429L;
	private static final String CONFIG_KEY_PAGE_TITLE = "pagetitle";
	
	public final static String SESSION_REDIRECT_URL = "rs.redirect.url";
	
	private ConfigService configService;
	private Provider<HttpServletRequest> request;
	
	@Inject
	public HomepageRpcServiceImpl(
			ConfigService configService, 
			Provider<HttpServletRequest> request) {
		this.configService = configService;
		this.request = request;
	}
	

	@SecurityChecked(loginRequired=false)
	@Override
	public String getPageTitle() {
		return configService.getConfigFailsafe(DwGwtFrameworkModule.CONFIG_FILE).getString(CONFIG_KEY_PAGE_TITLE);
	}

	@SecurityChecked(loginRequired=false)
	@Override
	public boolean isStartupComplete(){
		return LateInitStartup.isStartupCompleted();
	}
	
	@Override
	public String getSessionRedirect(){
		HttpSession session = request.get().getSession();
		String url = (String) session.getAttribute(SESSION_REDIRECT_URL);
		session.removeAttribute(SESSION_REDIRECT_URL);
		return url;
	}
}
