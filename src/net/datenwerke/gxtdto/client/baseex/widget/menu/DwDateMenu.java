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
 
 
package net.datenwerke.gxtdto.client.baseex.widget.menu;

import java.util.Date;

import net.datenwerke.gxtdto.client.baseex.widget.date.DwDatePicker;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.sencha.gxt.widget.core.client.DatePicker;
import com.sencha.gxt.widget.core.client.menu.Menu;

public class DwDateMenu extends Menu implements HasValueChangeHandlers<Date>{

	private DwDatePicker picker;

	public DwDateMenu() {
		picker = new DwDatePicker();
		picker.addValueChangeHandler(new ValueChangeHandler<Date>() {

			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				onPickerSelect(event);
			}
		});

		add(picker);
		getAppearance().applyDateMenu(getElement());
		plain = true;
		showSeparator = false;
		setEnableScrolling(false);
	}
	

	  @Override
	  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Date> handler) {
	    return addHandler(handler, ValueChangeEvent.getType());
	  }

	  @Override
	  public void focus() {
	    super.focus();
	    picker.getElement().focus();
	  }

	  /**
	   * Returns the selected date.
	   * 
	   * @return the date
	   */
	  public Date getDate() {
	    return picker.getValue();
	  }

	  /**
	   * Returns the date picker.
	   * 
	   * @return the date picker
	   */
	  public DatePicker getDatePicker() {
	    return picker;
	  }

	  /**
	   * Sets the menu's date.
	   * 
	   * @param date the date
	   */
	  public void setDate(Date date) {
	    picker.setValue(date);
	  }

	  protected void onPickerSelect(ValueChangeEvent<Date> event) {
	    fireEvent(event);
	  }
}
