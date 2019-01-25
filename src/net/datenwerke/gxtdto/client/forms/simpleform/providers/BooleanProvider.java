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

import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHookImpl;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCBoolean;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.Field;

/**
 * 
 *
 */
public class BooleanProvider extends FormFieldProviderHookImpl {

	@Override
	public boolean doConsumes(Class<?> type, SimpleFormFieldConfiguration... configs) {
		return Boolean.class.equals(type);
	}

	@Override
	public Field<Boolean> createFormField() {
		final CheckBox cb = new CheckBox();
		cb.setName(name);
		SFFCBoolean config = getConfig();
		if(null != config)
			cb.setBoxLabel(config.getBoxLabel());
		else
		cb.setBoxLabel("");
		
		/* add change listener */
		cb.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<Boolean> event) {
				ValueChangeEvent.fire(BooleanProvider.this, cb.getValue());
			}
		});
		
		return cb;
	}
	
	protected SFFCBoolean getConfig(){
		for(SimpleFormFieldConfiguration config : configs)
			if(config instanceof SFFCBoolean)
				return (SFFCBoolean) config;
		return null;
	}
	
	@Override
	public Object getValue(Widget field){
		CheckBox cb = (CheckBox)(field);
		return cb.getValue();
	}

}
