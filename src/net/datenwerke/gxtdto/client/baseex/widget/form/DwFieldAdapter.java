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

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.event.BlurEvent;
import com.sencha.gxt.widget.core.client.event.BlurEvent.BlurHandler;
import com.sencha.gxt.widget.core.client.form.AdapterField;
import com.sencha.gxt.widget.core.client.form.Field;

public class DwFieldAdapter<D> extends AdapterField<D> {

	protected Field<D> field;

	public DwFieldAdapter(Widget widget, Field<D> field) {
		super(widget);
		this.field = field;
		
		addListeners();
	}

	protected void addListeners() {
		field.addValueChangeHandler(new ValueChangeHandler<D>() {
			@Override
			public void onValueChange(ValueChangeEvent<D> event) {
				DwFieldAdapter.this.fireEvent(event);
			}
		});

		field.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				DwFieldAdapter.this.fireEvent(new BlurEvent());
			}
		});
	}

	@Override
	public D getValue() {
		return field.getValue();
	}
	
	public void setValue(final Object value) {
		field.setValue((D)value, true);
	}
	
	@Override
	public void setSize(String width, String height) {
		getWidget().setSize(width, height);
	}
}
