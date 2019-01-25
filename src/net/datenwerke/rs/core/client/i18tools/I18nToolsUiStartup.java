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
 
 
package net.datenwerke.rs.core.client.i18tools;

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.rs.core.client.i18tools.dto.FormatPatternsDto;

import com.google.inject.Inject;

public class I18nToolsUiStartup {

	@Inject
	public I18nToolsUiStartup(
			final I18nToolsDao dao,
			final I18nToolsUiServiceImpl service,
			final WaitOnEventUIService waitOnEventService, 
			final FormatUiHelper formatUiHelper
			){

		/* load generic rights after login */
		waitOnEventService.callbackOnEvent(LoginService.REPORTSERVER_EVENT_AFTER_ANY_LOGIN, new SynchronousCallbackOnEventTrigger() {
			@Override
			public void execute(final WaitOnEventTicket ticket) {
				dao.getDecimalSeparator(new RsAsyncCallback<String>(){
					@Override
					public void onSuccess(String result) {
						service.setUserDecimalSeparator(result);
					}
				});

				dao.getFormatPatterns(new RsAsyncCallback<FormatPatternsDto>(){
					public void onSuccess(FormatPatternsDto result) {
						formatUiHelper.setFormatPatterns(result);
					}
				});

				waitOnEventService.signalProcessingDone(ticket);
			}
		});
	}
}
