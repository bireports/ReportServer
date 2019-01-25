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
 
 
package net.datenwerke.gf.client.treedb;

import java.util.Collection;
import java.util.List;

import net.datenwerke.gf.client.treedb.stores.EnhancedTreeLoader;
import net.datenwerke.gf.client.treedb.stores.EnhancedTreeStore;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.treedb.client.treedb.TreeDbLoaderDao;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.TreeLoader;


/**
 * 
 *
 */
public interface TreeDBUIService {

	public abstract RpcProxy<AbstractNodeDto, List<AbstractNodeDto>> getUITreeProxy(TreeDbLoaderDao treeLoaderDao);
	
	/**
	 * Constructs a basic tree loader needed to create an ext tree.
	 * @return
	 */
	public abstract EnhancedTreeLoader getUITreeLoader(TreeDbLoaderDao treeLoaderDao);

	/**
	 * Creates a loader object using the specified proxy.
	 * @param rpcTreeLoader
	 * @param proxy
	 * @return
	 */
	public abstract EnhancedTreeLoader getUITreeLoader(TreeDbLoaderDao rpcTreeLoader, RpcProxy<AbstractNodeDto, List<AbstractNodeDto>> proxy);
	
	/**
	 * Constructs a basic tree store needed to create an ext tree.
	 * @return
	 */
	public EnhancedTreeStore getUITreeStore(Class<?> baseType, TreeDbLoaderDao treeLoaderDao, boolean changeAware);

	public abstract EnhancedTreeStore getUITreeStore(Class<?> baseType, TreeDbLoaderDao rpcTreeLoader, TreeLoader<AbstractNodeDto> loader, boolean changeAware);

	public abstract EnhancedTreeStore getUITreeStore(Class<?> baseType, TreeDbLoaderDao rpcTreeLoader, RpcProxy<AbstractNodeDto, List<AbstractNodeDto>> proxy, boolean changeAware);

	EnhancedTreeStore getUITreeStore(Class<?> baseType, TreeDbLoaderDao rpcTreeLoader, boolean changeAware,
			Collection<Dto2PosoMapper> wlFilters);
	
	EnhancedTreeStore getUITreeStore(Class<?> baseType, TreeDbLoaderDao rpcTreeLoader, boolean changeAware,
			Collection<Dto2PosoMapper> wlFilters, Collection<Dto2PosoMapper> blFilters);

	
}
