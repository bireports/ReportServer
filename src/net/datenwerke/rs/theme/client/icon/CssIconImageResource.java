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
 
 
package net.datenwerke.rs.theme.client.icon;

import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeUri;
import com.google.gwt.safehtml.shared.UriUtils;

public class CssIconImageResource implements ImageResource, CssIconContainer {

	private BaseIcon cssIcon;

	public CssIconImageResource(BaseIcon cssIcon){
		this.cssIcon = cssIcon;
	}

	public CssIconImageResource(BaseIcon cssIcon, int size) {
		this.cssIcon = cssIcon;
	}

	@Override
	public String getName() {
		return "css-image";
	}

	@Override
	public SafeHtml getCssIcon() {
		return cssIcon.toSafeHtml();
	}
	
	@Override
	public SafeHtml getCssIcon(int size) {
		return cssIcon.toSafeHtml(size);
	}

	@Override
	public Element getCssElement() {
		return cssIcon.toElement();
	}

	@Override
	public int getHeight() {
		return 16;
	}

	@Override
	public int getLeft() {
		return 16;
	}

	@Override
	public SafeUri getSafeUri() {
		return UriUtils.fromSafeConstant("css-image");
	}

	@Override
	public int getTop() {
		return 0;
	}

	@Override
	public String getURL() {
		return "css-image";
	}

	@Override
	public int getWidth() {
		return 16;
	}

	@Override
	public boolean isAnimated() {
		return false;
	}

}
