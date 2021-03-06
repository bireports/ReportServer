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
 
 
package net.datenwerke.security.ext.client.security.ui.aclview;

import java.util.List;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.security.client.security.dto.AceDto;
import net.datenwerke.security.client.security.dto.HierarchicalAceDto;
import net.datenwerke.security.client.security.dto.SecurityViewInformation;
import net.datenwerke.security.client.treedb.dto.SecuredAbstractNodeDto;
import net.datenwerke.security.ext.client.usermanager.provider.annotations.UserManagerTreeBasicSingleton;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class NodeACLView extends BasicACLView<SecuredAbstractNodeDto> {

	@Inject
	public NodeACLView(
		@UserManagerTreeBasicSingleton UITree userManagerTree) {
		super(userManagerTree);
	}


	protected AceDto createNewACE() {
		return new HierarchicalAceDto();
	}
	
	protected void doInitialize(AsyncCallback<SecurityViewInformation> callback) {
		/* load neccessary information */
		securityDao.loadSecurityViewInformation(target, callback);
	}

	protected void doRemoveACEs(final List<AceDto> aces, AsyncCallback<Void> callback) {
		securityDao.removeACEs(target, aces, callback);
	}
	
	protected void doAddAce(AsyncCallback<AceDto> callback) {
		securityDao.addACE(target, callback);
	}
	
	protected void doAceMoved(AceDto ace, int index, AsyncCallback<AceDto> callback) {
		securityDao.aceMoved(target, ace, index, callback);
	}

	protected void doEditACE(AceDto ace, AsyncCallback<AceDto> callback) {
		securityDao.editACE(target, ace, callback);
	}
}
