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
 
 
package net.datenwerke.rs.search.client.search.module;

import net.datenwerke.gxtdto.client.baseex.widget.DwContentPanel;
import net.datenwerke.rs.search.client.search.locale.SearchMessages;

import com.sencha.gxt.widget.core.client.Component;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class SearchAreaMainWidget extends DwContentPanel {

	private SearchAreaModule module;

	public SearchAreaMainWidget(){
		setHeadingText(SearchMessages.INSTANCE.searchAreaModule());
		
		ToolButton toolButton = new ToolButton(ToolButton.CLOSE);
		addTool(toolButton);
		toolButton.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				close();
			}
		});
	}
	
	public void addSearchComponent(String search, Component displayComponent) {
		setWidget(displayComponent);
		forceLayoutCommand.execute();
	}

	public void close() {
		hide();
		module.removeModule();
	}

	public void setModule(SearchAreaModule reportExecuteAreaModule) {
		this.module = reportExecuteAreaModule;
	}
}
