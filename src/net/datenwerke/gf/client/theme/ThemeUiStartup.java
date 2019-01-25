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
 
 
package net.datenwerke.gf.client.theme;

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gf.client.theme.dto.ThemeUiConfig;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;

import com.google.inject.Inject;

public class ThemeUiStartup {

	@Inject
	public ThemeUiStartup(	HookHandlerService hookHandlerService, 
			final WaitOnEventUIService waitOnEventService,
			final ThemeDao themeDao,
			final ThemeUiService themeService
		) {
		
		SynchronousCallbackOnEventTrigger callback = new SynchronousCallbackOnEventTrigger() {
			@Override
			public void execute(final WaitOnEventTicket ticket) {
				themeDao.loadUiTheme(new RsAsyncCallback<ThemeUiConfig>(){
					public void onSuccess(ThemeUiConfig result) {
						themeService.setThemeConfig(result);
						waitOnEventService.signalProcessingDone(ticket);
					};
					
					@Override
					public void onFailure(Throwable caught) {
						super.onFailure(caught);
						waitOnEventService.signalProcessingDone(ticket);
					}
				});
			}
		};
		
		waitOnEventService.callbackOnEvent(LoginService.REPORTSERVER_EVENT_AFTER_ANY_LOGIN, callback);
		waitOnEventService.callbackOnEvent(LoginService.REPORTSERVER_EVENT_BEFORE_AUTH_WINDOW_LOAD, callback);
	}
}
