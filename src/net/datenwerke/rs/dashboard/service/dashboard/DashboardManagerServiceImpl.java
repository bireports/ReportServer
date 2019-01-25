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
 
 
package net.datenwerke.rs.dashboard.service.dashboard;

import java.util.List;

import javax.persistence.EntityManager;

import net.datenwerke.rs.dashboard.service.dashboard.entities.AbstractDashboardManagerNode;
import net.datenwerke.rs.dashboard.service.dashboard.entities.AbstractDashboardManagerNode__;
import net.datenwerke.rs.utils.simplequery.PredicateType;
import net.datenwerke.rs.utils.simplequery.annotations.QueryByAttribute;
import net.datenwerke.rs.utils.simplequery.annotations.QueryById;
import net.datenwerke.rs.utils.simplequery.annotations.SimpleQuery;
import net.datenwerke.security.service.treedb.SecuredTreeDBManagerImpl;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class DashboardManagerServiceImpl extends SecuredTreeDBManagerImpl<AbstractDashboardManagerNode> implements DashboardManagerService {

	private final Provider<EntityManager> entityManagerProvider;
	
	@Inject
	public DashboardManagerServiceImpl(
			Provider<EntityManager> entityManagerProvider) {
		this.entityManagerProvider = entityManagerProvider;
	}

	@QueryById
	@Override
	public AbstractDashboardManagerNode getNodeById(long id) {
		return null; // magic
	}

	@Override
	@QueryByAttribute(where=AbstractDashboardManagerNode__.parent,type=PredicateType.IS_NULL)
	public List<AbstractDashboardManagerNode> getRoots() {
		return null;
	}

	@Override
	@SimpleQuery
	public List<AbstractDashboardManagerNode> getAllNodes() {
		return null;
	}

	

}
