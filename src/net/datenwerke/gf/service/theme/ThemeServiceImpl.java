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
 
 
package net.datenwerke.gf.service.theme;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.datenwerke.gf.client.theme.dto.ThemeUiConfig;
import net.datenwerke.rs.license.service.LicenseServiceImpl;

public class ThemeServiceImpl implements ThemeService {

	private final Logger logger = LoggerFactory.getLogger(getClass().getName());

	public static final String THEME_CONFIG = "ui/theme.cf";
	
	@Override
	public String getTheme() {
		String theme = loadTheme();
		return theme;
		
	}

	@Override
	public byte[] getLogo() {
		try {
			return IOUtils.toByteArray(getClass().getResourceAsStream("rs-logo.png"));
		} catch (IOException e) {
			throw new RuntimeException("Could not load logo", e);
		}
	}
	
	@Override
	public Map<String, String> getColorMap() {
		return new HashMap<String, String>();
	}
	
	@Override
	public ThemeUiConfig loadUiConfig() {
		ThemeUiConfig uiConfig = new ThemeUiConfig();

		String loginHtml = "<span class='rs-login-logo'><i class=\"icon-rs-logo\"></i></span><span class='rs-login-edition'>"+LicenseServiceImpl.COMMUNITY_LICENSE+"</span><span class=\"rs-login-bg\"><i class=\"icon-rs-logo-square\"></i></span>";
		String loginWidth = "200px";

		String headerHtml = "<span class=\"rs-header-logo\"><i class=\"icon-rs-Report\"></i><i class=\"icon-rs-Server\"></i></span>";
		String headerWidth = "185px";

		uiConfig.setLogoHeaderHtml(headerHtml);
		uiConfig.setLogoHeaderWidth(headerWidth);

		uiConfig.setLogoLoginHtml(loginHtml);
		uiConfig.setLogoLoginWidth(loginWidth);

		return uiConfig;
	}

	protected String loadTheme() {
		try {
			String theme = IOUtils.toString(getClass().getResourceAsStream("theme.css"));
			
			return theme;
		} catch (IOException e) {
			throw new RuntimeException("Could not load theme", e);
		}
	}


}
