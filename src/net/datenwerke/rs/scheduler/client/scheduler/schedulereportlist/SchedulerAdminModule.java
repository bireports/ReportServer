/*
 *  ReportServer
 *  Copyright (c) 2016 datenwerke Jan Albrecht
 *  http://reportserver.datenwerke.net
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

import net.datenwerke.gf.client.administration.interfaces.AdminModule;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.scheduler.client.scheduler.locale.SchedulerMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SchedulerAdminModule implements AdminModule{

	public static final String ADMIN_FILTER_PANEL = "ADMIN_FILTER_PANEL";
	
	private final ScheduledReportListPanelFactory schedulerAdminPanelFactory;

	private ScheduledReportListPanel schedulerAdminPanel;

	@Inject
	public SchedulerAdminModule(
		ScheduledReportListPanelFactory schedulerAdminPanelFactory
		) {
		
		/* store objects */
		this.schedulerAdminPanelFactory = schedulerAdminPanelFactory;
	}
	
	@Override
	public Widget getMainWidget() {
		if(null == this.schedulerAdminPanel){
			schedulerAdminPanel = schedulerAdminPanelFactory.create(ADMIN_FILTER_PANEL, true);
			schedulerAdminPanel.load();
		}
		return schedulerAdminPanel;
	}

	@Override
	public ImageResource getNavigationIcon() {
		return BaseIcon.CLOCK_O.toImageResource();
	}

	@Override
	public String getNavigationText() {
		return SchedulerMessages.INSTANCE.scheduler();
	}

	@Override
	public void notifyOfSelection() {
		if(null != schedulerAdminPanel)
			schedulerAdminPanel.reload();
	}
}
