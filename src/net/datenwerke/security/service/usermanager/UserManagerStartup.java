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
 
 
package net.datenwerke.security.service.usermanager;

import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;
import net.datenwerke.security.service.security.SecurityService;
import net.datenwerke.security.service.usermanager.entities.Group;
import net.datenwerke.security.service.usermanager.entities.OrganisationalUnit;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.security.service.usermanager.eventhandler.UserRemoveEventHandler;

import com.google.inject.Inject;

public class UserManagerStartup {

	@Inject
	public UserManagerStartup(
		EventBus eventBus,
		SecurityService securityService,
		
		UserRemoveEventHandler userRemoveEventHandler
		){
		
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, User.class, userRemoveEventHandler);
		
		registerSecurityTargets(securityService);
	}
	
	
	private void registerSecurityTargets(SecurityService securityService) {
		securityService.registerSecurityTarget(
			Group.class,
			User.class,
			OrganisationalUnit.class
		);
	}

}
