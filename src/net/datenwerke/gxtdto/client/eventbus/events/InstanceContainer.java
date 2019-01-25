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

import net.datenwerke.gxtdto.client.eventbus.EventBusHelper;
import net.datenwerke.gxtdto.client.eventbus.handlers.ObjectChangedEventHandler;
import net.datenwerke.gxtdto.client.eventbus.handlers.has.HasObjectChangedEventHandler;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

public final class InstanceContainer<T> implements HasObjectChangedEventHandler<T> {
	
	private final T instance;

	public InstanceContainer(T instance) {
		this.instance = instance;
	}

	public T getInstance() {
		return instance;
	}

	@Override
	public void fireEvent(GwtEvent<?> event) {
		EventBusHelper.EVENT_BUS.fireEventFromSource(event,this);
	}

	@Override
	public HandlerRegistration addObjectChangedHandler(
			ObjectChangedEventHandler<T> handler) {
		throw new IllegalStateException("not implemented");
	}

	@Override
	public HandlerRegistration addInstanceChangedHandler(
			ObjectChangedEventHandler<T> handler) {
		throw new IllegalStateException("not implemented");
	};
}