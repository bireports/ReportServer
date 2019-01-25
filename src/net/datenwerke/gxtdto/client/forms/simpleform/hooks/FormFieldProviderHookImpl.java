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
 
 
package net.datenwerke.gxtdto.client.forms.simpleform.hooks;

import net.datenwerke.gxtdto.client.eventbus.EventBusHelper;
import net.datenwerke.gxtdto.client.forms.binding.HasValueFieldBinding;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleForm;
import net.datenwerke.gxtdto.client.forms.simpleform.SimpleFormFieldConfiguration;
import net.datenwerke.gxtdto.client.forms.simpleform.json.SimpleFormFieldJson;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.ValueProvider;

/**
 * Base class for providers that simply create a Field.
 * 
 *
 */
public abstract class FormFieldProviderHookImpl implements FormFieldProviderHook {

	protected String name;
	protected Class<?> type;
	protected SimpleFormFieldConfiguration[] configs;
	protected SimpleForm form;
	
	protected boolean json;
	protected String jsonType;
	protected SimpleFormFieldJson jsonConfig;
	
	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}
	
	public SimpleForm getForm(){
		return form;
	}

	public SimpleFormFieldConfiguration[] getConfigs() {
		return configs;
	}
	
	public boolean consumes(Class<?> type,	SimpleFormFieldConfiguration... configs){
		boolean consumes = doConsumes(type, configs);
		if(consumes){
			this.type = type;
			this.configs = configs;
		}
		return consumes;
	}
	
	@Override
	public final boolean consumes(String type,
			SimpleFormFieldJson config) {
		boolean consumes = doConsumes(type, config);
		if(consumes){
			this.json = true;
			this.jsonType = type;
			this.jsonConfig = config;
		}
		return consumes;
	}
	
	public boolean doConsumes(String type,	SimpleFormFieldJson config){
		return false;
	}
	
	public abstract boolean doConsumes(Class<?> type,	SimpleFormFieldConfiguration... configs);

	public void init(String name, SimpleForm form){
		this.name = name;
		this.form = form;
	}

	protected HasValueFieldBinding fieldBinding;
	
	public abstract Widget createFormField();

	@Override
	public Object getValue(Widget field){
		if(field instanceof HasValue)
			return ((HasValue)field).getValue();
		return null;
	}
	
	@Override
	public String getStringValue(Widget field) {
		if(field instanceof HasValue)
			return String.valueOf(((HasValue)field).getValue());
		return null;
	}
	
	@Override
	public void setValue(Widget field, Object object){
		if(json){
			setStringValue(field, (String) object);
			return;
		}
		if(field instanceof HasValue)
			((HasValue)field).setValue(object, true);
	}
	
	protected void setStringValue(Widget field, String object) {
		
	}

	public void addFieldBindings(Object model, ValueProvider vp, Widget field) {
		if(field instanceof HasValueChangeHandlers)
			fieldBinding = new HasValueFieldBinding((HasValueChangeHandlers) field, model, vp);
		else
			fieldBinding = new HasValueFieldBinding(this, model, vp);
	}
	
	public void removeFieldBindings(Object model, Widget field) {
		if(null != fieldBinding){
			fieldBinding.unbind();
			fieldBinding = null;
		}
	}
	
	@Override
	public Widget reload(Widget field) {
		return createFormField();
	}
	
	public HandlerRegistration addValueChangeHandler(ValueChangeHandler handler){
		 return EventBusHelper.EVENT_BUS.addHandlerToSource(ValueChangeEvent.getType(), this, handler);
	}
	
	public void fireEvent(GwtEvent<?> event) {
		EventBusHelper.EVENT_BUS.fireEventFromSource(event, this);
	}

	@Override
	public boolean isDecorateable() {
		return true;
	}
}
