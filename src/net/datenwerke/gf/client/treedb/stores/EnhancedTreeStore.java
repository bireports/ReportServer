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
 
 
package net.datenwerke.gf.client.treedb.stores;

import java.util.Collection;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.dtomanager.stores.DtoAwareTreeStore;
import net.datenwerke.gxtdto.client.utils.modelkeyprovider.DtoIdModelKeyProvider;
import net.datenwerke.treedb.client.treedb.TreeDbLoaderDao;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.client.treedb.dto.EntireTreeDTO;

import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.TreeLoader;

/**
 * 
 *
 */
public class EnhancedTreeStore extends DtoAwareTreeStore<AbstractNodeDto> {

	private final TreeDbLoaderDao treeLoader;

	private Collection<Dto2PosoMapper> entireTreeWhitelistFilters = null;
	private Collection<Dto2PosoMapper> entireTreeBlacklistFilters = null;

	public EnhancedTreeStore(TreeDbLoaderDao treeLoader, TreeLoader<AbstractNodeDto> loader) {
		this(treeLoader, loader, true);
	}
	
	public EnhancedTreeStore(TreeDbLoaderDao treeLoader, TreeLoader<AbstractNodeDto> loader, boolean changeAware) {
		this(new DtoIdModelKeyProvider(), treeLoader, loader, changeAware);
	}

	public EnhancedTreeStore(TreeDbLoaderDao treeLoader, TreeLoader<AbstractNodeDto> loader, boolean changeAware, Collection<Dto2PosoMapper> whitelistFilters) {
		this(treeLoader, loader, changeAware);
		this.entireTreeWhitelistFilters = whitelistFilters;
	}

	public EnhancedTreeStore(TreeDbLoaderDao treeLoader,
			TreeLoader<AbstractNodeDto> loader,
			boolean changeAware,
			Collection<Dto2PosoMapper> wlFilters,
			Collection<Dto2PosoMapper> blFilters) {
		this(treeLoader, loader, changeAware);
		this.entireTreeWhitelistFilters = wlFilters;
		this.entireTreeBlacklistFilters = blFilters;
	}

	public EnhancedTreeStore(ModelKeyProvider<? super AbstractNodeDto> mkp,	TreeDbLoaderDao treeLoader, TreeLoader<AbstractNodeDto> loader, boolean changeAware) {
		super(mkp, loader, changeAware);
		this.treeLoader = treeLoader;
	}

	public void loadEntireTree(boolean useFto){
		loadEntireTree(new EnhancedTreeStoreLoadListener() {
			public void loadComplete() {}
		}, useFto);
	}

	public void loadEntireTree(final EnhancedTreeStoreLoadListener listener, boolean useFto){
		/* tell panel that we will load everything */
		getUiTree().allIsLoaded();

		treeLoader.loadAll(entireTreeWhitelistFilters, entireTreeBlacklistFilters, new RsAsyncCallback<EntireTreeDTO>() {
			@Override
			public void onSuccess(EntireTreeDTO result) {
				/* clear store */
				clear();
				fillEntireTree(result);
				listener.loadComplete();
			}
		}, useFto);
	}

	protected void fillEntireTree(EntireTreeDTO tree) {
		for(AbstractNodeDto root : tree.getRoots()){
			add(root);
			fillTreeWithChildren(root, tree);
		}
	}

	private void fillTreeWithChildren(AbstractNodeDto parent, EntireTreeDTO tree) {
		for(AbstractNodeDto child : tree.getChildrenFor(parent)){
			add(parent, child);
			fillTreeWithChildren(child, tree);
		}

	}

	protected UITree getUiTree(){
		return (UITree)treePanel;
	}


}
