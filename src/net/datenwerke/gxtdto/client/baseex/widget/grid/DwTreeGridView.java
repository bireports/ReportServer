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
 
 
package net.datenwerke.gxtdto.client.baseex.widget.grid;

import com.google.gwt.dom.client.Element;
import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.widget.core.client.tree.Tree.TreeNode;
import com.sencha.gxt.widget.core.client.treegrid.TreeGridView;

import net.datenwerke.rs.theme.client.icon.CssIconImageResource;

public class DwTreeGridView<M> extends TreeGridView<M> {

	@Override
	public void onIconStyleChange(TreeNode<M> node, ImageResource icon) {
		if(! (icon instanceof CssIconImageResource))
			super.onIconStyleChange(node, icon);
		else {
			Element iconEl = getIconElement(node);
			if (iconEl != null) {
				Element e = ((CssIconImageResource)icon).getCssElement();
				e.addClassName(iconEl.getClassName());
				node.setIconElement((Element) iconEl.getParentElement().insertBefore(e, iconEl));
				iconEl.removeFromParent();
			}
		}
	}


}
