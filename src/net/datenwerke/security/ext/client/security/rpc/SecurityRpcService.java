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
 
 
package net.datenwerke.security.ext.client.security.rpc;

import java.util.Collection;
import java.util.List;

import net.datenwerke.gxtdto.client.servercommunication.exceptions.ServerCallFailedException;
import net.datenwerke.security.client.security.GenericTargetIdentifier;
import net.datenwerke.security.client.security.dto.AceDto;
import net.datenwerke.security.client.security.dto.GenericSecurityTargetContainer;
import net.datenwerke.security.client.security.dto.SecurityViewInformation;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("security_security")
public interface SecurityRpcService extends RemoteService {

	public SecurityViewInformation loadSecurityViewInformation(AbstractNodeDto node) throws ServerCallFailedException;
	
	public SecurityViewInformation loadGenericSecurityViewInformation(GenericTargetIdentifier targetIdentifier) throws ServerCallFailedException;

	public AceDto addACE(AbstractNodeDto node) throws ServerCallFailedException;
	
	public AceDto aceMoved(AbstractNodeDto node, AceDto ace, int index) throws ServerCallFailedException;
	
	public AceDto editACE(AbstractNodeDto node, AceDto ace) throws ServerCallFailedException;
	
	public void removeACEs(AbstractNodeDto node, List<AceDto> aceDtos) throws ServerCallFailedException;
	
	public AceDto addACE(GenericTargetIdentifier targetIdentifier) throws ServerCallFailedException;
	
	public AceDto aceMoved(GenericTargetIdentifier targetIdentifier, AceDto ace, int index) throws ServerCallFailedException;
	
	public AceDto editACE(GenericTargetIdentifier targetIdentifier, AceDto ace) throws ServerCallFailedException;
	
	public void removeACEs(GenericTargetIdentifier targetIdentifier, List<AceDto> aceDtos) throws ServerCallFailedException;

	public GenericSecurityTargetContainer loadGenericRights(Collection<GenericTargetIdentifier> targetIdentifiers);
	
}
