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
 
 
package net.datenwerke.gxtdto.client.eventbus.handlers.has;

import net.datenwerke.gxtdto.client.eventbus.handlers.ObjectChangedEventHandler;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasObjectChangedEventHandler<T> extends HasHandlers {

	/**
	 * listen to changes of this object.
	 * 
	 * @param handler
	 * @return
	 */
	public HandlerRegistration addObjectChangedHandler(ObjectChangedEventHandler<T> handler);
	
	/**
	 * listen to changes of this particular instance.
	 * S
	 * @param handler
	 * @return
	 */
	public HandlerRegistration addInstanceChangedHandler(ObjectChangedEventHandler<T> handler);
}
