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

import net.datenwerke.rs.theme.client.icon.CssIconImageResource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.core.client.util.IconHelper;
import com.sencha.gxt.theme.neptune.client.base.panel.Css3HeaderAppearance;

public class RsHeaderAppearance extends Css3HeaderAppearance {

	private Css3HeaderStyle style;

	public RsHeaderAppearance() {
		this(GWT.<Css3HeaderResources>create(Css3HeaderResources.class));
	}

	public RsHeaderAppearance(Css3HeaderResources resources) {
		super(resources);
		
		this.style = resources.style();
	}

	@Override
	public void setIcon(XElement parent, ImageResource icon) {
		XElement iconWrap = parent.getFirstChildElement().cast();
		iconWrap.removeChildren();
		if (icon != null) {
			if(icon instanceof CssIconImageResource)
				iconWrap.appendChild(((CssIconImageResource)icon).getCssElement());
			else
				iconWrap.appendChild(IconHelper.getElement(icon));
		}
		parent.setClassName(style.headerHasIcon(), icon != null);
	}
}
