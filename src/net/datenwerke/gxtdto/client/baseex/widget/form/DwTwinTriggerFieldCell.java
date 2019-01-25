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
 
 
package net.datenwerke.gxtdto.client.baseex.widget.form;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.sencha.gxt.cell.core.client.form.TwinTriggerFieldCell;
import com.sencha.gxt.core.client.dom.XElement;

public class DwTwinTriggerFieldCell<T> extends TwinTriggerFieldCell<T> {

	@Override
	protected void onClick(com.google.gwt.cell.client.Cell.Context context,
			XElement parent, NativeEvent event, T value, ValueUpdater<T> updater) {
		Element target = event.getEventTarget().cast();

		if (!isReadOnly() && getAppearance().twinTriggerIsOrHasChild(parent, target)) {
			onTwinTriggerClick(context, parent, event, value, updater);
			return; // no trigger click if twin was clicked!
		}

		if (!isReadOnly() && getAppearance().triggerIsOrHasChild(parent, target)) {
			onTriggerClick(context, parent, event, value, updater);
		}
	}
}
