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
 
 
package net.datenwerke.rs.terminal.client.terminal;

import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.terminal.client.terminal.hookers.ClearScreenHooker;
import net.datenwerke.rs.terminal.client.terminal.hookers.JsCommandProcessor;
import net.datenwerke.rs.terminal.client.terminal.hookers.MessageCommandProcessor;
import net.datenwerke.rs.terminal.client.terminal.hookers.OverlayCommandProcessor;
import net.datenwerke.rs.terminal.client.terminal.hooks.ClientCommandHook;
import net.datenwerke.rs.terminal.client.terminal.hooks.CommandResultProcessorHook;
import net.datenwerke.rs.terminal.client.terminal.security.TerminalGenericTargetIdentifier;
import net.datenwerke.rs.terminal.client.terminal.security.TerminalSecurityTargetDomainHooker;
import net.datenwerke.security.client.security.SecurityUIService;
import net.datenwerke.security.client.security.dto.ExecuteDto;
import net.datenwerke.security.client.security.hooks.GenericSecurityViewDomainHook;
import net.datenwerke.security.client.security.hooks.GenericTargetProviderHook;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class TerminalUIStartup {

	@Inject
	public TerminalUIStartup(
		final WaitOnEventUIService waitOnEventService,
		
		final TerminalUIService terminalService,
		final SecurityUIService securityService,
		
		final HookHandlerService hookHandler,
		final Provider<ClearScreenHooker> clearScreenCmd,
		
		final TerminalSecurityTargetDomainHooker securityTargetDomain,
		
		Provider<MessageCommandProcessor> messageCommandProcessor,
		Provider<OverlayCommandProcessor> overlayCommandProcessor,
		Provider<JsCommandProcessor> jsCommandProcessor
		){
		
		hookHandler.attachHooker(CommandResultProcessorHook.class, messageCommandProcessor);
		hookHandler.attachHooker(CommandResultProcessorHook.class, overlayCommandProcessor);
		hookHandler.attachHooker(CommandResultProcessorHook.class, jsCommandProcessor);
		
		/* security */
		hookHandler.attachHooker(GenericTargetProviderHook.class, new GenericTargetProviderHook(securityTargetDomain.genericSecurityViewDomainHook_getTargetId()));
		hookHandler.attachHooker(GenericSecurityViewDomainHook.class, securityTargetDomain);

		/* attach terminal */
		waitOnEventService.callbackOnEvent(SecurityUIService.REPORTSERVER_EVENT_GENERIC_RIGHTS_LOADED, new SynchronousCallbackOnEventTrigger() {
			
			public void execute(final WaitOnEventTicket ticket) {
				/* init terminal */
				if(securityService.hasRight(TerminalGenericTargetIdentifier.class, ExecuteDto.class)){
					hookHandler.attachHooker(ClientCommandHook.class, clearScreenCmd);

					terminalService.initTerminal();
				}
								
				waitOnEventService.signalProcessingDone(ticket);
			}
		});
	}
}
