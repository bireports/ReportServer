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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.providers;

import net.datenwerke.gxtdto.client.baseex.widget.form.DwNumberField;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHookImpl;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.sencha.gxt.widget.core.client.form.Field;
import com.sencha.gxt.widget.core.client.form.NumberField;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor.LongPropertyEditor;


/**
 * 
 *
 */
public class LongProvider extends FormFieldProviderHookImpl {

	@Override
	public boolean doConsumes(Class<?> type, SimpleFormFieldConfiguration... configs) {
		return Long.class.equals(type);
	}

	@Override
	public Field<Long> createFormField() {
		final NumberField<Long> field = new DwNumberField<Long>(new LongPropertyEditor());
		field.setName(name);
		
		field.addValueChangeHandler(new ValueChangeHandler<Long>() {
			@Override
			public void onValueChange(ValueChangeEvent<Long> event) {
				ValueChangeEvent.fire(LongProvider.this, event.getValue());
			}
		});
		
		return field;
	}

}
