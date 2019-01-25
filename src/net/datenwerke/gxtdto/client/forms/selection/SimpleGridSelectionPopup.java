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
 
 
package net.datenwerke.gxtdto.client.forms.selection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.datenwerke.gxtdto.client.forms.locale.FormsMessages;
import net.datenwerke.gxtdto.client.forms.selection.SelectionPopup.ItemsSelectedCallback;

import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Event;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.dom.XDOM;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoader;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
import com.sencha.gxt.widget.core.client.grid.GridView;

/**
 * 
 *
 * @param <M>
 */
public class SimpleGridSelectionPopup<M> extends Grid<M> {
	
	private Map<ValueProvider<M, String>, String> displayProperties;
	private ListStore<M> allStore;
	private boolean allStoreLoaded = false;
	private boolean alwaysReloadData = false;
	private int columnWidth;

	private SelectionMode popupSelectionMode = SelectionMode.MULTI;
	private ListLoader<ListLoadConfig, ListLoadResult<M>> allItemsStoreloader;
	private String header;

	public SimpleGridSelectionPopup(String displayName, ValueProvider<M, String> displayProperty, ListStore<M> selectedStore, ListStore<M> allStore){
		super();
		this.store = selectedStore;
		this.allStore = allStore;
		this.displayProperties = Collections.singletonMap(displayProperty, displayName);
		this.columnWidth = -1;
		
		initializeUI();
	}
	
	public SimpleGridSelectionPopup(Map<ValueProvider<M, String>, String> displayProperties, int columnWidth, ListStore<M> selectedStore, ListStore<M> allStore){
		super();
		this.store = selectedStore;
		this.allStore = allStore;
		this.displayProperties = displayProperties;
		this.columnWidth = columnWidth;
		
		initializeUI();
	}
	
	private void initializeUI() {
	    this.view = new GridView<M>();
	    disabledStyle = null;
	    getView().setTrackMouseOver(false);
		
		/* create columns */
		List<ColumnConfig<M,?>> configs = new ArrayList<ColumnConfig<M,?>>();   
		
		/* add main column */
		for(ValueProvider<M, String> vp : displayProperties.keySet()){
			ColumnConfig<M,String> cc = new ColumnConfig<M,String>(vp, 300, displayProperties.get(vp));
			if(columnWidth != -1)
				cc.setWidth(columnWidth);
			cc.setMenuDisabled(true);
			configs.add(cc);
		}
		
		/* create column model */
		this.cm = new ColumnModel<M>(configs);
		
		getView().setEmptyText(FormsMessages.INSTANCE.noDataSelected());
		if(! configs.isEmpty())
			getView().setAutoExpandColumn(configs.get(configs.size()-1));
		
		setHeight(200);
		
		/* set selection model - has to be set after column model */
		setSelectionModel(new GridSelectionModel<M>());
		
		/* from Grid constructor */
	    disabledStyle = null;

	    setAllowTextSelection(false);

	    SafeHtmlBuilder builder = new SafeHtmlBuilder();
	    view.getAppearance().render(builder);

	    setElement(XDOM.create(builder.toSafeHtml()));
	    getElement().makePositionable();

	    sinkCellEvents();
	}
	
	public void setPopupSelectionMode(SelectionMode mode){
		this.popupSelectionMode  = mode;
	}
	
	@Override
	protected void onDoubleClick(Event e) {
		super.onDoubleClick(e);
		
		SelectionPopup<M> selectionPanel = new SelectionPopup<M>(allStore, displayProperties);
		
		selectionPanel.setSelectionCallback(new ItemsSelectedCallback<M>() {

			@Override
			public void itemsSelected(List<M> items) {
				store.clear();
				store.addAll(items);
				SimpleGridSelectionPopup.this.itemsSelected(items);
			}
		});

		selectionPanel.setSelectionMode(popupSelectionMode);
		selectionPanel.setSelectedItems(store.getAll());
		if(null != allItemsStoreloader)
			selectionPanel.setLoader(allItemsStoreloader);
		if(! allStoreLoaded){
			selectionPanel.loadData();
			allStoreLoaded = true && ! alwaysReloadData;
		}
		
		selectionPanel.setHeadingText(header);
		selectionPanel.show();
	}

	protected void itemsSelected(List<M> selectedItems) {
	}
	
	public void reloadData(){
		allStoreLoaded = false;
	}
	
	public void alwaysReloadData(){
		alwaysReloadData  = true;
	}
	
	public void setAllItemsStoreLoader(ListLoader<ListLoadConfig, ListLoadResult<M>> loader) {
		this.allItemsStoreloader = loader;
	}

	public void setHeader(String header) {
		this.header = header;
	}
}
