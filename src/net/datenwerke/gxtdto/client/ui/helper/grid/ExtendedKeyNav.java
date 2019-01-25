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
 
 
package net.datenwerke.gxtdto.client.ui.helper.grid;

import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.KeyNav;

public class ExtendedKeyNav extends KeyNav {

	public ExtendedKeyNav() {
		super();
	}
	
	public ExtendedKeyNav(Widget target) {
		super(target);
	}

	public void onKeyPress(com.google.gwt.dom.client.NativeEvent evt) {
		if(evt.getCtrlKey() && 65 == evt.getKeyCode()){
			onSelectAll();
		}
	}

	protected void onSelectAll() {
		// implement
	};
}
