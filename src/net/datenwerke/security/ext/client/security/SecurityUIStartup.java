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
 
 
package net.datenwerke.security.ext.client.security;

import java.util.Collection;
import java.util.HashSet;

import net.datenwerke.gf.client.administration.AdministrationUIService;
import net.datenwerke.gf.client.administration.hooks.AdminModuleProviderHook;
import net.datenwerke.gf.client.login.LoginService;
import net.datenwerke.gxtdto.client.dtomanager.callback.RsAsyncCallback;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.security.client.security.GenericTargetIdentifier;
import net.datenwerke.security.client.security.SecurityUIService;
import net.datenwerke.security.client.security.dto.GenericSecurityTargetContainer;
import net.datenwerke.security.client.security.dto.ReadDto;
import net.datenwerke.security.client.security.hooks.GenericSecurityViewDomainHook;
import net.datenwerke.security.client.security.hooks.GenericTargetProviderHook;
import net.datenwerke.security.ext.client.security.ui.genericview.GenericSecurityViewAdminModule;
import net.datenwerke.security.ext.client.security.ui.genericview.targets.GenericSecurityTargetAdminViewGenericTargetIdentifier;
import net.datenwerke.security.ext.client.security.ui.genericview.targets.GenericSecurityTargetAdminViewSecurityTargetDomainHooker;

import com.google.inject.Inject;

public class SecurityUIStartup {

	@Inject
	public SecurityUIStartup(
		final HookHandlerService hookHandler,
		final WaitOnEventUIService waitOnEventService,
		final SecurityDao securityDao,
		final GenericSecurityViewAdminModule genericSecurityViewAdminModule,
		GenericSecurityTargetAdminViewSecurityTargetDomainHooker securityTargetDomain, 
		
		final SecurityUIService securityService
		){
	
		/* attach security target domains */
		hookHandler.attachHooker(GenericTargetProviderHook.class, new GenericTargetProviderHook(securityTargetDomain.genericSecurityViewDomainHook_getTargetId()));
		hookHandler.attachHooker(GenericSecurityViewDomainHook.class, securityTargetDomain);
		
		/* test if user has rights to see  generic rights admin view view */
		waitOnEventService.callbackOnEvent(AdministrationUIService.REPORTSERVER_EVENT_HAS_ADMIN_RIGHTS, new SynchronousCallbackOnEventTrigger(){
			public void execute(final WaitOnEventTicket ticket) {
				if(securityService.hasRight(GenericSecurityTargetAdminViewGenericTargetIdentifier.class, ReadDto.class))
					hookHandler.attachHooker(AdminModuleProviderHook.class, new AdminModuleProviderHook(genericSecurityViewAdminModule),  HookHandlerService.PRIORITY_HIGH  + 6);

				waitOnEventService.signalProcessingDone(ticket);
			}
		});
		
		/* load generic rights after login */
		waitOnEventService.callbackOnEvent(LoginService.REPORTSERVER_EVENT_AFTER_INITIAL_LOGIN, new SynchronousCallbackOnEventTrigger() {
			@Override
			public void execute(final WaitOnEventTicket ticket) {
				Collection<GenericTargetIdentifier> targetIdentifiers = new HashSet<GenericTargetIdentifier>();
				for(GenericTargetProviderHook provider : hookHandler.getHookers(GenericTargetProviderHook.class))
					targetIdentifiers.add(provider.getObject());
				
				securityDao.loadGenericRights(targetIdentifiers, new RsAsyncCallback<GenericSecurityTargetContainer>(){
					@Override
					public void onSuccess(GenericSecurityTargetContainer result) {
						securityService.setGenericSecurityContainer(result);
						waitOnEventService.triggerEvent(SecurityUIService.REPORTSERVER_EVENT_GENERIC_RIGHTS_LOADED);
						
						waitOnEventService.signalProcessingDone(ticket);
					}
				});
			}
		});
	}
}
