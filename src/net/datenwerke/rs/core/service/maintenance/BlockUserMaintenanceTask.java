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
 
 
package net.datenwerke.rs.core.service.maintenance;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import net.datenwerke.gf.service.maintenance.hooks.MaintenanceTask;
import net.datenwerke.rs.core.service.reportserver.ReportServerModule;
import net.datenwerke.rs.core.service.reportserver.ReportServerService;
import net.datenwerke.security.service.usermanager.UserManagerService;
import net.datenwerke.security.service.usermanager.UserPropertiesServiceImpl;
import net.datenwerke.security.service.usermanager.entities.User;
import net.datenwerke.security.service.usermanager.entities.UserProperty__;
import net.datenwerke.security.service.usermanager.entities.User__;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BlockUserMaintenanceTask implements MaintenanceTask {

	private final Provider<EntityManager> emp;
	private final UserManagerService userService;
	private final UserPropertiesServiceImpl propertiesService;
	private final ReportServerService rsService;
	
	@Inject
	public BlockUserMaintenanceTask(
		Provider<EntityManager> emp, 
		ReportServerService rsService,
		UserManagerService userService,
		UserPropertiesServiceImpl propertiesService) {
		this.emp = emp;
		this.rsService = rsService;
		this.userService = userService;
		this.propertiesService = propertiesService;
	}


	@Override
	public void performMaintenance() {
		EntityManager entityManager = emp.get();
		
		Long ts = new Date().getTime();
		String castedProperty = "cast(property.value as string)"; 
		String qs = "from " + User.class.getSimpleName() + " user inner join user." + User__.properties + " property with property." + UserProperty__.key + "='" + ReportServerModule.USER_PROPERTY_ACCOUNT_EXPIRATION_DATE + "' AND cast(" + castedProperty + " as long) < " + ts; 
		Query q = entityManager.createQuery(qs);
		
		List resultList = q.getResultList();
		for(Object res : resultList){
			if(res instanceof Object[] && ((Object[])res).length > 0 && ((Object[])res)[0] instanceof User){
				User u = (User) ((Object[])res)[0];
				
				propertiesService.setPropertyValue(u, ReportServerModule.USER_PROPERTY_ACCOUNT_INHIBITED, "true");

				userService.merge(u);
			}
		}
	}

	
}
