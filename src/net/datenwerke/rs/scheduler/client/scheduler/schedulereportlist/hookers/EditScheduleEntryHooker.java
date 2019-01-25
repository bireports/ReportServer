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

import java.util.ArrayList;

import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.forms.wizard.WizardDialog;
import net.datenwerke.gxtdto.client.locale.BaseMessages;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.gxtdto.client.servercommunication.callback.NotamCallback;
import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.core.client.reportmanager.dto.reports.ReportDto;
import net.datenwerke.rs.core.client.sendto.SendToClientConfig;
import net.datenwerke.rs.core.client.sendto.SendToDao;
import net.datenwerke.rs.scheduler.client.scheduler.SchedulerDao;
import net.datenwerke.rs.scheduler.client.scheduler.dto.ReportScheduleDefinition;
import net.datenwerke.rs.scheduler.client.scheduler.locale.SchedulerMessages;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereport.ScheduleDialog;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereport.ScheduleDialog.DialogCallback;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.ScheduledReportListPanel;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobInformation;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.dto.ReportScheduleJobListInformation;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hooks.ScheduledReportListDetailToolbarHook;
import net.datenwerke.rs.theme.client.icon.BaseIcon;
import net.datenwerke.security.client.usermanager.dto.UserDto;

import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gxtdto.client.baseex.widget.btn.DwTextButton;

import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

public class EditScheduleEntryHooker implements ScheduledReportListDetailToolbarHook {

	private final LoginService loginService;
	private final SchedulerDao schedulerDao;
	private final ToolbarService toolbarService;
	private final Provider<ScheduleDialog> scheduleDialogProvider;
	private final SendToDao sendToDao;

	@Inject
	public EditScheduleEntryHooker(
		LoginService loginService,
		SchedulerDao schedulerDao,
		ToolbarService toolbarService,
		Provider<ScheduleDialog> multiDialogProvider,
		SendToDao sendToDao
		) {
		
		/* store objects */
		this.loginService = loginService;
		this.schedulerDao = schedulerDao;
		this.toolbarService = toolbarService;
		this.scheduleDialogProvider = multiDialogProvider;
		this.sendToDao = sendToDao;
	}

	@Override
	public void statusBarToolbarHook_addLeft(ToolBar toolbar,
			final ReportScheduleJobListInformation info,
			final ReportScheduleJobInformation detailInfo,
			final ScheduledReportListPanel reportListPanel) {
		
		/* only for selected user */
		UserDto user = loginService.getCurrentUser();
		if(! user.getId().equals(detailInfo.getOwnerId()) && ! user.isSuperUser())
			return;

		
		DwTextButton removeBtn = toolbarService.createSmallButtonLeft(SchedulerMessages.INSTANCE.editScheduledJobLabel(), BaseIcon.CLOCK_EDIT);
		removeBtn.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				reportListPanel.mask(BaseMessages.INSTANCE.loadingMsg());
				schedulerDao.getReportFor(info.getJobId(), new RsAsyncCallback<ReportDto>(){
					public void onSuccess(final ReportDto report) {
						sendToDao.loadClientConfigsFor(report, new RsAsyncCallback<ArrayList<SendToClientConfig>>(){
							@Override
							public void onSuccess(
									ArrayList<SendToClientConfig> sendToConfigs) {
								reportListPanel.unmask();
								
								ScheduleDialog dialog = scheduleDialogProvider.get();
								
								ReportScheduleDefinition definition = detailInfo.getScheduleDefinition();
								dialog.displayEdit(definition, sendToConfigs, report, new DialogCallback() {
									
									@Override
									public void finished(ReportScheduleDefinition configDto, final WizardDialog dialog) {
										dialog.mask(BaseMessages.INSTANCE.storingMsg());
										
										schedulerDao.reschedule(info.getJobId(), configDto, new NotamCallback<Void>(SchedulerMessages.INSTANCE.scheduled()){
											@Override
											public void doOnSuccess(Void result) {
												dialog.hide();
												reportListPanel.reload();
											}
											@Override
											public void doOnFailure(Throwable caught) {
												super.doOnFailure(caught);
												dialog.unmask();
											}
										});										
									}
								});
							}
							
							@Override
							public void onFailure(Throwable caught) {
								super.onFailure(caught);
								reportListPanel.unmask();
							}
						});
					};
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
