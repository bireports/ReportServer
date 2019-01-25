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
 
 
package net.datenwerke.rs.theme.client.grid;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.core.client.resources.StyleInjectorHelper;
import com.sencha.gxt.theme.neptune.client.base.grid.Css3ColumnHeaderAppearance;
import com.sencha.gxt.widget.core.client.grid.ColumnHeader.ColumnHeaderStyles;

import net.datenwerke.rs.theme.client.icon.BaseIcon;

public class RsColumnHeaderAppearance extends Css3ColumnHeaderAppearance {

	public interface ColumnHeaderTemplate extends XTemplates {
		@XTemplate("<div class=\"{style.header} rs-grid-head\"><div class=\"{style.headerInner}\"></div></div>")
		SafeHtml render(ColumnHeaderStyles style);
	}
	
	public interface RsColumnHeaderAppearanceResources extends Css3ColumnHeaderResources  {
	    @Source("RsColumnHeader.css")
	    Styles style();
    }

	private final RsColumnHeaderAppearanceResources resources;
	private final ColumnHeaderStyles style;
	private ColumnHeaderTemplate templates = GWT.create(ColumnHeaderTemplate.class);

	public RsColumnHeaderAppearance() {
		this(GWT.<RsColumnHeaderAppearanceResources> create(RsColumnHeaderAppearanceResources.class));
	}

	public RsColumnHeaderAppearance(RsColumnHeaderAppearanceResources resources) {
		this.resources = resources;
		this.style = this.resources.style();

		StyleInjectorHelper.ensureInjected(style, true);
	}

	@Override
	public void render(SafeHtmlBuilder sb) {
		sb.append(templates.render(style));
	}

	@Override
	public ImageResource sortAscendingIcon() {
		return BaseIcon.SORT_ALPHA_ASC.toImageResource();
	}

	@Override
	public ImageResource sortDescendingIcon() {
		return BaseIcon.SORT_ALPHA_DESC.toImageResource();
	}

}
