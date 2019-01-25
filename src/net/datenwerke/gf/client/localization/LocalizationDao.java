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
 
 
package net.datenwerke.gf.client.localization;

import java.util.Map;

import net.datenwerke.gf.client.localization.rpc.LocalizationRpcServiceAsync;
import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class LocalizationDao extends Dao {
	
	private final LocalizationRpcServiceAsync rpcService;

	@Inject
	public LocalizationDao(LocalizationRpcServiceAsync rpcService) {
		this.rpcService = rpcService;
	}
	
	
	public void getLanguageSelectorConfiguration(AsyncCallback<Map<String, String>> callback){
		rpcService.getLanguageSelectorConfiguration(callback);
	}

	public void setUserLocale(String locale, AsyncCallback<Void> callback){
		rpcService.setUserLocale(locale, callback);
	}
	
	public String getCurrentClientLocale() {
		String locale = Window.Location.getParameter("locale");
		if(null == locale){
			locale = Cookies.getCookie("locale");
		}
		
		return locale;
	}


	public void setUserTimezone(String timezone, RsAsyncCallback<Void> callback) {
		rpcService.setUserTimezone(timezone, callback);
	}
	
}
