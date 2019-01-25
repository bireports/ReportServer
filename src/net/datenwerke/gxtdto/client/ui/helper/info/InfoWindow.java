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
 
 
package net.datenwerke.gxtdto.client.ui.helper.info;

import java.util.Map;
import java.util.Map.Entry;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.DwWindow;
import net.datenwerke.gxtdto.client.baseex.widget.form.DwFormPanel;
import net.datenwerke.gxtdto.client.baseex.widget.layout.DwFlowContainer;
import net.datenwerke.gxtdto.client.locale.BaseMessages;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer;
import com.sencha.gxt.widget.core.client.container.BorderLayoutContainer.BorderLayoutData;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FormPanel;

public class InfoWindow extends DwWindow {

	private DwContentPanel pluggableInfoPanel;
	private AccordionLayoutContainer accordionContainer;
	private DwFlowContainer infoPanelContainer;
	
	@Inject
	public InfoWindow(){
		initializeUI();
	}

	protected void initializeUI() {
		setClosable(true);
		setAnimCollapse(true);
		setWidth(500);
		setHeight(530);
		
		BorderLayoutContainer borderContainer = new BorderLayoutContainer();
		add(borderContainer);
		
		infoPanelContainer = new DwFlowContainer();
		infoPanelContainer.getElement().getStyle().setBackgroundColor("#EEEEEE");
		
		pluggableInfoPanel = DwContentPanel.newInlineInstance();
		accordionContainer = new AccordionLayoutContainer();
		pluggableInfoPanel.setWidget(accordionContainer);
		
		/* add to main container */
		BorderLayoutData northData = new BorderLayoutData(50);
		borderContainer.setNorthWidget(infoPanelContainer, northData);
		
		borderContainer.setCenterWidget(pluggableInfoPanel);
	}

	public void setInfoData(Widget widget) {
		infoPanelContainer.add(widget);
	}
	
	public void addInfoPanel(Widget panel){
		accordionContainer.add(panel);
		
		setHeight(height + 25);
	}

	public void addSimpelDataInfoPanel(String header, Map<String, String> data) {
		DwContentPanel wrapper = new DwContentPanel();
		wrapper.setHeadingText(header);
		wrapper.expand();
		
		DwFormPanel panel = new DwFormPanel();
		wrapper.add(panel);

		DwFlowContainer fieldWrapper = new DwFlowContainer();
		fieldWrapper.setScrollMode(ScrollMode.AUTOY);
		panel.add(fieldWrapper, new MarginData(2));
		
		for(Entry<String, String> entry : data.entrySet()){
			FieldLabel field = new FieldLabel(new Label(entry.getValue()), entry.getKey());
			//field.setWidth(250);
			fieldWrapper.add(field);
		}
		
		accordionContainer.add(wrapper);
		
		setHeight(height + 25);
	}
	
	public FormPanel addDelayedSimpelDataInfoPanel(String header) {
		DwContentPanel wrapper = new DwContentPanel();
		wrapper.setHeadingText(header);
		
		FormPanel panel = new FormPanel();
		wrapper.add(panel);

		DwFlowContainer fieldWrapper = new DwFlowContainer();
		fieldWrapper.setScrollMode(ScrollMode.AUTOY);
		panel.add(fieldWrapper, new MarginData(2));
		
		accordionContainer.add(wrapper);
		
		setHeight(height + 25);
		
		return panel;
	}
	
	public DwContentPanel addDelayedSimpelInfoPanel(String header) {
		DwContentPanel panel = new DwContentPanel();
		panel.setAnimCollapse(false);
		panel.add(new Label(BaseMessages.INSTANCE.loadingMsg()));
		
		panel.setHeadingText(header);
		
		accordionContainer.add(panel);
		
		setHeight(height + 25);
		
		return panel;
	}
}
