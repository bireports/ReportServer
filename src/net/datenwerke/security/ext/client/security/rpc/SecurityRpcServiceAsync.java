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
 
 
package net.datenwerke.security.ext.client.security.rpc;

import java.util.Collection;
import java.util.List;

import net.datenwerke.security.client.security.GenericTargetIdentifier;
import net.datenwerke.security.client.security.dto.AceDto;
import net.datenwerke.security.client.security.dto.GenericSecurityTargetContainer;
import net.datenwerke.security.client.security.dto.SecurityViewInformation;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SecurityRpcServiceAsync {

	void loadSecurityViewInformation(AbstractNodeDto node,
			AsyncCallback<SecurityViewInformation> callback);

	void editACE(AbstractNodeDto node, AceDto ace, AsyncCallback<AceDto> callback);

	void removeACEs(AbstractNodeDto node, List<AceDto> aceDtos,
			AsyncCallback<Void> callback);

	void addACE(AbstractNodeDto node, AsyncCallback<AceDto> callback);

	void aceMoved(AbstractNodeDto node, AceDto ace,
			int index, AsyncCallback<AceDto> callback);

	void loadGenericSecurityViewInformation(GenericTargetIdentifier targetIdentifier,
			AsyncCallback<SecurityViewInformation> callback);

	void addACE(GenericTargetIdentifier targetIdentifier, AsyncCallback<AceDto> callback);

	void aceMoved(GenericTargetIdentifier targetIdentifier, AceDto ace, int index,
			AsyncCallback<AceDto> callback);

	void editACE(GenericTargetIdentifier targetIdentifier, AceDto ace, AsyncCallback<AceDto> callback);

	void removeACEs(GenericTargetIdentifier targetIdentifier, List<AceDto> aceDtos,
			AsyncCallback<Void> callback);

	void loadGenericRights(
			Collection<GenericTargetIdentifier> targetIdentifiers,
			AsyncCallback<GenericSecurityTargetContainer> callback);

}
