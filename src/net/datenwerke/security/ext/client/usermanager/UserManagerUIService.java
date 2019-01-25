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
 
 
package net.datenwerke.security.ext.client.usermanager;

import net.datenwerke.gxtdto.client.stores.LoadableListStore;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.usermanager.dto.AbstractUserManagerNodeDto;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownGroup;
import net.datenwerke.security.client.usermanager.dto.ie.StrippedDownUser;

import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoader;

public interface UserManagerUIService {

	public ListLoader<ListLoadConfig, ListLoadResult<StrippedDownGroup>> getStrippedGroupLoader();
	public ListLoader<ListLoadConfig, ListLoadResult<StrippedDownUser>> getStrippedUserLoader();
	
	BaseIcon getIcon(AbstractUserManagerNodeDto node);

	public LoadableListStore<ListLoadConfig, StrippedDownUser, ListLoadResult<StrippedDownUser>> getStrippedUserStore();
	public LoadableListStore<ListLoadConfig, StrippedDownGroup, ListLoadResult<StrippedDownGroup>> getStrippedGroupStore();
	
}
