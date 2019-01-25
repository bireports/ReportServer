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
 
 
package net.datenwerke.rs.theme.client.menu;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.theme.neptune.client.base.menu.Css3MenuAppearance;

public class RsMenuAppearance extends Css3MenuAppearance {

	public interface RsBaseMenuTemplate extends BaseMenuTemplate {

		@XTemplate(source = "RsMenu.html")
		SafeHtml template(MenuStyle style, String ignoreClass);

	}

	public RsMenuAppearance() {
		this(GWT.<Css3MenuResources>create(Css3MenuResources.class),
				GWT.<BaseMenuTemplate>create(RsBaseMenuTemplate.class));
	}

	public RsMenuAppearance(Css3MenuResources resources, BaseMenuTemplate template) {
		super(resources, template);
	}
}
