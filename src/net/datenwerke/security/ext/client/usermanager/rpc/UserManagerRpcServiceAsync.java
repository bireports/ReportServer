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
 
 
package net.datenwerke.security.ext.client.usermanager.rpc;

import java.util.Collection;
import java.util.List;

import net.datenwerke.security.client.usermanager.dto.UserDto;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownGroup;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUser;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.ListLoadResult;

public interface UserManagerRpcServiceAsync {

	void getStrippedDownUsers(AsyncCallback<ListLoadResult<StrippedDownUser>> callback);

	void getStrippedDownGroups(AsyncCallback<ListLoadResult<StrippedDownGroup>> callback);

	void getStrippedDownUsers(Collection<Long> ids,
			AsyncCallback<List<StrippedDownUser>> callback);

	void changeActiveUserData(UserDto userDto, AsyncCallback<UserDto> callback);
	
}
