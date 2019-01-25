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
 
 
package net.datenwerke.rs.globalconstants.client.globalconstants;

import java.util.Collection;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.callback.DaoAsyncCallback;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.rs.globalconstants.client.globalconstants.dto.GlobalConstantDto;
import net.datenwerke.rs.globalconstants.client.globalconstants.rpc.GlobalConstantsRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

/**
 * 
 *
 */
public class GlobalConstantsDao extends Dao {

	private final GlobalConstantsRpcServiceAsync rpcService;
	
	@Inject
	public GlobalConstantsDao(
		GlobalConstantsRpcServiceAsync rpcService
		){
		this.rpcService = rpcService;
	}
	
	public void loadGlobalConstants(AsyncCallback<List<GlobalConstantDto>> callback){
		rpcService.loadGlobalConstants(transformListCallback(callback));
	}

	public void addNewConstant(
			RsAsyncCallback<GlobalConstantDto> callback) {
		rpcService.addNewConstant(transformDtoCallback(callback));
	}
	
	public void updateConstant(GlobalConstantDto constantDto,
			AsyncCallback<GlobalConstantDto> callback){
		rpcService.updateConstant(constantDto, transformDtoCallback(callback));
	}

	public void removeAllConstants(AsyncCallback<Void> callback, Collection<GlobalConstantDto> oldConstants){
		DaoAsyncCallback<Void> tCallback = transformAndKeepCallback(callback);
		tCallback.setDtosToDetach((Collection)oldConstants);
		rpcService.removeAllConstants(tCallback);
	}
	
	public void removeConstants(Collection<GlobalConstantDto> constants,
			AsyncCallback<Void> callback){
		DaoAsyncCallback<Void> tCallback = transformAndKeepCallback(callback);
		tCallback.setDtosToDetach((Collection)constants);
		rpcService.removeConstants(constants, tCallback);
	}
}
