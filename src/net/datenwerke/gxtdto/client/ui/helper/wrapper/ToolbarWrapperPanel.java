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
 
 
package net.datenwerke.gxtdto.client.ui.helper.wrapper;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwSplitButton;
import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenu;
import net.datenwerke.gxtdto.client.baseex.widget.menu.DwMenuItem;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.DwToolBar;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.container.NorthSouthContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.menu.Item;
import com.sencha.gxt.widget.core.client.menu.Menu;
import com.sencha.gxt.widget.core.client.menu.MenuItem;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class ToolbarWrapperPanel extends DwContentPanel {

	private final DwToolBar toolbar;
	
	public ToolbarWrapperPanel(Widget main){
		setHeaderVisible(false);
		setBodyBorder(false);
		setBorders(false);
		
		NorthSouthContainer cont = new NorthSouthContainer();
		add(cont);
		
		toolbar = new DwToolBar();
		cont.setNorthWidget(toolbar);
		
		cont.add(main);
	}
	
	public ToolBar getToolbar(){
		return toolbar;
	}
	
	public void addRemoveButtons(){
		addRemoveButtons(BaseMessages.INSTANCE.remove(), BaseMessages.INSTANCE.removeAll());
	}
	
	public void addRemoveButtons(String removeItem, String removeAll){
		/* add remove buttons */
		DwSplitButton removeButton = new DwSplitButton(removeItem);
		removeButton.setIcon(BaseIcon.DELETE);
		removeButton.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				removeButtonClicked(event);
			}
		});

		toolbar.add(removeButton);
		MenuItem removeAllButton = new DwMenuItem(removeAll, BaseIcon.DELETE);
		removeAllButton.addSelectionHandler(new SelectionHandler<Item>() {
			
			@Override
			public void onSelection(SelectionEvent<Item> event) {
				removeAllButtonClicked(event);
			}
		});
		Menu remMenu = new DwMenu();
		remMenu.add(removeAllButton);
		removeButton.setMenu(remMenu);
	}
	
	protected void removeAllButtonClicked(SelectionEvent<Item> event) {
		
	}

	protected void removeButtonClicked(SelectEvent event) {
		
	}

	public void addSubmitButton() {
		addSubmitButton(BaseMessages.INSTANCE.submit());
	}
	
	public void addSubmitButton(String submit) {
		DwTextButton submitBtn = new DwTextButton(submit);
		submitBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				submitButtonClicked(event);
			}
		});
		addButton(submitBtn);
	}

	protected void submitButtonClicked(SelectEvent event) {
		
	}
}
