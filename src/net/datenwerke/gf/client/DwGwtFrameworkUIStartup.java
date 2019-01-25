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
 
 
package net.datenwerke.gf.client;

import net.datenwerke.gf.client.dispatcher.DispatcherService;
import net.datenwerke.gf.client.homepage.HomepageDao;
import net.datenwerke.gf.client.uiutils.date.simpleform.DateFormulaProvider;
import net.datenwerke.gxtdto.client.forms.simpleform.hooks.FormFieldProviderHook;
import net.datenwerke.gxtdto.client.servercommunication.callback.HandledAsyncCallback;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class DwGwtFrameworkUIStartup {
	
	@Inject
	public DwGwtFrameworkUIStartup(
			Provider<DateFormulaProvider> dateFormulaProvider,
			
			HookHandlerService hookHandler,
			final HomepageDao homepageDao,
			final WaitOnEventUIService waitOnEventService
			) {
		
		/* simple form */
		hookHandler.attachHooker(FormFieldProviderHook.class, dateFormulaProvider, HookHandlerService.PRIORITY_LOW);
		
		/* Set Window Title */
		waitOnEventService.callbackOnEvent(DispatcherService.REPORTSERVER_EVENT_BEFORE_STARTUP, new SynchronousCallbackOnEventTrigger() {
			
			public void execute(final WaitOnEventTicket ticket) {
				homepageDao.getPageTitle(new HandledAsyncCallback<String>(null) {
					@Override
					public void doOnSuccess(String result) {
						if(null != result){
							Window.setTitle(result);
						}
					}
				});
				waitOnEventService.signalProcessingDone(ticket);
			}
		});
	}

}
