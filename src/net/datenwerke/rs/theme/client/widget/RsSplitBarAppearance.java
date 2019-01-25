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
 
 
package net.datenwerke.rs.theme.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.Style.LayoutRegion;
import com.sencha.gxt.core.client.resources.StyleInjectorHelper;
import com.sencha.gxt.theme.neptune.client.base.widget.Css3SplitBarAppearance;

public class RsSplitBarAppearance extends Css3SplitBarAppearance {

	private final Css3SplitBarStyle style;

	public RsSplitBarAppearance() {
		this(GWT.<Css3SplitBarResources>create(Css3SplitBarResources.class));
	}

	public RsSplitBarAppearance(Css3SplitBarResources resources) {
		super(resources);
		this.style = resources.style();

		StyleInjectorHelper.ensureInjected(style, true);
	}

	@Override
	public void render(SafeHtmlBuilder sb, LayoutRegion region) {
		String cls = "";
		if (region == LayoutRegion.SOUTH || region == LayoutRegion.NORTH) {
			cls = style.horizontalBar();
		} else {
			cls = style.verticalBar();
		}
		sb.appendHtmlConstant("<div class='" + cls + " rs-splitbar'></div>");
	}
}
