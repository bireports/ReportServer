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
 
 
package net.datenwerke.gxtdto.client.servercommunication.callback;

import net.datenwerke.gxtdto.client.servercommunication.callback.locale.CallbackHandlerMessages;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ExpectedException;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.core.client.util.DelayedTask;

public class TimeoutCallback <T> implements AsyncCallback<T>{

	private final NotamCallback<T> callback;
	private final DelayedTask  dt;
	
	public TimeoutCallback(NotamCallback<T> callback){
		this(15000, callback);
	}
	
	public TimeoutCallback(int timeout, NotamCallback<T> callback) {
		this.callback = callback;
		
		dt = new DelayedTask() {
			@Override
			public void onExecute() {
				onTimeout();
			}
		};
		
		dt.delay(timeout);
	}
	
	private void onTimeout(){
		if(null != callback.getRequest()){
			if(!callback.getRequest().isPending()){
				return;
			}
			callback.getRequest().cancel();
		}
		doOnTimeout();
		callback.onFailure(new ExpectedException(CallbackHandlerMessages.INSTANCE.timeoutMessage()));
	}
	
	public void doOnTimeout(){};
	
	public void onFailure(Throwable caught) {
		dt.cancel();
		callback.onFailure(caught);
		
	}
	
	public void onSuccess(T result) {
		dt.cancel();
		callback.onSuccess(result);
	};
}
