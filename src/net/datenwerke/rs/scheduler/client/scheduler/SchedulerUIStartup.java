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
 
 
package net.datenwerke.rs.scheduler.client.scheduler;

import net.datenwerke.gf.client.administration.AdministrationUIService;
import net.datenwerke.gf.client.administration.hooks.AdminModuleProviderHook;
import net.datenwerke.gf.client.homepage.hooks.ClientMainModuleProviderHook;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.reportexecutor.hooks.ReportExecutorViewToolbarHook;
import net.datenwerke.rs.scheduler.client.scheduler.hookers.EmailExportSnippetProvider;
import net.datenwerke.rs.scheduler.client.scheduler.hookers.ReportViewScheduleButtonHooker;
import net.datenwerke.rs.scheduler.client.scheduler.hooks.ScheduleExportSnippetProviderHook;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.SchedulerAdminModule;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.SchedulerClientModule;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.filters.ExecutionStatusEntriesFilter;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.filters.FailedLastTimeEntriesFilter;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.filters.FilterUserScheduledEntriesFilter;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.filters.MyOrToMeScheduledEntriesFilter;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hookers.EditScheduleEntryHooker;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hookers.LoadDetailsForEntryHooker;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hookers.RemoveScheduleEntryHooker;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hookers.ScheduleNowHooker;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hookers.actionlog.GenericActionLogEntryDetailHooker;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hooks.ActionLogEntryDetailHook;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hooks.ScheduledReportListDetailToolbarHook;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hooks.ScheduledReportListFilter;
import net.datenwerke.rs.scheduler.client.scheduler.schedulereportlist.hooks.ScheduledReportToolbarListFilter;
import net.datenwerke.rs.scheduler.client.scheduler.security.SchedulingAdminViewGenericTargetIdentifier;
import net.datenwerke.rs.scheduler.client.scheduler.security.SchedulingAdminViewSecurityTargetDomainHooker;
import net.datenwerke.rs.scheduler.client.scheduler.security.SchedulingBasicGenericTargetIdentifier;
import net.datenwerke.rs.scheduler.client.scheduler.security.SchedulingBasicSecurityTargetDomainHooker;
import net.datenwerke.security.client.security.SecurityUIService;
import net.datenwerke.security.client.security.dto.ReadDto;
import net.datenwerke.security.client.security.hooks.GenericSecurityViewDomainHook;
import net.datenwerke.security.client.security.hooks.GenericTargetProviderHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class SchedulerUIStartup {

	@Inject
	public SchedulerUIStartup(
		final WaitOnEventUIService waitOnEventService,
			
		final SchedulerDao schedulerDao,
		
		final HookHandlerService hookHandler,
		final Provider<SchedulerAdminModule> adminModuleProvider,
		ReportViewScheduleButtonHooker scheduleButtonHooker,
		final Provider<SchedulerClientModule> schedulerModuleProvider,
		SchedulingAdminViewSecurityTargetDomainHooker adminSecurityTargetDomain,
		SchedulingBasicSecurityTargetDomainHooker basicSecurityTargetDomain,
		
		Provider<EmailExportSnippetProvider> emailExportSnippet,
		
		Provider<RemoveScheduleEntryHooker> removeScheduleEntryHooker,
		Provider<LoadDetailsForEntryHooker> loadDetailsScheduleEntryHooker,
		Provider<EditScheduleEntryHooker> editScheduleEntryHooker,
		Provider<ScheduleNowHooker> scheduleNowHooker,
		
		Provider<MyOrToMeScheduledEntriesFilter> myOrToMeFilter,
		Provider<FilterUserScheduledEntriesFilter> userFilter,
		Provider<ExecutionStatusEntriesFilter> statusFilter,
		Provider<FailedLastTimeEntriesFilter> failedLastFilter,
		
		Provider<GenericActionLogEntryDetailHooker> genericActionLogEntryHooker,
		
		final SecurityUIService securityService
		){
		
		hookHandler.attachHooker(ScheduledReportToolbarListFilter.class, myOrToMeFilter);
		hookHandler.attachHooker(ScheduledReportListFilter.class, userFilter);
		hookHandler.attachHooker(ScheduledReportListFilter.class, statusFilter);
		hookHandler.attachHooker(ScheduledReportListFilter.class, failedLastFilter);
		
		hookHandler.attachHooker(ScheduleExportSnippetProviderHook.class, emailExportSnippet);
		
		/* schedule ui */
		hookHandler.attachHooker(ScheduledReportListDetailToolbarHook.class, removeScheduleEntryHooker);
		hookHandler.attachHooker(ScheduledReportListDetailToolbarHook.class, loadDetailsScheduleEntryHooker, HookHandlerService.PRIORITY_LOW);
		hookHandler.attachHooker(ScheduledReportListDetailToolbarHook.class, editScheduleEntryHooker);
		hookHandler.attachHooker(ScheduledReportListDetailToolbarHook.class, scheduleNowHooker);
		
		/* logs */
		hookHandler.attachHooker(ActionLogEntryDetailHook.class, genericActionLogEntryHooker, HookHandlerService.PRIORITY_LOWER);
		
		/* attach hookers */
		/* report view toolbar */
		hookHandler.attachHooker(ReportExecutorViewToolbarHook.class, scheduleButtonHooker, HookHandlerService.PRIORITY_LOW);
		
		/* attach security target domains */
		hookHandler.attachHooker(GenericTargetProviderHook.class, new GenericTargetProviderHook(adminSecurityTargetDomain.genericSecurityViewDomainHook_getTargetId()));
		hookHandler.attachHooker(GenericSecurityViewDomainHook.class, adminSecurityTargetDomain);
		hookHandler.attachHooker(GenericTargetProviderHook.class, new GenericTargetProviderHook(basicSecurityTargetDomain.genericSecurityViewDomainHook_getTargetId()));
		hookHandler.attachHooker(GenericSecurityViewDomainHook.class, basicSecurityTargetDomain);
		
		/* test if user has rights to see scheduler admin view */
		waitOnEventService.callbackOnEvent(AdministrationUIService.REPORTSERVER_EVENT_HAS_ADMIN_RIGHTS, new SynchronousCallbackOnEventTrigger(){
			public void execute(final WaitOnEventTicket ticket) {
				if(securityService.hasRight(SchedulingAdminViewGenericTargetIdentifier.class, ReadDto.class))
					hookHandler.attachHooker(AdminModuleProviderHook.class, new AdminModuleProviderHook(adminModuleProvider),  HookHandlerService.PRIORITY_LOW);

				waitOnEventService.signalProcessingDone(ticket);
			}
		});
		
		
		/* request callback after login and check for rights */
		waitOnEventService.callbackOnEvent(SecurityUIService.REPORTSERVER_EVENT_GENERIC_RIGHTS_LOADED, new SynchronousCallbackOnEventTrigger() {
			public void execute(final WaitOnEventTicket ticket) {
				if(securityService.hasRight(SchedulingBasicGenericTargetIdentifier.class, ReadDto.class)){
					/* attach uimodule */
					hookHandler.attachHooker(ClientMainModuleProviderHook.class, new ClientMainModuleProviderHook(schedulerModuleProvider), HookHandlerService.PRIORITY_LOW - 10);
				} 
				waitOnEventService.signalProcessingDone(ticket);
			}
		});
	}
}
