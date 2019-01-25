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
 
 
package net.datenwerke.rs.saiku.client.saiku;

import java.util.HashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

import net.datenwerke.gf.client.config.ClientConfigDao;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;

public class SaikuUiServiceImpl implements SaikuUiService {

	private final ClientConfigDao configDao;
	
	private HashMap<String,String> settings;
	
	@Inject
	public SaikuUiServiceImpl(ClientConfigDao configDao) {
		super();
		this.configDao = configDao;
	}

	@Override
	public void getSettings(final AsyncCallback<HashMap<String, String>> callback) {
		if(null != settings){
			callback.onSuccess(settings);
			return;
		}
		
		configDao.getConfigProperties("saiku.properties", new RsAsyncCallback<HashMap<String,String>>(){
			@Override
			public void onSuccess(HashMap<String, String> result) {
				settings = result;
				if(null == settings)
					settings = new HashMap<>();
				callback.onSuccess(settings);
			}
			@Override
			public void onFailure(Throwable caught) {
				settings = new HashMap<>();
				callback.onSuccess(settings);
			}
		});
	}
}
