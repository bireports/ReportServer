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
 
 
package net.datenwerke.gxtdto.client.baseex.widget.drag;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.fx.client.Draggable;

public class DwDraggable extends Draggable {

	public DwDraggable(Widget dragComponent) {
		super(dragComponent);
	}

	public DwDraggable(Widget dragComponent, DraggableAppearance appearance) {
		super(dragComponent, appearance);
	}

	public DwDraggable(Widget dragComponent, Widget handle, DraggableAppearance appearance) {
		super(dragComponent, handle, appearance);
	}

	public DwDraggable(Widget dragComponent, Widget handle) {
		super(dragComponent, handle);
	}



	@Override
	protected XElement createProxy() {
		XElement proxy = super.createProxy();
		proxy.addClassName("rs-draggable");
		return proxy;
	}
}
