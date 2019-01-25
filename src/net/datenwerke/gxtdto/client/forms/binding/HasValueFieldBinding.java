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
 
 
package net.datenwerke.gxtdto.client.forms.binding;

import net.datenwerke.gxtdto.client.eventbus.events.ObjectChangedEvent;
import net.datenwerke.gxtdto.client.eventbus.handlers.ObjectChangedEventHandler;
import net.datenwerke.gxtdto.client.eventbus.handlers.has.HasObjectChangedEventHandler;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.HasValue;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Util;
import com.sencha.gxt.data.shared.Converter;

@SuppressWarnings({"rawtypes", "unchecked"})
public class HasValueFieldBinding implements ObjectChangedEventHandler, ValueChangeHandler{

	private final HasValueChangeHandlers field;
	
	private HandlerRegistration addObjectChangedHandler;
	private HandlerRegistration addValueChangeHandler;

	private ValueProvider vp;

	private Object model;

	private boolean inFieldUpdate;

	private Converter converter;

	public HasValueFieldBinding(final HasValueChangeHandlers field) {
		this.field = field;
	}
	
	public HasValueFieldBinding(HasValueChangeHandlers field, Object model, ValueProvider vp) {
		this.field = field;
		bind(model, vp);
	}

	public HasValueFieldBinding(HasValueChangeHandlers field, Object model, ValueProvider vp, Converter converter) {
		this.field = field;
		this.converter = converter;
		bind(model, vp);
	}

	public void bind(final Object model, final ValueProvider vp){
		this.model = model;
		this.vp = vp;
		if(null == vp)
			throw new IllegalArgumentException();
		
		addValueChangeHandler = getField().addValueChangeHandler(this);
		
		if(model instanceof HasObjectChangedEventHandler && getField() instanceof HasValue){
			addObjectChangedHandler = ((HasObjectChangedEventHandler)model).addObjectChangedHandler(this);
			
			// initial set
			updateField();
		}
	}
	
	public void unbind(){
		if(null != addObjectChangedHandler)
			addObjectChangedHandler.removeHandler();
		addValueChangeHandler.removeHandler();
		
		model = null;
		vp = null;
		addObjectChangedHandler = null;
		addValueChangeHandler = null;
	}
	
	/**
	 * Updates the model's value with the field value.
	 */
	public void updateModel() {
		if(! isBinded())
			return;
		if(getField() instanceof HasValue)
			updateModel(((HasValue)getField()).getValue());
	}

	protected void updateModel(Object value) {
		vp.setValue(model, convertFieldValue(value));
	}

	protected Object convertModelValue(Object object) {
		if(null == converter)
			return object;
		return converter.convertModelValue(object);
	}

	protected Object convertFieldValue(Object value) {
		if(null == converter)
			return value;
		return converter.convertFieldValue(value);
	}

	public HasValueChangeHandlers getField() {
		return field;
	}

	@Override
	public void onObjectChangedEvent(ObjectChangedEvent event) {
		if(! isBinded())
			return;
		if(null == event.getValueProvider() || ! Util.equalWithNull(event.getValueProvider().getPath(), vp.getPath()))
			return;
		updateField();
	}

	public void updateField() {
		if(! isBinded())
			return;
		
		boolean oldInFieldUpdate = inFieldUpdate;
		inFieldUpdate = true;
		
		if(getField() instanceof HasValue)
			((HasValue)getField()).setValue(convertModelValue(vp.getValue(model)), true);
		
		inFieldUpdate = oldInFieldUpdate;
	}

	@Override
	public void onValueChange(ValueChangeEvent event) {
		if(! isBinded())
			return;
		if(! inFieldUpdate)
			updateModel(event.getValue());
	}

	private boolean isBinded() {
		return null != addValueChangeHandler;
	}
}
