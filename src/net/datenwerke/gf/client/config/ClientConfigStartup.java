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
 
 
package net.datenwerke.gf.client.config;

import javax.inject.Inject;

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

public class ClientConfigStartup {

	@Inject
	public ClientConfigStartup(	
			HookHandlerService hookHandlerService, 
			final WaitOnEventUIService waitOnEventService,
			final ClientConfigService clientConfigService,
			final ClientConfigDao dao
		) {
		
		/* load generic rights after login */
		waitOnEventService.callbackOnEvent(LoginService.REPORTSERVER_EVENT_AFTER_ANY_LOGIN, new SynchronousCallbackOnEventTrigger() {
			@Override
			public void execute(final WaitOnEventTicket ticket) {
				waitOnEventService.signalProcessingDone(ticket);

				dao.getClientConfig(ClientConfigModule.MAIN_CLIENT_CONFIG, new RsAsyncCallback<String>(){
					@Override
					public void onSuccess(String result) {
						clientConfigService.setMainConfig(result);
						
						waitOnEventService.triggerEvent(ClientConfigModule.CLIENT_CONFIG_FILE_LOADED); 
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						super.onFailure(caught);
					}
				});
			}
		});
	}
}
