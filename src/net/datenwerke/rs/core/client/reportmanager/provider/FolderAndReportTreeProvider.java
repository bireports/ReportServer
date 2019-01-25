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
 
 
package net.datenwerke.rs.core.client.reportmanager.provider;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTree;
import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTreeFactory;
import net.datenwerke.gf.client.treedb.TreeDBUIService;
import net.datenwerke.gf.client.treedb.stores.EnhancedTreeStore;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.rs.core.client.reportmanager.ReportManagerTreeLoaderDao;
import net.datenwerke.rs.core.client.reportmanager.ReportManagerUIModule;
import net.datenwerke.rs.core.client.reportmanager.dto.interfaces.posomap.ReportVariantDto2PosoMap;
import net.datenwerke.rs.core.client.reportmanager.dto.posomap.ReportFolderDto2PosoMap;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.AbstractReportManagerNodeDto;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.posomap.ReportDto2PosoMap;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class FolderAndReportTreeProvider implements Provider<ManagerHelperTree>{

	private final TreeDBUIService treeDBUIService;
	private final ReportManagerTreeLoaderDao reportManagerDao;
	private final ManagerHelperTreeFactory treeFactory;
	
	@Inject
	public FolderAndReportTreeProvider(
		TreeDBUIService treeDBUIService,	
		ReportManagerTreeLoaderDao reportManagerDao,
		ManagerHelperTreeFactory treeFactory
		){
		
		this.treeDBUIService = treeDBUIService;
		this.reportManagerDao = reportManagerDao;
		this.treeFactory = treeFactory;
	}

	public ManagerHelperTree get() {
		/* store */
		List<Dto2PosoMapper>  wlFilters = new ArrayList<Dto2PosoMapper>();
		wlFilters.add(new ReportFolderDto2PosoMap());
		wlFilters.add(new ReportDto2PosoMap());
		
		List<Dto2PosoMapper>  blFilters = new ArrayList<Dto2PosoMapper>();
		blFilters.add(new ReportVariantDto2PosoMap());
		EnhancedTreeStore store = treeDBUIService.getUITreeStore(AbstractReportManagerNodeDto.class, reportManagerDao, false, wlFilters, blFilters);
		
		/* build tree */
		final ManagerHelperTree tree = treeFactory.create(ReportManagerUIModule.class, store, reportManagerDao);
		tree.configureIconProvider();
	
		return tree;
	}
}
