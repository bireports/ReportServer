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
 
 
package net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist;

import net.datenwerke.gf.client.homepage.modules.ClientMainModuleImpl;
import net.datenwerke.rs.scheduler.client.scheduler.locale.SchedulerMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SchedulerClientModule extends ClientMainModuleImpl {
	
	public static final String CLIENT_FILTER_PANEL = "CLIENT_FILTER_PANEL";
	
	private final ScheduledReportListPanelFactory schedulerMyTasksPanelFactory;
	
	private ScheduledReportListPanel schedulerPanel;
	
	@Inject
	public SchedulerClientModule(
		ScheduledReportListPanelFactory scheduledReportListPanelFactory
	) {
		
		/* store objects */
		this.schedulerMyTasksPanelFactory = scheduledReportListPanelFactory;
	}
	
	@Override
	public ImageResource getIcon() {
		return BaseIcon.CLOCK_O.toImageResource();
	}
	
	public Widget getMainWidget() {
		if(null == this.schedulerPanel){
			schedulerPanel = schedulerMyTasksPanelFactory.create(CLIENT_FILTER_PANEL, false);
			schedulerPanel.load();
		}
		return schedulerPanel;
	}

	public String getModuleName() {
		return SchedulerMessages.INSTANCE.scheduler();
	}

	public void selected() {
		if(null != schedulerPanel)
			schedulerPanel.reload();
	}

	@Override
	public boolean isRecyclable() {
		return true;
	}

}
