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
 
 
package net.datenwerke.rs.eximport.client.eximport.im.rpc;

import java.util.Collection;
import java.util.Map;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportConfigDto;
import net.datenwerke.rs.eximport.client.eximport.im.dto.ImportPostProcessConfigDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("importrpc")
public interface ImportRpcService extends RemoteService {
	
	public Collection<String> initViaFile() throws ServerCallFailedException;
	
	public Collection<String> uploadXML(String xmldata) throws ServerCallFailedException;

	public void reset() throws ServerCallFailedException;
	
	public void invalidateConfig() throws ServerCallFailedException;
	
	public void performImport(Map<String, ImportConfigDto> configMap, Map<String, ImportPostProcessConfigDto> postProcessMap) throws ServerCallFailedException;
	
}
