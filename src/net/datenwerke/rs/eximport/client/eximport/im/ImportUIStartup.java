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
 
 
package net.datenwerke.rs.eximport.client.eximport.im;

import net.datenwerke.gf.client.administration.AdministrationUIService;
import net.datenwerke.gf.client.administration.hooks.AdminModuleProviderHook;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.eximport.client.eximport.security.ImportGenericTargetIdentifier;
import net.datenwerke.security.client.security.SecurityUIService;
import net.datenwerke.security.client.security.dto.ReadDto;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class ImportUIStartup {

	@Inject
	public ImportUIStartup(
		final HookHandlerService hookHandler,
		final WaitOnEventUIService waitOnEventService,
		final SecurityUIService securityService,
		final Provider<ImportAdminModule> importAdminModule
	){
		
		/* test if user has rights to see mass mail view */
		waitOnEventService.callbackOnEvent(AdministrationUIService.REPORTSERVER_EVENT_HAS_ADMIN_RIGHTS, new SynchronousCallbackOnEventTrigger(){
			public void execute(final WaitOnEventTicket ticket) {
				if(securityService.hasRight(ImportGenericTargetIdentifier.class, ReadDto.class))
					hookHandler.attachHooker(AdminModuleProviderHook.class, new AdminModuleProviderHook(importAdminModule), HookHandlerService.PRIORITY_HIGH + 80);

				waitOnEventService.signalProcessingDone(ticket);
			}
		});
	}

	
}
