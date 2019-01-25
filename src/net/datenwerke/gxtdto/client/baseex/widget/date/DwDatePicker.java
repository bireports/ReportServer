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
 
 
package net.datenwerke.gxtdto.client.baseex.widget.date;

import java.util.Date;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.core.client.util.DateWrapper;
import com.sencha.gxt.widget.core.client.DatePicker;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;


public class DwDatePicker extends DatePicker {

	@Override
	public XElement getElement() {
		/* very mean hack */
		if(!(todayBtn instanceof DwTextButton)){
			todayBtn = new DwTextButton(getMessages().todayText());
			todayBtn.addSelectHandler(new SelectHandler() {

				@Override
				public void onSelect(SelectEvent event) {
					setValue(new DateWrapper().asDate());
				}
			});

			todayBtn.setToolTip(getMessages().todayTip(DateTimeFormat.getFormat(PredefinedFormat.DATE_SHORT).format(new Date())));
		}
		if(!(monthPickerOkButton instanceof DwTextButton)){
			final TextButton oldBtn = monthPickerOkButton;
			monthPickerOkButton = new DwTextButton(getMessages().okText());
			monthPickerOkButton.addSelectHandler(new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					oldBtn.fireEvent(new SelectEvent());
				}
			});

			monthPickerCancelButton = new DwTextButton(getMessages().cancelText());
			monthPickerCancelButton.addSelectHandler(new SelectHandler() {
				@Override
				public void onSelect(SelectEvent event) {
					focus();
					hideMonthPicker();
				}
			});
		}

		return super.getElement();
	}
}
