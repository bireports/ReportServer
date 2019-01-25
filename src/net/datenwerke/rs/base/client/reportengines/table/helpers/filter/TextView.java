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
 
 
package net.datenwerke.rs.base.client.reportengines.table.helpers.filter;

import java.util.List;

import net.datenwerke.gxtdto.client.baseex.widget.layout.DwNorthSouthContainer;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.FilterService;
import net.datenwerke.rs.base.client.reportengines.table.columnfilter.locale.FilterMessages;
import net.datenwerke.rs.base.client.reportengines.table.dto.ColumnDto;

import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.dnd.core.client.DndDropEvent;
import com.sencha.gxt.dnd.core.client.DropTarget;
import com.sencha.gxt.widget.core.client.TabPanel;
import net.datenwerke.gxtdto.client.baseex.widget.mb.DwAlertMessageBox;
import com.sencha.gxt.widget.core.client.form.TextArea;

/**
 * 
 *
 * @param <M>
 */
public abstract class TextView<M> extends DwNorthSouthContainer {

	@Inject
	protected static FilterService filterService;
	
	final protected ListStore<M> store;
	final protected SelectionPanel<M> selectionPanel;
	final protected ColumnDto column;

	protected TextArea textArea;
	
	public TextView(ListStore<M> store, SelectionPanel<M> selectionPanel, ColumnDto column, final TabPanel tPanel) {
		this.store = store;
		this.selectionPanel = selectionPanel;
		this.column = column;
		
		this.textArea = new TextArea();

		setBorders(false);
		
		setWidget(textArea);
		
		new DropTarget(textArea){
			@Override
			protected void onDragDrop(DndDropEvent e) {
			    super.onDragDrop(e);
			    Object data = e.getData();
			    List<Object> models = prepareDropData(data, true);
			    
			    handleDroppedData(models, e);
			  }
		};
		
		tPanel.addBeforeSelectionHandler(new BeforeSelectionHandler<Widget>() {
			@Override
			public void onBeforeSelection(BeforeSelectionEvent<Widget> event) {
				if(tPanel.getContainer().getActiveWidget() == TextView.this){
					try{
						List<M> newDtos = tryParseText();
						TextView.this.store.clear();
						TextView.this.store.addAll(newDtos);
					}catch (RuntimeException e) {
						new DwAlertMessageBox(FilterMessages.INSTANCE.textViewInvalidMessage(), e.getMessage()).show();
						event.cancel();
					}
				}
			}
		});
	}
	
	abstract protected void handleDroppedData(List<Object> models, DndDropEvent e);

	abstract protected List<M> tryParseText() throws RuntimeException;
	
}
