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
 
 
package net.datenwerke.treedb.client.treedb;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.Dao;
import net.datenwerke.gxtdto.client.dtomanager.Dto;
import net.datenwerke.gxtdto.client.dtomanager.Dto2PosoMapper;
import net.datenwerke.gxtdto.client.dtomanager.callback.DaoAsyncCallback;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;
import net.datenwerke.treedb.client.treedb.dto.EntireTreeDTO;
import net.datenwerke.treedb.client.treedb.dto.decorator.AbstractNodeDtoDec;
import net.datenwerke.treedb.client.treedb.rpc.RPCTreeLoaderAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class TreeDbLoaderDao extends Dao {

	protected final RPCTreeLoaderAsync treeLoader;
	
	protected Dto state;

	protected final TreeDbFtoConverter treeDbFtoConverter;

	
	public TreeDbLoaderDao(RPCTreeLoaderAsync treeLoader, TreeDbFtoConverter treeDbFtoConverter){
		this.treeLoader = treeLoader;
		this.treeDbFtoConverter = treeDbFtoConverter;
	}

	public void getRoot( AsyncCallback<List<AbstractNodeDto>> callback){
		DaoAsyncCallback asyncCallback = transformListCallback(callback);
		treeLoader.getRoot(state, asyncCallback);
	}
	
	public void getChildren(AbstractNodeDto node, boolean useFto, AsyncCallback<List<AbstractNodeDto>> callback){
		if(!useFto || null == this.treeDbFtoConverter){
			DaoAsyncCallback asyncCallback = transformListCallback(callback);
			treeLoader.getChildren(node, state,asyncCallback);
		}else{
			getChildrenAsFto(node, callback);
		}
	}

	public void loadAll(Collection<Dto2PosoMapper> whiteListFilters, Collection<Dto2PosoMapper> blackListFilters, AsyncCallback<EntireTreeDTO> callback, boolean useFto){
		if(null == whiteListFilters && null == blackListFilters)
			loadAll(callback, useFto);
		else {
			DaoAsyncCallback asyncCallback = transformContainerCallback(callback); 
			treeLoader.loadAll(state, whiteListFilters, blackListFilters, asyncCallback);
		}
	}

	
	public void loadAll(AsyncCallback<EntireTreeDTO> callback, boolean useFto){
		if(useFto && null != this.treeDbFtoConverter){
			loadAllAsFto(callback);
		}else{
			DaoAsyncCallback asyncCallback = transformContainerCallback(callback); 
			treeLoader.loadAll(state, asyncCallback);
		}
	}
	
	private void getChildrenAsFto(AbstractNodeDto node,  final AsyncCallback<List<AbstractNodeDto>> callback){
		final AsyncCallback handledCallback = transformListCallback(callback);
		treeLoader.getChildrenAsFto(node, node, new AsyncCallback<String[][]>() {

			@Override
			public void onFailure(Throwable caught) {
				handledCallback.onFailure(caught);
			}

			@Override
			public void onSuccess(String[][] result) {
				List<AbstractNodeDto> nodes = new ArrayList<AbstractNodeDto>();
				
				for(String[] fto : result){
					AbstractNodeDtoDec dto = treeDbFtoConverter.convert(fto);
					nodes.add(dto);
				}
				
				handledCallback.onSuccess(nodes);
			}
		});
	}
	
	private void loadAllAsFto(final AsyncCallback<EntireTreeDTO> callback){
//		final DaoAsyncCallback asyncCallback = transformContainerCallback(callback);
		final AsyncCallback asyncCallback = callback;

		treeLoader.loadAllAsFto(state, new AsyncCallback<String[][]>() {
			@Override
			public void onFailure(Throwable caught) {
				asyncCallback.onFailure(caught);
			}
			
			@Override
			public void onSuccess(String[][] result) {
				HashMap<Long, AbstractNodeDtoDec> nodes = new HashMap<Long, AbstractNodeDtoDec>(); 
				
				for(String[] fto : result){
					AbstractNodeDtoDec dto = treeDbFtoConverter.convert(fto);
					nodes.put(dto.getId(), dto);
				}
				
				
				EntireTreeDTO etdto = new EntireTreeDTO();
				
				for(AbstractNodeDtoDec node : nodes.values()){
					if(null != node.getParentNodeId()){
						etdto.addChild(nodes.get(node.getParentNodeId()), node);
					}else{
						etdto.addRoot(node);
					}
				}

				asyncCallback.onSuccess(etdto);
			}
		});
	}
	
	public void loadNode(AbstractNodeDto node, AsyncCallback<AbstractNodeDto> callback){
		treeLoader.loadNodeById(node.getId(), state, transformDtoCallback(callback));
	}
	
	public void loadNodeById(Long id, AsyncCallback<AbstractNodeDto> callback){
		treeLoader.loadNodeById(id, state, transformDtoCallback(callback));
	}
	
	public void loadFullViewNode(AbstractNodeDto node, AsyncCallback<AbstractNodeDto> callback){
		treeLoader.loadFullViewNode(node, state, transformDtoCallback(callback));
	}

	public Dto getState() {
		return state;
	}

	public void setState(Dto state) {
		this.state = state;
	}
	
	
}
