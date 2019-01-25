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
 
 
package net.datenwerke.gf.client.theme;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.HTML;

import net.datenwerke.gf.client.theme.dto.ThemeUiConfig;

public class ThemeUiServiceImpl implements ThemeUiService {

	private ThemeUiConfig themeConfig;

	@Override
	public void setThemeConfig(ThemeUiConfig themeConfig) {
		this.themeConfig = themeConfig;
	}
	
	@Override
	public ThemeUiConfig getThemeConfig() {
		return themeConfig;
	}
	
	@Override
	public HTML getHeaderLogo() {
		try{
			SafeHtml safeLogo = SafeHtmlUtils.fromTrustedString(themeConfig.getLogoHeaderHtml());
			HTML logo = new HTML(safeLogo);
			logo.setWidth(themeConfig.getLogoHeaderWidth());
			return logo;
		} catch(Exception e){
			SafeHtml safeLogo = new SafeHtmlBuilder().appendHtmlConstant("<span class=\"rs-logo-fallback\">Logo Missing</span>").toSafeHtml();
			HTML logo = new HTML(safeLogo);
			logo.setWidth(200 + "px");
			return logo;
		}
	}
	
	@Override
	public HTML getLoginLogo() {
		try{
			SafeHtml safeLogo = SafeHtmlUtils.fromTrustedString(themeConfig.getLogoLoginHtml());
			HTML logo = new HTML(safeLogo);
			logo.setWidth(themeConfig.getLogoLoginWidth());
			return logo;
		} catch(Exception e){
			SafeHtml safeLogo = new SafeHtmlBuilder().appendHtmlConstant("<span class=\"rs-logo-fallback\">Logo Missing</span>").toSafeHtml();
			HTML logo = new HTML(safeLogo);
			logo.setWidth(200 + "px");
			return logo;
		}
	}

}
