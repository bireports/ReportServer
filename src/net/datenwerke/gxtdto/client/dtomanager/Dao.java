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
 
 
package net.datenwerke.gxtdto.client.dtomanager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import net.datenwerke.gxtdto.client.dtomanager.callback.DaoAsyncCallback;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

/**
 * 
 *
 */
abstract public class Dao {

	@Inject
	protected static ClientDtoManagerService dtoManager;

	protected <X extends Dto> X unproxy(X proxy){
		return dtoManager.unproxy(proxy);
	}

	protected <X extends Dto> Collection<X> unproxy(Collection<X> proxy){
		return dtoManager.unproxy(proxy);
	}
	public <X extends Dto> X registerDto(X dto){
		return dtoManager.registerDto(dto);
	}
	
	public <X extends Dto> Collection<X> registerDtos(Collection<X> dtos){
		return dtoManager.registerDtos(dtos);
	}
	
	public <X extends Dto> void detachDto(X dto){
		dtoManager.detachDto(dto);
	}
	
	public <X extends Dto> void detachDtos(Collection<X> dtos){
		dtoManager.detachDtos(dtos);
	}
	
	public void registerDtoContainer(DtoContainer container){
		dtoManager.registerDtoContainer(container);
	}
	

	public void registerDtoContainer(Collection<DtoContainer> container) {
		dtoManager.registerDtoContainer(container);
	}

	protected <D> DaoAsyncCallback<D> transformAndKeepCallback(final AsyncCallback<D> callback){
		DaoAsyncCallback<D> daoCb =  new DaoAsyncCallback<D>(callback, this);
		daoCb.doNotRegisterDtos();
		return daoCb;
	}
	
	
	protected <D extends Dto> DaoAsyncCallback<D> transformDtoCallback(final AsyncCallback<D> callback){
		return new DaoAsyncCallback<D>(callback, this);
	}
	
	protected <D extends DtoContainer> DaoAsyncCallback<D> transformContainerCallback(final AsyncCallback<D> callback){
		return new DaoAsyncCallback<D>(callback, this);
	}

	protected <D extends PagingLoadResult<? extends Dto>> DaoAsyncCallback<D> transformPagingCallback(final AsyncCallback<D> callback){
		DaoAsyncCallback<D> transformed = new DaoAsyncCallback<D>(callback, this);
		transformed.setPostRegisterDtos();
		return transformed;
	}
	
	protected <D extends Collection<? extends Dto>> DaoAsyncCallback<D> transformListCallback(final AsyncCallback<D> callback){
		return new DaoAsyncCallback<D>(callback, this);
	}
	
	protected <D extends ListLoadResult<? extends Dto>> DaoAsyncCallback<D> transformListLoadCallback(final AsyncCallback<D> callback){
		return new DaoAsyncCallback<D>(callback, this);
	}
	
	protected <D extends Collection<? extends DtoContainer>> DaoAsyncCallback<D> transformListContainerCallback(final AsyncCallback<D> callback){
		return new DaoAsyncCallback<D>(callback, this);
	}

	
	
}
