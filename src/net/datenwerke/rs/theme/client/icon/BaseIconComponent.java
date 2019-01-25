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
 
 
package net.datenwerke.rs.theme.client.icon;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.HTML;
import com.sencha.gxt.core.shared.event.GroupingHandlerRegistration;
import com.sencha.gxt.widget.core.client.WidgetComponent;

public class BaseIconComponent extends WidgetComponent implements HasClickHandlers{

	private GroupingHandlerRegistration handlers = new GroupingHandlerRegistration();
	
	public BaseIconComponent(SafeHtml safeHtml) {
		super(new HTML(safeHtml));
	}

	@Override
	public HandlerRegistration addClickHandler(ClickHandler handler) {
		HandlerRegistration clickHandler = ((HTML)getWidget()).addClickHandler(handler);
		handlers.add(clickHandler);
		return clickHandler;
	}
	
	public void release() {
		handlers.removeHandler();
		handlers = null;
	}
}
