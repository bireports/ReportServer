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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.provider;

import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTree;
import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTreeFactory;
import net.datenwerke.gf.client.treedb.TreeDBUIService;
import net.datenwerke.gf.client.treedb.stores.EnhancedTreeStore;
import net.datenwerke.rs.dashboard.client.dashboard.DashboardTreeLoaderDao;
import net.datenwerke.rs.dashboard.client.dashboard.DashboardUiModule;
import net.datenwerke.rs.dashboard.client.dashboard.dto.AbstractDashboardManagerNodeDto;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BasicTreeProvider implements Provider<ManagerHelperTree>{

	private final TreeDBUIService treeDBUIService;
	private final DashboardTreeLoaderDao treeLoader;
	private final ManagerHelperTreeFactory treeFactory;
	
	@Inject
	public BasicTreeProvider(
		TreeDBUIService treeDBUIService,	
		DashboardTreeLoaderDao treeLoader,
		ManagerHelperTreeFactory treeFactory
		){
		
		this.treeDBUIService = treeDBUIService;
		this.treeLoader = treeLoader;
		this.treeFactory = treeFactory;
	}

	public ManagerHelperTree get() {
		/* store */
		EnhancedTreeStore store = treeDBUIService.getUITreeStore(AbstractDashboardManagerNodeDto.class, treeLoader, false);
		
		/* build tree */
		final ManagerHelperTree tree = treeFactory.create(DashboardUiModule.class, store, treeLoader);
		tree.configureIconProvider();

		return tree;
	}
}
