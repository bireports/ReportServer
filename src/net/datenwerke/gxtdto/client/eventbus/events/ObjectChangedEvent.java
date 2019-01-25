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
 
 
package net.datenwerke.gxtdto.client.eventbus.events;

import net.datenwerke.gxtdto.client.eventbus.handlers.ObjectChangedEventHandler;
import net.datenwerke.gxtdto.client.eventbus.handlers.has.HasObjectChangedEventHandler;

import com.google.gwt.event.shared.GwtEvent;
import com.sencha.gxt.core.client.ValueProvider;

public class ObjectChangedEvent<T> extends GwtEvent<ObjectChangedEventHandler<T>> {
	
	public static final Type<ObjectChangedEventHandler<?>> TYPE = new Type<ObjectChangedEventHandler<?>>();
	
	private T object;
	
	private ValueProvider<?, ?> valueProvider;

	private Object oldValue;
	
	/**
	 * Constructor.
	 */
	protected ObjectChangedEvent() {
	}
	
	public ObjectChangedEvent(T object, ValueProvider<?, ?> valueProvider, Object oldValue) {
		this.object = object;
		this.valueProvider = valueProvider;
		this.oldValue = oldValue;
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ObjectChangedEventHandler<T>> getAssociatedType() {
		return (Type) TYPE;
	}

	@Override
	protected void dispatch(ObjectChangedEventHandler<T> handler) {
		 handler.onObjectChangedEvent(this);
	}

	public static <T> void fire(HasObjectChangedEventHandler<T> source, T object, ValueProvider<?, ?> valueProvider) {
		fire(source, object, valueProvider, null);
	}
	
	public static <T> void fire(HasObjectChangedEventHandler<T> source, T object, ValueProvider<?, ?> valueProvider, Object oldValue) {
		ObjectChangedEvent<T> event = new ObjectChangedEvent<T>(object, valueProvider, oldValue);
		source.fireEvent(event);
	}
	
	public T getObject(){
		return object;
	}
	
	public ValueProvider<?, ?> getValueProvider() {
		return valueProvider;
	}

	public String getPropertyPath() {
		return null != valueProvider ? valueProvider.getPath() : null;
	}
	
	public Object getOldValue() {
		return oldValue;
	}
	
}
