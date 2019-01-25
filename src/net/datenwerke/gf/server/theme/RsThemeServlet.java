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
 
 
package net.datenwerke.gf.server.theme;

import java.io.IOException;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gf.service.theme.ThemeService;
import net.datenwerke.security.server.SecuredHttpServlet;
import net.datenwerke.security.service.security.annotation.SecurityChecked;

@Singleton
public class RsThemeServlet  extends SecuredHttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -541050931207667866L;

	public static final String PROPERTY_LOGO = "logo";
	
	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	private final Provider<ThemeService> themeServiceProvider;
	
	@Inject
	public RsThemeServlet(
		Provider<ThemeService> themeServiceProvider
		){
		
		/* store objects */
		this.themeServiceProvider = themeServiceProvider;
	}
	
	@Override
	@SecurityChecked(bypass=true)
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		if("true".equals(request.getParameter(PROPERTY_LOGO))){
			getLogo(response);
			return;
		}
		
		ThemeService themeService = themeServiceProvider.get();
		String theme = themeService.getTheme();
		
		response.setContentType("text/css");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Disposition", "inline; filename=\"rstheme.css\"");
		response.setHeader("Content-Length", theme.getBytes().length + "");
		
		response.getOutputStream().print(theme);
	}

	protected void getLogo(HttpServletResponse response) throws IOException {
		ThemeService themeService = themeServiceProvider.get();
		byte[] logo = themeService.getLogo();
		
		response.setHeader("Content-Disposition", "inline; filename=\"logo\"");
		response.setHeader("Content-type", "image");
		response.setHeader("Content-Length", logo.length + "");
				
		response.getOutputStream().write(logo);
	}
}
