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
 
 
package net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hookers.actionlog;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.core.client.dom.ScrollSupport.ScrollMode;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.TextArea;
import com.sencha.gxt.widget.core.client.grid.Grid;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.gxtdto.client.baseex.widget.layout.DwFlowContainer;
import net.datenwerke.gxtdto.client.ui.helper.grid.keyvalue.KeyValueGridHelper;
import net.datenwerke.gxtdto.client.ui.helper.grid.keyvalue.KeyValueProperty;
import net.datenwerke.gxtdto.client.ui.helper.grid.keyvalue.KeyValuePropertyPA;
import net.datenwerke.rs.scheduler.client.scheduler.locale.SchedulerMessages;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.ScheduledReportListPanel;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hooks.ActionLogEntryDetailHook;
import net.datenwerke.scheduler.client.scheduler.dto.OutcomeDto;
import net.datenwerke.scheduler.client.scheduler.dto.history.ActionEntryDto;

public class GenericActionLogEntryDetailHooker implements ActionLogEntryDetailHook {

	private static KeyValuePropertyPA kvPa = GWT.create(KeyValuePropertyPA.class);

	@Inject
	private KeyValueGridHelper gridHelper;
	
	@Override
	public boolean consumes(ActionEntryDto action) {
		return action instanceof ActionEntryDto;
	}

	@Override
	public Widget getCard(ActionEntryDto action,
			ScheduledReportListPanel scheduledReportListPanel) {
		DwFlowContainer panel = new DwFlowContainer();
		panel.setScrollMode(ScrollMode.AUTOY);
		
		ListStore<KeyValueProperty> store = new ListStore<KeyValueProperty>(kvPa.id());
		
		store.add(new KeyValueProperty(SchedulerMessages.INSTANCE.actionName(), action.getActionName()));
		
		switch(action.getOutcome()){
		case SUCCESS:
			store.add(new KeyValueProperty(SchedulerMessages.INSTANCE.outcomeLabel(), SchedulerMessages.INSTANCE.success() ));
			break;
		case FAILURE:
			store.add(new KeyValueProperty(SchedulerMessages.INSTANCE.outcomeLabel(), SchedulerMessages.INSTANCE.failure() ));
			break;
		case VETO:
			store.add(new KeyValueProperty(SchedulerMessages.INSTANCE.outcomeLabel(), SchedulerMessages.INSTANCE.veto() ));
			break;
		}
		
		Grid<KeyValueProperty> grid = gridHelper.create(store);
		
		DwContentPanel executionActionLogPanel = new DwContentPanel();
		executionActionLogPanel.setLightDarkStyle();
		executionActionLogPanel.setHeadingText(SchedulerMessages.INSTANCE.executionActionLogEntryLabel());
		
		VerticalLayoutContainer executionActionLogWrapper = new VerticalLayoutContainer();
		executionActionLogPanel.add(executionActionLogWrapper);
		
		panel.add(executionActionLogPanel);
		
		executionActionLogWrapper.add(grid, new VerticalLayoutData(1,-1));
		
		if(OutcomeDto.FAILURE == action.getOutcome()){
			TextArea area = new TextArea();
			area.setWidth(400);
			area.setHeight(300);
			area.setValue(action.getErrorDescription());
			
			executionActionLogWrapper.add(scheduledReportListPanel.createLabel(SchedulerMessages.INSTANCE.errorDescriptionLabel()), new VerticalLayoutData(1,-1, new Margins(5,0,0,0)));
			executionActionLogWrapper.add(area, new VerticalLayoutData(1,-1));
		}
		
		return panel;
	}

}
