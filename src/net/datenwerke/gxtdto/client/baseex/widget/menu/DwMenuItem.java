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
 
 
package net.datenwerke.gxtdto.client.baseex.widget.menu;

import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.widget.core.client.menu.MenuItem;


public class DwMenuItem extends MenuItem {

	public DwMenuItem() {
		super();
	}
	
	public DwMenuItem(String label) {
		super(label);
	}
	
	public DwMenuItem(String label, ImageResource icon) {
		super(label, icon);
	}

	public DwMenuItem(String label,
			SelectionHandler<MenuItem> selectionHandler) {
		super(label, selectionHandler);
	}
	
	public DwMenuItem(String label, BaseIcon icon) {
		this(label, icon.toImageResource());
	}
	
	public void setIcon(BaseIcon icon) {
		setIcon(icon.toImageResource());
	}

	

	

	
	

	
}
