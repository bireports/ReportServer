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
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RPCTreeManagerAsync {

	public void deleteNode(AbstractNodeDto node, Dto state, AsyncCallback<Void> callback);
	
	public void deleteNodeWithForce(AbstractNodeDto node, Dto state, AsyncCallback<Void> callback);
	
	public void insertNode(AbstractNodeDto dummyNode, AbstractNodeDto node, Dto state, AsyncCallback<AbstractNodeDto> callback);
	
	public void updateNode(AbstractNodeDto node, Dto state, AsyncCallback<AbstractNodeDto> callback);
	
	public void moveNodeInsert(AbstractNodeDto node, AbstractNodeDto reference, int position, Dto state, AsyncCallback<AbstractNodeDto> asyncCallback);
	
	public void moveNodeAppend(AbstractNodeDto node, AbstractNodeDto reference, Dto state, AsyncCallback<AbstractNodeDto> asyncCallback);
	
	public void moveNodesAppend(List<AbstractNodeDto> nodes,
			AbstractNodeDto reference, Dto state,
			AsyncCallback<List<AbstractNodeDto>> transformDtoCallback);
	
	public void refreshNode(AbstractNodeDto node, Dto state, AsyncCallback<AbstractNodeDto> callback);

	public void duplicateNode(AbstractNodeDto toDuplicate, Dto state, AsyncCallback<AbstractNodeDto> callback);
	
	public void setFlag(AbstractNodeDto node, long flagToSet, long flagToUnset, AsyncCallback<AbstractNodeDto> callback);

	
}
