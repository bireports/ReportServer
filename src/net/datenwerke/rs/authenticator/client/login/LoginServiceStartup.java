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
 
 
package net.datenwerke.rs.authenticator.client.login;

import java.util.List;
import java.util.Set;

import net.datenwerke.gf.client.dispatcher.DispatcherService;
import net.datenwerke.gf.client.homepage.hooks.HomepageHeaderContentHook;
import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.servercommunication.callback.AsyncCallbackSuccessHook;
import net.datenwerke.gxtdto.client.servercommunication.callback.ViolatedSecurityHook;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ViolatedSecurityExceptionDto;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.authenticator.client.login.hookers.AccountInhibitionPostAuthenticateClientHook;
import net.datenwerke.rs.authenticator.client.login.hookers.HomepageLogoutHook;
import net.datenwerke.rs.authenticator.client.login.hooks.ClientPAMHook;
import net.datenwerke.rs.authenticator.client.login.pam.ClientPAM;
import net.datenwerke.rs.authenticator.client.login.pam.UserPasswordClientPAM;
import net.datenwerke.rs.authenticator.client.login.sessiontimeout.SessionTimeoutWatchdog;
import net.datenwerke.security.client.usermanager.dto.UserDto;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class LoginServiceStartup {

	@Inject
	public LoginServiceStartup(
		final HookHandlerService hookHandler,
		final WaitOnEventUIService waitOnEventService,
		HomepageLogoutHook logoutBtnHook,
		final LoginDao loginDao,
		Provider<UserPasswordClientPAM> userPasswordClientPamProvider, 
		
		Provider<AccountInhibitionPostAuthenticateClientHook> accountInhibitionPostAuthenticateClientHook,
		
		final LoginService loginService
		){


		hookHandler.attachHooker(PostAuthenticateClientHook.class, accountInhibitionPostAuthenticateClientHook);
		
		hookHandler.attachHooker(ClientPAMHook.class, new ClientPAMHook(userPasswordClientPamProvider));
		
		
		/* attach hooks */
		hookHandler.attachHooker(HomepageHeaderContentHook.class, logoutBtnHook);
		hookHandler.attachHooker(AsyncCallbackSuccessHook.class, SessionTimeoutWatchdog.getAsyncCallbackSuccessHook());
		hookHandler.attachHooker(ViolatedSecurityHook.class, new ViolatedSecurityHook() {

			@Override
			public void violationOccured(final ViolatedSecurityExceptionDto caught, final List<ViolatedSecurityHook> chain) {
				loginDao.isAuthenticated(new AsyncCallback<UserDto>(){
					@Override
					public void onFailure(Throwable caught) {
						goToLoginPage();
					}
					
					@Override
					public void onSuccess(UserDto result) {
						if(null != result){
							if(chain.size() > 0){
								ViolatedSecurityHook next = chain.remove(0);
								next.violationOccured(caught, chain);
							}
						}else{
							goToLoginPage();
						}
					}
					
					private void goToLoginPage(){
						loginService.logoff();
					}
				});
			}
		});
		
		/* request callback after login and check for rights */
		waitOnEventService.callbackOnEvent(DispatcherService.REPORTSERVER_EVENT_BEFORE_STARTUP, new SynchronousCallbackOnEventTrigger() {
			
			public void execute(final WaitOnEventTicket ticket) {
				loginDao.getRequiredClientModules(new RsAsyncCallback<Set<String>>() {
					@Override
					public void onSuccess(Set<String> result) {
						
						for(ClientPAMHook h : hookHandler.getHookers(ClientPAMHook.class)){
							ClientPAM pam = h.getObject().get();
							if(!result.contains(pam.getModuleName())){
								hookHandler.detachHooker(ClientPAMHook.class, h);
							}
						}
						
						waitOnEventService.signalProcessingDone(ticket);
					}
				});
			}
		});
	
		
		/* low level history listener */
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				if(! loginService.isAuthenticated())
					loginService.fireHistoryEventOnLogin(event);
			}
		});
	}
}
