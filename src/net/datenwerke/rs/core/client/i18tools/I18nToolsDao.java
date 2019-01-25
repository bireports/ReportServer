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

import javax.inject.Inject;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.i18n.I18nToolsUIService;
import net.datenwerke.rs.core.client.i18tools.dto.FormatPatternsDto;
import net.datenwerke.rs.core.client.i18tools.rpc.I18nToolsRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class I18nToolsDao extends Dao {

	private final I18nToolsRpcServiceAsync rpcService;
	private final I18nToolsUIService i18nToolsService;

	@Inject
	public I18nToolsDao(I18nToolsRpcServiceAsync rpcService, I18nToolsUIService i18nToolsService) {
		this.rpcService = rpcService;
		this.i18nToolsService = i18nToolsService;
	}
	
	public void getDecimalSeparator(AsyncCallback<String> callback){
		rpcService.getDecimalSeparator(callback);
	}
	
	public void setDecimalSeparator(String separator, AsyncCallback<Void> callback){
		if(i18nToolsService instanceof I18nToolsUiServiceImpl)
			((I18nToolsUiServiceImpl)i18nToolsService).setUserDecimalSeparator(separator);
		rpcService.setDecimalSeparator(separator, callback);
	}
	
	public void getFormatPatterns(AsyncCallback<FormatPatternsDto> callback) {
		rpcService.getFormatPatterns(callback);
	}
}
