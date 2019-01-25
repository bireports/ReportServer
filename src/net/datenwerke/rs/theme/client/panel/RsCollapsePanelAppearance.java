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
 
 
package net.datenwerke.rs.theme.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.Style.LayoutRegion;
import com.sencha.gxt.core.client.resources.StyleInjectorHelper;
import com.sencha.gxt.theme.neptune.client.base.widget.Css3CollapsePanelAppearance;

public class RsCollapsePanelAppearance extends Css3CollapsePanelAppearance {

	private Css3CollapsePanelResources resources;
	private Css3CollapsePanelStyle style;


	public RsCollapsePanelAppearance() {
		this(GWT.<Css3CollapsePanelResources>create(Css3CollapsePanelResources.class));
	}

	public RsCollapsePanelAppearance(Css3CollapsePanelResources resources) {
		super();
		this.resources = resources;
		this.style = this.resources.style();

		StyleInjectorHelper.ensureInjected(style, true);
	}


	@Override
	public void render(SafeHtmlBuilder sb, LayoutRegion region) {
		String cls = style.panel();

		switch (region) {
		case WEST:
			cls += " " + style.west();
			break;
		case EAST:
			cls += " " + style.east();
			break;
		case NORTH:
			cls += " " + style.north();
			break;
		case SOUTH:
			cls += " " + style.south();
			break;
		default:
		}

		sb.appendHtmlConstant("<div class=\"" + cls + " rs-cp-collapse\">");
		sb.appendHtmlConstant("<div class='" + style.iconWrap() + "'></div>");
		sb.appendHtmlConstant("</div>");
	}

}
