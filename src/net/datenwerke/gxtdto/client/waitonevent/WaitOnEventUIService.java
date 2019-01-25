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
 
 
package net.datenwerke.gxtdto.client.waitonevent;

/**
 * 
 *
 */
public interface WaitOnEventUIService{

	/**
	 * Callback will be removed after its execution.
	 * 
	 * @param event
	 * @param callback
	 */
	public void callbackOnEvent(String event, SynchronousCallbackOnEventTrigger callback);

	/**
	 * Callback will be called after every occurance of the event.
	 * 
	 * @param event
	 * @param callback
	 */
	public void permanentCallbackOnEvent(String event, SynchronousCallbackOnEventTrigger callback);
	
	public void removePermanentCallbackOnEvent(String event, SynchronousCallbackOnEventTrigger callback);
	
	public void callbackOnFinishingProcessing(String event, CallbackOnEventDone callback);
	
	public void permanentCallbackOnFinishingProcessing(String event, CallbackOnEventDone callback);
	
	public void removePermanentCallbackOnFinishingProcessing(String event, CallbackOnEventDone callback);
	
	public void triggerEvent(String event);
	
	public void triggerEvent(String event, CallbackOnEventDone callback);
	
	public void signalProcessingDone(WaitOnEventTicket ticket);
	
	
}