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
 
 
package net.datenwerke.rs.authenticator.client.login.sessiontimeout;

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.servercommunication.callback.AsyncCallbackSuccessHook;
import net.datenwerke.rs.authenticator.client.login.rpc.LoginHandlerAsync;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class SessionTimeoutWatchdog {
	
	private static int DEFAULT_SESSION_TIMEOUT = 15 * 60; //seconds
	private static boolean configured = false;
	
	private static int DEFAULT_WARNING_PERIOD = 60; //seconds
	
	private static long lastSuccessfulRequest;
	
	@Inject
	private static LoginService loginService;
	
	@Inject
	private static LoginHandlerAsync loginHandler;

	private static boolean timerRunning = false;
	private static Timer timeoutWarningTimer  = new Timer() {

		@Override
		public void run() {
			if(!configured){
				configured = true;
				
				loginHandler.getSessionTimeout(new AsyncCallback<Integer>() {
					
					@Override
					public void onSuccess(Integer result) {
						DEFAULT_SESSION_TIMEOUT = result;

//						System.out.println("Setting session timeout to " + result + " seconds.");
						
						if(DEFAULT_SESSION_TIMEOUT > 60)
							DEFAULT_SESSION_TIMEOUT -= 60;
					}
					
					@Override
					public void onFailure(Throwable caught) {
					}
				});
			}
			
			if(loginService.isAuthenticated()){
				if(System.currentTimeMillis() - lastSuccessfulRequest > 1000 * (DEFAULT_SESSION_TIMEOUT - DEFAULT_WARNING_PERIOD) ){
					timeoutWarningTimer.cancel();
					timerRunning = false;
					showWarningMessage();
				}
			}
		}
	};
	


	private static AsyncCallbackSuccessHook asyncCallbackSuccessHook = new AsyncCallbackSuccessHook() {
		@Override
		public void onSuccess() {
			
			lastSuccessfulRequest = System.currentTimeMillis();
			
			if(!timerRunning){
				timeoutWarningTimer.scheduleRepeating(5000);
				timerRunning = true;
			}
			
		}
	}; 
	
	private SessionTimeoutWatchdog() {
		// no constructor
	}

	
	protected static void showWarningMessage(){
		SessionTimeoutWarningDialog dialog = new SessionTimeoutWarningDialog(DEFAULT_WARNING_PERIOD);
		dialog.show();
	}
	
	public static AsyncCallbackSuccessHook getAsyncCallbackSuccessHook() {
		return asyncCallbackSuccessHook;
	}
	
	
}
