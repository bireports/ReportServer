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
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.client.treedb.dto.EntireTreeDTO;

public interface RPCTreeLoader {
	public List<AbstractNodeDto> getRoot(Dto state) throws ServerCallFailedException;
	
	public List<AbstractNodeDto> getChildren(AbstractNodeDto node, Dto state) throws ServerCallFailedException;
	
	public String[][] getChildrenAsFto(AbstractNodeDto node, Dto state) throws ServerCallFailedException;

	public EntireTreeDTO loadAll(Dto state, Collection<Dto2PosoMapper> whiteListFilters, Collection<Dto2PosoMapper> blackListFilters) throws ServerCallFailedException;
	
	public EntireTreeDTO loadAll(Dto state) throws ServerCallFailedException;
	
	public String[][] loadAllAsFto(Dto state) throws ServerCallFailedException;
	
	public AbstractNodeDto loadFullViewNode(AbstractNodeDto node, Dto state) throws ServerCallFailedException;

	public AbstractNodeDto loadNodeById(Long id, Dto state)
			throws ServerCallFailedException;
}
