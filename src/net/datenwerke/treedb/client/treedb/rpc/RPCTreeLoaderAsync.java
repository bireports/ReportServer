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
 
 
package net.datenwerke.treedb.client.treedb.rpc;

import java.util.Collection;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.client.treedb.dto.EntireTreeDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RPCTreeLoaderAsync {

	public void getRoot(Dto state, AsyncCallback<List<AbstractNodeDto>> callback);
	
	public void getChildren(AbstractNodeDto node, Dto state, AsyncCallback<List<AbstractNodeDto>> callback);
	
	public void getChildrenAsFto(AbstractNodeDto node, Dto state, AsyncCallback<String[][]> callback);
	
	public void loadAll(Dto state, Collection<Dto2PosoMapper> filters, Collection<Dto2PosoMapper> blackListFilters, AsyncCallback<EntireTreeDTO> callback);
	
	public void loadAll(Dto state, AsyncCallback<EntireTreeDTO> callback);
	
	public void loadAllAsFto(Dto state,  AsyncCallback<String[][]> callback);
	
	public void loadFullViewNode(AbstractNodeDto node, Dto state, AsyncCallback<AbstractNodeDto> callback);
	
	public void loadNodeById(Long id, Dto state, AsyncCallback<AbstractNodeDto> callback);
}
