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
 
 
package net.datenwerke.gxtdto.client.ui.helper.nav;

import net.datenwerke.rs.theme.client.icon.CssIconContainer;

import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;

public class CssIconNavModelData<M> extends NavigationModelData<M> implements CssIconContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CssIconNavModelData(int id, String name, ImageResource icon,
			M component) {
		super(id, name, icon, component);
	}

	public CssIconNavModelData(String name, ImageResource icon, M component) {
		super(name, icon, component);
	}

	public CssIconNavModelData(String name, M component) {
		super(name, component);
	}

	@Override
	public SafeHtml getCssIcon() {
		return null;
	}
	
	@Override
	public SafeHtml getCssIcon(int size) {
		return null;
	}
	
	@Override
	public Element getCssElement() {
		return null;
	}

	
}
