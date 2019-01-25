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
 
 
package net.datenwerke.gxtdto.client.i18n.remotemessages;

import java.util.HashMap;

import net.datenwerke.gxtdto.client.i18n.remotemessages.rpc.RemoteMessageRpcServiceAsync;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class RemoteMessageCache {
	
	private static RemoteMessageCache instance;
	private HashMap<String, HashMap<String, String>> cache;
	
	
	public static RemoteMessageCache getInstance(){
		if(null == instance)
			instance = new RemoteMessageCache();
		
		return instance;
	}
	
	private RemoteMessageCache() {
		
	}
	
	public void init(RemoteMessageRpcServiceAsync service, final AsyncCallback<Void> initCompleteCallback){
		 service.getMessages(getCurrentLocale(), new AsyncCallback<HashMap<String,HashMap<String,String>>>() {
			
			@Override
			public void onSuccess(HashMap<String, HashMap<String, String>> result) {
				cache = result;
				initCompleteCallback.onSuccess(null);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				initCompleteCallback.onFailure(caught);
			}
		});
	}
	
	private String getCurrentLocale() {
		String locale = Window.Location.getParameter("locale");
		if(null == locale){
			locale = Cookies.getCookie("locale");
		}
		 return locale;
	}
	
	public String getMessage(String msgClass, String key){
		if(null == cache)
			return "null";
		
		if(! cache.containsKey(msgClass)) {
			System.out.println("No such message file "+msgClass+". While locating key " + key);
//			System.out.println("Known files are " + cache.keySet());
			return "NULL";
//			throw new RuntimeException("No such message file "+msgClass+". While locating key " + key);
		}

		HashMap<String, String> keys = cache.get(msgClass);
		
		if(! keys.containsKey(key)){
			System.out.println("No such key " + key + " in message file " + msgClass);
			System.out.println("Known keys are " + keys.keySet());
			return "NULL";
//			throw new RuntimeException("No such key " + key + " in message file " + msgClass);
		}
			
		return keys.get(key);
	}
	
	

}
