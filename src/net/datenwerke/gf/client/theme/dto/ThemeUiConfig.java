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
 
 
package net.datenwerke.gf.client.theme.dto;

import java.io.Serializable;

public class ThemeUiConfig implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String logoLoginHtml = "<i class=\"icon-rs-logo rs-login-logo\"></i><span class=\"rs-login-bg\"><i class=\"icon-rs-logo-square\"></i></span>";
	private String logoLoginWidth = "200px";
	
	private String logoHeaderHtml = "<span class=\"rs-header-logo\"><i class=\"icon-rs-Report\"></i><i class=\"icon-rs-Server\"></i></span>";
	private String logoHeaderWidth = "180px";
	
	private int headerHeight = 40;
	
	public ThemeUiConfig(){
		// default constructor
	}
	
	public String getLogoLoginHtml() {
		return logoLoginHtml;
	}
	public void setLogoLoginHtml(String logoLoginHtml) {
		this.logoLoginHtml = logoLoginHtml;
	}
	public String getLogoLoginWidth() {
		return logoLoginWidth;
	}
	public void setLogoLoginWidth(String logoLoginWidth) {
		this.logoLoginWidth = logoLoginWidth;
	}
	public String getLogoHeaderHtml() {
		return logoHeaderHtml;
	}
	public void setLogoHeaderHtml(String logoHeaderHtml) {
		this.logoHeaderHtml = logoHeaderHtml;
	}
	public String getLogoHeaderWidth() {
		return logoHeaderWidth;
	}
	public void setLogoHeaderWidth(String logoHeaderWidth) {
		this.logoHeaderWidth = logoHeaderWidth;
	}
	public int getHeaderHeight() {
		return headerHeight;
	}
	public void setHeaderHeight(int headerHeight) {
		this.headerHeight = headerHeight;
	}
	
}
