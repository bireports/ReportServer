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
 
 
package net.datenwerke.rs.eximport.client.eximport.im;

import java.util.Collection;
import java.util.Map;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportConfigDto;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportPostProcessConfigDto;
import net.datenwerke.rs.eximport.client.eximport.im.rpc.ImportRpcServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class ImporterDao extends Dao {

	private final ImportRpcServiceAsync rpcService;
	
	@Inject
	public ImporterDao(
		ImportRpcServiceAsync rpcService	
		){
		
		/* store objects */
		this.rpcService = rpcService;
	}
	
	public void uploadXML(String xmldata, AsyncCallback<Collection<String>> callback){
		rpcService.uploadXML(xmldata, transformAndKeepCallback(callback));
	}
	
	public void performImport(Map<String, ImportConfigDto> configMap, Map<String, ImportPostProcessConfigDto> postProcessMap, AsyncCallback<Void> callback){
		rpcService.performImport(configMap, postProcessMap, transformAndKeepCallback(callback));
	}
	
	public void reset(AsyncCallback<Void> callback){
		rpcService.reset(transformAndKeepCallback(callback));
	}
	
	public void initViaFile(AsyncCallback<Collection<String>> callback){
		rpcService.initViaFile(transformAndKeepCallback(callback));
	}
	
	public void invalidateConfig(AsyncCallback<Void> callback){
		rpcService.invalidateConfig(transformAndKeepCallback(callback));
	}
	
}
