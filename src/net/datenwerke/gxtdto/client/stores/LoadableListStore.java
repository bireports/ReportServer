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
 
 
package net.datenwerke.gxtdto.client.stores;

import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.LoadResultListStoreBinding;
import com.sencha.gxt.data.shared.loader.Loader;

public class LoadableListStore<C, M, D extends ListLoadResult<M>> extends ListStore<M> implements
		HasLoader<C, D> {

	private Loader<C, D> loader;
	
	public LoadableListStore(ModelKeyProvider<? super M> keyProvider, Loader<C, D> loader) {
		super(keyProvider);
		this.loader = loader;
		
		loader.addLoadHandler(new LoadResultListStoreBinding<C, M, D>(this));
	}

	@Override
	public Loader<C, D> getLoader() {
		return loader;
	}

}
