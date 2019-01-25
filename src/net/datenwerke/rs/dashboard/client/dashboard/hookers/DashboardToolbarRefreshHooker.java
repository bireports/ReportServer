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
 
 
package net.datenwerke.rs.dashboard.client.dashboard.hookers;

import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.SeparatorToolItem;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.DwToolBar;
import net.datenwerke.rs.dashboard.client.dashboard.dto.DashboardDto;
import net.datenwerke.rs.dashboard.client.dashboard.hooks.DashboardToolbarHook;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DadgetPanel;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DashboardMainComponent;
import net.datenwerke.rs.dashboard.client.dashboard.ui.DashboardView;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

public class DashboardToolbarRefreshHooker implements DashboardToolbarHook {

	private DashboardView dashboardView;

	@Override
	public void addLeft(DwToolBar toolbar, DashboardMainComponent dashboardMainComponent) {
		
	}

	@Override
	public void addRight(DwToolBar toolbar, final DashboardMainComponent dashboardMainComponent) {
		DwTextButton refreshBtn = new DwTextButton(BaseMessages.INSTANCE.refresh(), BaseIcon.REFRESH);
		
		toolbar.add(refreshBtn);
		toolbar.add(new SeparatorToolItem());
		
		refreshBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				if(null != dashboardView){
					for(DadgetPanel dp : dashboardView.getAllDagetPanels())
						dp.refresh();
				}
			}
		});
	}

	@Override
	public void dashboardDisplayed(DashboardDto dashboard, DashboardView dashboardView) {
		this.dashboardView = dashboardView;
	}
	
	@Override
	public void dashboardChanged(DashboardDto dashboard, DashboardView view) {
		dashboardDisplayed(dashboard, view);
	}

}
