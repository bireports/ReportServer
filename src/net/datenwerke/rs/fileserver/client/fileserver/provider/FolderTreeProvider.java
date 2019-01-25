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
 
 
package net.datenwerke.rs.fileserver.client.fileserver.provider;

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTree;
import net.datenwerke.gf.client.managerhelper.tree.ManagerHelperTreeFactory;
import net.datenwerke.gf.client.treedb.TreeDBUIService;
import net.datenwerke.gf.client.treedb.stores.EnhancedTreeStore;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.rs.fileserver.client.fileserver.FileServerTreeLoaderDao;
import net.datenwerke.rs.fileserver.client.fileserver.FileServerUiModule;
import net.datenwerke.rs.fileserver.client.fileserver.dto.AbstractFileServerNodeDto;
import net.datenwerke.rs.fileserver.client.fileserver.dto.posomap.FileServerFolderDto2PosoMap;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class FolderTreeProvider implements Provider<ManagerHelperTree>{

	private final TreeDBUIService treeDBUIService;
	private final FileServerTreeLoaderDao treeLoader;
	private final ManagerHelperTreeFactory treeFactory;
	
	@Inject
	public FolderTreeProvider(
		TreeDBUIService treeDBUIService,	
		FileServerTreeLoaderDao treeLoader,
		ManagerHelperTreeFactory treeFactory
		){
		
		this.treeDBUIService = treeDBUIService;
		this.treeLoader = treeLoader;
		this.treeFactory = treeFactory;
	}

	public ManagerHelperTree get() {
		/* store */
		List<Dto2PosoMapper> filters = new ArrayList<Dto2PosoMapper>();
		filters.add(new FileServerFolderDto2PosoMap());
		EnhancedTreeStore store = treeDBUIService.getUITreeStore(AbstractFileServerNodeDto.class, treeLoader, false, filters);
		
		/* build tree */
		final ManagerHelperTree tree = treeFactory.create(FileServerUiModule.class, store, treeLoader);
		tree.configureIconProvider();

		return tree;
	}
}
