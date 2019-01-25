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
 
 
package net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hookers;

import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.scheduler.client.scheduler.SchedulerDao;
import net.datenwerke.rs.scheduler.client.scheduler.locale.SchedulerMessages;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.ScheduledReportListPanel;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobInformation;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobListInformation;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hooks.ScheduledReportListDetailToolbarHook;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.inject.Inject;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;

import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class LoadDetailsForEntryHooker implements ScheduledReportListDetailToolbarHook {

	private final SchedulerDao schedulerDao;
	private final ToolbarService toolbarService;
	
	@Inject
	public LoadDetailsForEntryHooker(
		SchedulerDao schedulerDao,
		ToolbarService toolbarService
		) {
		
		/* store objects */
		this.schedulerDao = schedulerDao;
		this.toolbarService = toolbarService;
	}

	@Override
	public void statusBarToolbarHook_addLeft(ToolBar toolbar,
			final ReportScheduleJobListInformation info,
			final ReportScheduleJobInformation detailInfo,
			final ScheduledReportListPanel reportListPanel) {
		
		DwTextButton removeBtn = toolbarService.createSmallButtonLeft(SchedulerMessages.INSTANCE.loadDetailsLabel(), BaseIcon.LIST);
		removeBtn.addSelectHandler(new SelectHandler() {
			
			@Override
			public void onSelect(SelectEvent event) {
				reportListPanel.mask(BaseMessages.INSTANCE.loadingMsg());
				schedulerDao.loadDetailsFor(info, new RsAsyncCallback<ReportScheduleJobInformation>(){
					public void onSuccess(ReportScheduleJobInformation result) {
						reportListPanel.setDataInDetailStore(result);
						reportListPanel.unmask();
					}
					@Override
					public void onFailure(Throwable caught) {
						reportListPanel.unmask();
					}
				});
			}
		});
		toolbar.add(removeBtn);
		
	}

	@Override
	public void statusBarToolbarHook_addRight(ToolBar toolbar,
			ReportScheduleJobListInformation info,
			ReportScheduleJobInformation detailInfo,
			ScheduledReportListPanel reportListPanel) {

	}

}
