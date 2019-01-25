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
 
 
package net.datenwerke.gxtdto.client.clipboard;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.event.HeaderClickEvent;
import com.sencha.gxt.widget.core.client.event.HeaderClickEvent.HeaderClickHandler;
import com.sencha.gxt.widget.core.client.grid.Grid;

public abstract class ClipboardHandler implements KeyDownHandler {

	protected Widget component;

	public ClipboardHandler(Widget target){
		bind(target);
	}

	/**
	 * Binds the key nav to the component.
	 * 
	 * @param target the target component
	 */
	public void bind(final Widget target) {
		if (target != null) {
			target.addDomHandler(this, KeyDownEvent.getType());
			
			if(target instanceof Grid){
				((Grid)target).addHeaderClickHandler(new HeaderClickHandler() {
					
					@Override
					public void onHeaderClick(HeaderClickEvent event) {
						((Component)target).focus();
					}
				});
			}
		}
		this.component = target;
	}

}
