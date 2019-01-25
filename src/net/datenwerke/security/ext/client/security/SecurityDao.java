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
 
 
package net.datenwerke.security.ext.client.security;

import java.util.Collection;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.security.client.security.GenericTargetIdentifier;
import net.datenwerke.security.client.security.dto.AceDto;
import net.datenwerke.security.client.security.dto.GenericSecurityTargetContainer;
import net.datenwerke.security.client.security.dto.SecurityViewInformation;
import net.datenwerke.security.ext.client.security.rpc.SecurityRpcServiceAsync;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class SecurityDao extends Dao {

	private final SecurityRpcServiceAsync rpcService;

	@Inject
	public SecurityDao(SecurityRpcServiceAsync rpcService) {
		super();
		this.rpcService = rpcService;
	}
	
	public void loadSecurityViewInformation(AbstractNodeDto node,
			AsyncCallback<SecurityViewInformation> callback){
		rpcService.loadSecurityViewInformation(node, transformAndKeepCallback(callback));
	}

	public void editACE(AbstractNodeDto node, AceDto ace, AsyncCallback<AceDto> callback){
		rpcService.editACE(node, ace, transformDtoCallback(callback));
	}

	public void removeACEs(AbstractNodeDto node, List<AceDto> aceDtos,
			AsyncCallback<Void> callback){
		rpcService.removeACEs(node, aceDtos, transformAndKeepCallback(callback));
	}

	public void addACE(AbstractNodeDto node, AsyncCallback<AceDto> callback){
		rpcService.addACE(node, transformDtoCallback(callback));
	}

	public void aceMoved(AbstractNodeDto node, AceDto ace,
			int index, AsyncCallback<AceDto> callback){
		rpcService.aceMoved(node, ace, index, transformDtoCallback(callback));
	}

	public void loadGenericSecurityViewInformation(GenericTargetIdentifier targetIdentifier,
			AsyncCallback<SecurityViewInformation> callback){
		rpcService.loadGenericSecurityViewInformation(targetIdentifier, transformAndKeepCallback(callback));
	}

	public void addACE(GenericTargetIdentifier targetIdentifier, AsyncCallback<AceDto> callback){
		rpcService.addACE(targetIdentifier, transformDtoCallback(callback));
	}

	public void aceMoved(GenericTargetIdentifier targetIdentifier, AceDto ace, int index,
			AsyncCallback<AceDto> callback){
		rpcService.aceMoved(targetIdentifier, ace, index, transformDtoCallback(callback));
	}

	public void editACE(GenericTargetIdentifier targetIdentifier, AceDto ace, AsyncCallback<AceDto> callback){
		rpcService.editACE(targetIdentifier, ace, transformDtoCallback(callback));
	}

	public void removeACEs(GenericTargetIdentifier targetIdentifier, List<AceDto> aceDtos,
			AsyncCallback<Void> callback){
		rpcService.removeACEs(targetIdentifier, aceDtos, transformAndKeepCallback(callback));
	}

	public void loadGenericRights(
			Collection<GenericTargetIdentifier> targetIdentifiers,
			AsyncCallback<GenericSecurityTargetContainer> callback){
		rpcService.loadGenericRights(targetIdentifiers, transformAndKeepCallback(callback));
	}
}
