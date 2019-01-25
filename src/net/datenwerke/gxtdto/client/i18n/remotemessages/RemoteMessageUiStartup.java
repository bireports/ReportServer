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
 
 
package net.datenwerke.gxtdto.client.i18n.remotemessages;

import javax.inject.Inject;

import net.datenwerke.gxtdto.client.i18n.remotemessages.rpc.RemoteMessageRpcServiceAsync;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class RemoteMessageUiStartup {

	public static final String REPORTSERVER_EVENT_BEFORE_STARTUP = "REPORTSERVER_EVENT_BEFORE_STARTUP";

	@Inject
	public RemoteMessageUiStartup(final RemoteMessageRpcServiceAsync remoteMessageService, final WaitOnEventUIService waitOnEventService) {
		waitOnEventService.callbackOnEvent(REPORTSERVER_EVENT_BEFORE_STARTUP, new SynchronousCallbackOnEventTrigger() {

			public void execute(final WaitOnEventTicket ticket) {
				RemoteMessageCache.getInstance().init(remoteMessageService, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						waitOnEventService.signalProcessingDone(ticket);								
					}

					@Override
					public void onSuccess(Void result) {
						waitOnEventService.signalProcessingDone(ticket);								
					}
				});

			}
		});
	}

}
