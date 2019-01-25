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
 
 
package net.datenwerke.security.service.security;

import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;
import net.datenwerke.security.service.security.eventhandler.SecurityIntegrityValidator;
import net.datenwerke.security.service.usermanager.entities.AbstractUserManagerNode;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SecurityStartup {

	@Inject
	public SecurityStartup(
		EventBus eventBus,
		SecurityIntegrityValidator integrityValidator,
		SecurityService securityService
		){
		
		
		/* attach validator */
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, AbstractUserManagerNode.class, integrityValidator);
		
		
		/* register securee */
		securityService.registerSecuree(new SecurityServiceSecuree());
	}
}
