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
 
 
package net.datenwerke.treedb.client.treedb.rpc;

import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.NeedForcefulDeleteClientException;
import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

public interface RPCTreeManager {

	public AbstractNodeDto moveNodeInsert(AbstractNodeDto node,  AbstractNodeDto reference, int index, Dto state) throws ServerCallFailedException;
	
	public AbstractNodeDto moveNodeAppend(AbstractNodeDto node, AbstractNodeDto reference, Dto state) throws ServerCallFailedException;
	
	public List<AbstractNodeDto> moveNodesAppend(List<AbstractNodeDto> node, AbstractNodeDto reference, Dto state) throws ServerCallFailedException;

	public AbstractNodeDto insertNode(AbstractNodeDto objectTypeToInsert, AbstractNodeDto node, Dto state) throws ServerCallFailedException;
	
	public AbstractNodeDto updateNode(AbstractNodeDto node, Dto state) throws ServerCallFailedException;
	
	public void deleteNode(AbstractNodeDto node, Dto state) throws ServerCallFailedException, NeedForcefulDeleteClientException;
	
	public void deleteNodeWithForce(AbstractNodeDto node, Dto state) throws ServerCallFailedException;
	
	public AbstractNodeDto refreshNode(AbstractNodeDto node, Dto state) throws ServerCallFailedException;
	
	public AbstractNodeDto duplicateNode(AbstractNodeDto toDuplicate, Dto state) throws ServerCallFailedException;

	AbstractNodeDto setFlag(AbstractNodeDto node, long flagToSet, long flagToUnset) throws ServerCallFailedException;

}
