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
 
 
package net.datenwerke.rs.theme.client.tab;

import net.datenwerke.rs.theme.client.icon.CssIconImageResource;

import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.core.client.dom.XDOM;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.core.client.resources.ThemeStyles;
import com.sencha.gxt.core.client.util.IconHelper;
import com.sencha.gxt.theme.neptune.client.base.tabs.Css3TabPanelBottomAppearance;
import com.sencha.gxt.widget.core.client.TabItemConfig;

public class RsTabPanelBottomAppearance extends Css3TabPanelBottomAppearance {

	@Override
	protected void setItemIcon(XElement item, ImageResource icon) {
		XElement node = item.selectNode("." + style.tabImage());
		if (node != null) {
			node.removeFromParent();
		}
		if (icon != null) {
			Element e = null;
			if(icon instanceof CssIconImageResource){
				e = ((CssIconImageResource)icon).getCssElement();
				XElement text = item.selectNode("." + style.tabStripText());
				if(null != text)
					item.insertBefore(e, text);
				else
					item.appendChild(e);
				e.addClassName(style.tabImage());
			}else {
				e = IconHelper.getElement(icon);
				e.setClassName(style.tabImage());
			}


		}
		item.setClassName(style.tabWithIcon(), icon != null);
	}

	@Override
	public void insert(XElement parent, TabItemConfig config, int index) {
		XElement item = XDOM.create(itemTemplate.render(style, config).asString());
		item.setClassName(ThemeStyles.get().style().disabled(), !config.isEnabled());

		if (config.isHTML()) {
			XElement textEl = item.selectNode("." + style.tabStripText());
			textEl.setInnerHTML(config.getHTML());
		}

		getStrip(parent).insertChild(item, index);

		if (config.getIcon() != null) {
			setItemIcon(item, config.getIcon());
		}

		if (config.isClosable()) {
			item.addClassName(style.tabStripClosable());
			item.addClassName("rs-tp-closable");
			XElement a = item.selectNode("." + style.tabStripClose());
			a.addClassName("fa fa-times");
		}
	}

	@Override
	public void createScrollers(XElement parent) {
		int h = getStripWrap(parent).getOffsetHeight();
		XElement scrollLeft = getBar(parent).insertFirst("<div class='" + style.tabScrollerLeft() + " rs-tp-scroll-bl fa fa-chevron-left'></div>");
		scrollLeft.setId(XDOM.getUniqueId());
		scrollLeft.setHeight(h);

		XElement scrollRight = getBar(parent).insertFirst("<div class='" + style.tabScrollerRight() + " rs-tp-scroll-br fa fa-chevron-right'></div>");
		scrollRight.setId(XDOM.getUniqueId());
		scrollRight.setHeight(h);
	}
}
