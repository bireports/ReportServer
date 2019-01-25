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

import java.util.ArrayList;
import java.util.List;

import net.datenwerke.gf.client.history.HistoryCallback;
import net.datenwerke.gf.client.history.HistoryLocation;
import net.datenwerke.gxtdto.client.eventbus.events.SubmoduleDisplayRequest;
import net.datenwerke.gxtdto.client.utils.handlers.GenericStoreHandler;
import net.datenwerke.treedb.client.treedb.dto.AbstractNodeDto;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;
import com.sencha.gxt.data.shared.TreeStore;
import com.sencha.gxt.widget.core.client.event.ExpandItemEvent;
import com.sencha.gxt.widget.core.client.event.ExpandItemEvent.ExpandItemHandler;

public class TreeDBHistoryCallback implements HistoryCallback {
	
	interface HistoryCallbackListener{
		public void handleEvent();
	}
	
	public static final String HISTORY_PARAMETER_TREE_PATH = "path";
	private static final int MAX_RETRY_CNT = 50;

	
	private final Provider<UITree> treeProvider;
	private final EventBus eventbus;
	private final Provider<?> submoduleComponentProvider;
	private final String submoduleParentId;

	private boolean selectParent;
	
	public TreeDBHistoryCallback(
			Provider<UITree> treeProvider,
			EventBus eventbus,
			Provider<?> submoduleComponentProvider,
			String submoduleParentId
		) {
		this.treeProvider = treeProvider;
		this.eventbus = eventbus;
		this.submoduleComponentProvider = submoduleComponentProvider;
		this.submoduleParentId = submoduleParentId;
	}
	
	public TreeDBHistoryCallback(
			Provider<UITree> treeProvider,
			EventBus eventbus,
			final Widget submodule,
			String submoduleParentId
		) {
		this.treeProvider = treeProvider;
		this.eventbus = eventbus;
		this.submoduleComponentProvider = new Provider<Widget>(){
			@Override
			public Widget get() {
				return submodule;
			}
		};
		this.submoduleParentId = submoduleParentId;
	}
	
	@Override
	public void locationChanged(HistoryLocation location) {
		if(location.hasParameter(HISTORY_PARAMETER_TREE_PATH)){
			String[] pathElements = location.getParameterValue(HISTORY_PARAMETER_TREE_PATH).split("\\.");
			final ArrayList<Long> nodes = new ArrayList<Long>();
			for(String elem : pathElements){
				nodes.add(Long.valueOf(elem));
			}
			
			/* display module */
			eventbus.fireEvent(new SubmoduleDisplayRequest(submoduleComponentProvider.get(), submoduleParentId));
			if(nodes.isEmpty())
				return;
			
			if(selectParent)
				nodes.remove(nodes.size()-1);

			/* load tree and select correct item */
			final UITree tree = treeProvider.get();
			final TreeStore<AbstractNodeDto> store = tree.getStore();
			
			
			final HistoryCallbackListener callback = new HistoryCallbackListener() {
				@Override
				public void handleEvent() {
					doHandleEvent(nodes,tree,store);
				}
			};
			
			/* wait for root to load or begin expansion directly */
			if(store.getRootItems().isEmpty()){
				final List<HandlerRegistration> dummy = new ArrayList<HandlerRegistration>();
				HandlerRegistration addStoreAddHandler = store.addStoreHandlers(new GenericStoreHandler<AbstractNodeDto>(){
					@Override
					protected void handleDataChangeEvent() {
						if(store.getRootCount() > 0){
							for(HandlerRegistration reg : dummy)
								reg.removeHandler();
							expandPath(new ArrayList<Long>(nodes), tree, callback, 0);
						}
					}
				});

				dummy.add(addStoreAddHandler);
			} else
				expandPath(new ArrayList<Long>(nodes), tree, callback, 0);
			
		}
	}
	

	
	private void expandPath(final ArrayList<Long> nodes, final UITree tree, final HistoryCallbackListener callback, final int retryCount) {
		if(nodes.size() <= 1){
			callback.handleEvent();
			return;
		}
				
		/* load first item and check if it is in tree */
		Long id = nodes.get(0);
		final AbstractNodeDto item = tree.getStore().findModelWithKey(String.valueOf(id));
		if(null == item || ! tree.isNodeInTree(item)){
			if(retryCount > MAX_RETRY_CNT)
				callback.handleEvent();
			else {
				Scheduler.get().scheduleFixedDelay(new RepeatingCommand() {
					
					@Override
					public boolean execute() {
						expandPath(nodes, tree, callback, retryCount+1);
						return false;
					}
				},100);
			}
			
			return;
		}
		
		/* remove from nodes */
		nodes.remove(0);
		
		if(tree.isLeaf(item)){
			callback.handleEvent();
			return;
		}
		if(tree.isExpanded(item)){
			expandPath(nodes, tree, callback, 0);
			return;
		}
		
		final List<HandlerRegistration> dummy = new ArrayList<HandlerRegistration>();
		final HandlerRegistration addExpandHandler = tree.addExpandHandler(new ExpandItemHandler<AbstractNodeDto>() {
			@Override
			public void onExpand(ExpandItemEvent<AbstractNodeDto> event) {
				if(event.getItem() == item){
					for(HandlerRegistration reg : dummy)
						reg.removeHandler();
					expandPath(nodes, tree, callback, 0);
				}
			}
		});
		dummy.add(addExpandHandler);
		
		tree.setExpanded(item, true);
	}

	protected void doHandleEvent(final ArrayList<Long> nodes, final UITree tree, final TreeStore<AbstractNodeDto> store) {
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				int i = nodes.size() - 1;
				while(i >= 0){
					AbstractNodeDto item = store.findModelWithKey(String.valueOf(nodes.get(i--)));
					if(null != item){
						tree.getSelectionModel().select(item, false);
						tree.scrollIntoView(item);
						break;
					}
				}							
			}
		});
	}
	
	public void setSelectParent(boolean b) {
		this.selectParent = b;
	}

}
