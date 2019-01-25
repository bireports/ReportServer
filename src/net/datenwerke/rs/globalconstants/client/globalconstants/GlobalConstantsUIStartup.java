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
 
 
package net.datenwerke.rs.globalconstants.client.globalconstants;

import net.datenwerke.gf.client.administration.AdministrationUIService;
import net.datenwerke.gf.client.administration.hooks.AdminModuleProviderHook;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.globalconstants.client.globalconstants.security.GlobalConstantsGenericTargetIdentifier;
import net.datenwerke.rs.globalconstants.client.globalconstants.security.GlobalConstantsViewSecurityTargetDomainHooker;
import net.datenwerke.security.client.security.SecurityUIService;
import net.datenwerke.security.client.security.dto.ReadDto;
import net.datenwerke.security.client.security.hooks.GenericSecurityViewDomainHook;
import net.datenwerke.security.client.security.hooks.GenericTargetProviderHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * 
 *
 */
public class GlobalConstantsUIStartup {

	
	@Inject
	public GlobalConstantsUIStartup(
		final WaitOnEventUIService waitOnEventService,
		final HookHandlerService hookHandler,
		final GlobalConstantsDao constantsDao,
		
		final Provider<GlobalConstantsAdminModule> adminModuleProvider,
		GlobalConstantsViewSecurityTargetDomainHooker securityTargetDomain,

		final SecurityUIService securityService
		){
		
		/* attach security target domains */
		hookHandler.attachHooker(GenericTargetProviderHook.class, new GenericTargetProviderHook(securityTargetDomain.genericSecurityViewDomainHook_getTargetId()));
		hookHandler.attachHooker(GenericSecurityViewDomainHook.class, securityTargetDomain);

		/* test if user has rights to see properties view */
		waitOnEventService.callbackOnEvent(AdministrationUIService.REPORTSERVER_EVENT_HAS_ADMIN_RIGHTS, new SynchronousCallbackOnEventTrigger(){
			public void execute(final WaitOnEventTicket ticket) {
				if(securityService.hasRight(GlobalConstantsGenericTargetIdentifier.class, ReadDto.class))
					hookHandler.attachHooker(AdminModuleProviderHook.class, new AdminModuleProviderHook(adminModuleProvider),  HookHandlerService.PRIORITY_HIGH + 100);
				
				waitOnEventService.signalProcessingDone(ticket);
			}
		});
	}
}
