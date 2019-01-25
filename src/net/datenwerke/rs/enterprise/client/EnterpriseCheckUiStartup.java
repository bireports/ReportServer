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
 
 
package net.datenwerke.rs.enterprise.client;

import com.google.inject.Inject;

import net.datenwerke.gf.client.dispatcher.DispatcherService;
import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.waitonevent.CallbackOnEventDone;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;

public class EnterpriseCheckUiStartup {

	@Inject
	public EnterpriseCheckUiStartup(
		final WaitOnEventUIService waitOnEventService,
		final EnterpriseCheckDao enterpriseDao,
		final EnterpriseUiService enterpriseService
		) {	

		SynchronousCallbackOnEventTrigger entepriseCallbackBefore = createCallback(waitOnEventService, enterpriseDao, enterpriseService, EnterpriseCheckUiModule.REPORTSERVER_ENTERPRISE_DETERMINED_BEFORE_LOGIN);
		SynchronousCallbackOnEventTrigger entepriseCallbackAfter = createCallback(waitOnEventService, enterpriseDao, enterpriseService, EnterpriseCheckUiModule.REPORTSERVER_ENTERPRISE_DETERMINED_AFTER_LOGIN);
		
		waitOnEventService.callbackOnEvent(DispatcherService.REPORTSERVER_EVENT_USER_LOGGED_IN_APPLICATION_LOADED, entepriseCallbackAfter);
		
		waitOnEventService.callbackOnEvent(LoginService.REPORTSERVER_EVENT_BEFORE_AUTH_WINDOW_LOAD, entepriseCallbackBefore);
	}

	protected SynchronousCallbackOnEventTrigger createCallback(final WaitOnEventUIService waitOnEventService, final EnterpriseCheckDao enterpriseDao, final EnterpriseUiService enterpriseService, final String trigger) {
		return new SynchronousCallbackOnEventTrigger() {
			@Override
			public void execute(final WaitOnEventTicket ticket) {
				enterpriseDao.isEnterprise(new RsAsyncCallback<EnterpriseInformationDto>(){
					@Override
					public void onSuccess(EnterpriseInformationDto result) {
						try{
							if(null != result){
								((EnterpriseUiServiceImpl)enterpriseService).setEnterprise(result.isEnterprise());
								((EnterpriseUiServiceImpl)enterpriseService).setEvaluation(result.isEvaluation());
								((EnterpriseUiServiceImpl)enterpriseService).setEnterpriseJarAvailable(result.isEnterpriseJarAvailable());
							}
						} finally {
							waitOnEventService.triggerEvent(trigger, new CallbackOnEventDone() {
								@Override
								public void execute() {
									waitOnEventService.signalProcessingDone(ticket);
								}
							});
						}


					}
					@Override
					public void onFailure(Throwable caught) {
						try{
							super.onFailure(caught);
							waitOnEventService.triggerEvent(trigger);
						} finally {
							waitOnEventService.signalProcessingDone(ticket);
						}
					}
				});
				
			}
		};
	}
}
