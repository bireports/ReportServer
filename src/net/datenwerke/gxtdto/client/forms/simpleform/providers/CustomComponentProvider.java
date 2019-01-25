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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.providers;

import net.datenwerke.gxtdto.client.eventbus.EventBusHelper;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHook;
import net.datenwerke.gxtdto.client.forms.simpleform.json.SimpleFormFieldJson;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.configs.SFFCCustomComponent;
import net.datenwerke.gxtdto.client.forms.simpleform.providers.dummy.CustomComponent;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;

public class CustomComponentProvider implements FormFieldProviderHook {

	private SimpleFormFieldConfiguration[] configs;

	@Override
	public boolean consumes(Class<?> type,
			SimpleFormFieldConfiguration... configs) {
		boolean consumes =  type.equals(CustomComponent.class) && configs.length > 0 && configs[0] instanceof SFFCCustomComponent;
		if(consumes)
			this.configs = configs;
		return consumes;
	}
	
	@Override
	public boolean consumes(String type,
			SimpleFormFieldJson config) {
		return false;
	}

	@Override
	public void init(String name, SimpleForm form) {
		
	}

	@Override
	public Widget createFormField() {
		return getCustomComponent();
	}
	
	public Widget getCustomComponent(){
		return ((SFFCCustomComponent)configs[0]).getComponent();
	}

	@Override
	public void addFieldBindings(Object model, ValueProvider vp, Widget field) {
	}

	@Override
	public void removeFieldBindings(Object model, Widget field) {
	}

	public HandlerRegistration addValueChangeHandler(ValueChangeHandler handler){
		 return EventBusHelper.EVENT_BUS.addHandler(ValueChangeEvent.getType(), handler);
	}
	
	public void fireEvent(GwtEvent<?> event) {
		EventBusHelper.EVENT_BUS.fireEvent(event);
	}

	@Override
	public Object getValue(Widget field) {
		return null;
	}
	
	@Override
	public String getStringValue(Widget field) {
		return null;
	}
	
	@Override
	public void setValue(Widget field, Object value) {
		//
	}

	@Override
	public Widget reload(Widget field) {
		return createFormField();
	}

	@Override
	public boolean isDecorateable() {
		return false;
	}

}
