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
 
 
package net.datenwerke.gxtdto.client.utils.handlers;

import com.sencha.gxt.data.shared.event.StoreAddEvent;
import com.sencha.gxt.data.shared.event.StoreClearEvent;
import com.sencha.gxt.data.shared.event.StoreDataChangeEvent;
import com.sencha.gxt.data.shared.event.StoreFilterEvent;
import com.sencha.gxt.data.shared.event.StoreHandlers;
import com.sencha.gxt.data.shared.event.StoreRecordChangeEvent;
import com.sencha.gxt.data.shared.event.StoreRemoveEvent;
import com.sencha.gxt.data.shared.event.StoreSortEvent;
import com.sencha.gxt.data.shared.event.StoreUpdateEvent;

public class GenericStoreHandler<M> implements StoreHandlers<M> {

	protected void handleEvent() {
		
	}
	
	protected void handleDataChangeEvent() {
		
	}

	
	@Override
	public void onAdd(StoreAddEvent<M> event) {
		handleEvent();
		handleDataChangeEvent();
	}

	@Override
	public void onRemove(StoreRemoveEvent<M> event) {
		handleEvent();	
		handleDataChangeEvent();
	}

	@Override
	public void onFilter(StoreFilterEvent<M> event) {
		handleEvent();		
	}

	@Override
	public void onClear(StoreClearEvent<M> event) {
		handleEvent();		
		handleDataChangeEvent();
	}

	@Override
	public void onUpdate(StoreUpdateEvent<M> event) {
		handleEvent();		
		handleDataChangeEvent();
	}

	@Override
	public void onDataChange(StoreDataChangeEvent<M> event) {
		handleEvent();	
		handleDataChangeEvent();
	}

	@Override
	public void onRecordChange(StoreRecordChangeEvent<M> event) {
		handleEvent();	
		handleDataChangeEvent();
	}

	@Override
	public void onSort(StoreSortEvent<M> event) {
		handleEvent();		
	}

}
