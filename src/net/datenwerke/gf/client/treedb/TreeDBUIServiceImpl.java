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
 
 
package net.datenwerke.gf.client.treedb;

import java.util.Collection;
import java.util.List;

import net.datenwerke.gf.client.treedb.stores.EnhancedTreeLoader;
import net.datenwerke.gf.client.treedb.stores.EnhancedTreeStore;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.treedb.client.treedb.TreeDbLoaderDao;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Singleton;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.TreeLoader;

/**
 * 
 *
 */
@Singleton
public class TreeDBUIServiceImpl implements TreeDBUIService {

	@Override
	public RpcProxy<AbstractNodeDto, List<AbstractNodeDto>> getUITreeProxy(
			final TreeDbLoaderDao rpcTreeLoader) {

		RpcProxy<AbstractNodeDto, List<AbstractNodeDto>> proxy = new RpcProxy<AbstractNodeDto, List<AbstractNodeDto>>() {
			@Override
			public void load(AbstractNodeDto loadConfig,
					AsyncCallback<List<AbstractNodeDto>> callback) {
				if(null == loadConfig)
					rpcTreeLoader.getRoot(callback);
				else if(loadConfig instanceof AbstractNodeDto)
					rpcTreeLoader.getChildren((AbstractNodeDto) loadConfig, true, callback);
			}
		};
		
		return proxy;
	}
	
	@Override
	public EnhancedTreeLoader getUITreeLoader(TreeDbLoaderDao rpcTreeLoader) {
		RpcProxy<AbstractNodeDto, List<AbstractNodeDto>> proxy = getUITreeProxy(rpcTreeLoader);
		
		EnhancedTreeLoader loader = new EnhancedTreeLoader(proxy);
		
		return loader;
	}
	
	@Override
	public EnhancedTreeLoader getUITreeLoader(TreeDbLoaderDao rpcTreeLoader, RpcProxy<AbstractNodeDto, List<AbstractNodeDto>> proxy) {
		EnhancedTreeLoader loader = new EnhancedTreeLoader(proxy);
		return loader;
	}

	@Override
	public EnhancedTreeStore getUITreeStore(Class<?> baseType, TreeDbLoaderDao rpcTreeLoader, boolean changeAware) {
		TreeLoader<AbstractNodeDto> loader = getUITreeLoader(rpcTreeLoader);
		
		EnhancedTreeStore store = new EnhancedTreeStore(rpcTreeLoader, loader, changeAware);
		store.addTypeFilter(baseType);
		configureStore(store);
		
		return store;
	}
	
	@Override
	public EnhancedTreeStore getUITreeStore(Class<?> baseType, TreeDbLoaderDao rpcTreeLoader, boolean changeAware, Collection<Dto2PosoMapper> wlFilters) {
		TreeLoader<AbstractNodeDto> loader = getUITreeLoader(rpcTreeLoader);
		
		EnhancedTreeStore store = new EnhancedTreeStore(rpcTreeLoader, loader, changeAware, wlFilters);
		store.addTypeFilter(baseType);
		configureStore(store);
		
		return store;
	}
	
	@Override
	public EnhancedTreeStore getUITreeStore(Class<?> baseType, TreeDbLoaderDao rpcTreeLoader, boolean changeAware, Collection<Dto2PosoMapper> wlFilters, Collection<Dto2PosoMapper> blFilters) {
		TreeLoader<AbstractNodeDto> loader = getUITreeLoader(rpcTreeLoader);
		
		EnhancedTreeStore store = new EnhancedTreeStore(rpcTreeLoader, loader, changeAware, wlFilters, blFilters);
		store.addTypeFilter(baseType);
		configureStore(store);
		
		return store;
	}
	
	@Override
	public EnhancedTreeStore getUITreeStore(Class<?> baseType, TreeDbLoaderDao rpcTreeLoader, RpcProxy<AbstractNodeDto, List<AbstractNodeDto>> proxy, boolean changeAware) {
		TreeLoader<AbstractNodeDto> loader = getUITreeLoader(rpcTreeLoader, proxy);
		
		EnhancedTreeStore store = new EnhancedTreeStore(rpcTreeLoader, loader, changeAware);
		store.addTypeFilter(baseType);
		configureStore(store);
		
		return store;
	}
	
	@Override
	public EnhancedTreeStore getUITreeStore(Class<?> baseType, TreeDbLoaderDao rpcTreeLoader, TreeLoader<AbstractNodeDto> loader, boolean changeAware) {
		EnhancedTreeStore store = new EnhancedTreeStore(rpcTreeLoader, loader, changeAware);
		store.addTypeFilter(baseType);
		configureStore(store);
		
		return store;
	}

	private void configureStore(EnhancedTreeStore store) {
		store.setAlphabeticSorter();
	}


}
