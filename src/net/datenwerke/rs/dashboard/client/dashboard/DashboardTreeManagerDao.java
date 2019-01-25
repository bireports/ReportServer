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
 
 
package net.datenwerke.rs.dashboard.client.dashboard;

import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.rs.dashboard.client.dashboard.dto.AbstractDashboardManagerNodeDto;
import net.datenwerke.rs.dashboard.client.dashboard.rpc.DashboardTreeManagerAsync;
import net.datenwerke.treedb.client.treedb.TreeDbManagerDao;

import com.google.inject.Inject;

public class DashboardTreeManagerDao extends TreeDbManagerDao {

	@Inject
	public DashboardTreeManagerDao(DashboardTreeManagerAsync treeManager) {
		super(treeManager);
	}

	@Override
	public Dto2PosoMapper getBaseNodeMapper() {
		return AbstractDashboardManagerNodeDto.newPosoMapper();
	}

}
