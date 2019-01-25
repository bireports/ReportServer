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
 
 
package net.datenwerke.rs.core.client.easteregg;

import javax.inject.Inject;

import net.datenwerke.gf.client.config.ClientConfigModule;
import net.datenwerke.gf.client.config.ClientConfigService;
import net.datenwerke.gxtdto.client.objectinformation.hooks.ObjectPreviewTabProviderHook;
import net.datenwerke.gxtdto.client.waitonevent.SynchronousCallbackOnEventTrigger;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventTicket;
import net.datenwerke.gxtdto.client.waitonevent.WaitOnEventUIService;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.urlview.hooks.UrlViewSpecialViewHandler;

public class EasterEggStartup {

	@Inject
	public EasterEggStartup(
			final HookHandlerService hookHandler,
			
			final RsPacmanView pacman,
			final RsPacmanObjectInfo pacmanObjectView,
			final ClientConfigService clientConfigService,
			
			final WaitOnEventUIService waitOnEventService
			){
			
		waitOnEventService.callbackOnEvent(ClientConfigModule.CLIENT_CONFIG_FILE_LOADED, new SynchronousCallbackOnEventTrigger() {
			@Override
			public void execute(final WaitOnEventTicket ticket) {
				waitOnEventService.signalProcessingDone(ticket);

				try{
					boolean disable = clientConfigService.getBoolean("eastereggs.disable", false);
					if(! disable){
						/* easter egg */
						hookHandler.attachHooker(UrlViewSpecialViewHandler.class, pacman);
				 		hookHandler.attachHooker(
							ObjectPreviewTabProviderHook.class,
							pacmanObjectView,
							HookHandlerService.PRIORITY_LOW
				 			);
					}
				} catch(Exception e){}
			}
		});
		
		

	}
}
