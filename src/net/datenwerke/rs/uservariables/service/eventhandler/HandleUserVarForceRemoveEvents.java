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
 
 
package net.datenwerke.rs.uservariables.service.eventhandler;

import java.util.Collection;

import net.datenwerke.rs.core.service.reportmanager.ReportParameterService;
import net.datenwerke.rs.uservariables.service.parameters.UserVariableParameterDefinition;
import net.datenwerke.rs.uservariables.service.uservariables.UserVariableService;
import net.datenwerke.rs.uservariables.service.uservariables.entities.UserVariableDefinition;
import net.datenwerke.rs.uservariables.service.uservariables.entities.UserVariableInstance;
import net.datenwerke.rs.utils.eventbus.EventHandler;
import net.datenwerke.security.service.eventlogger.jpa.ForceRemoveEntityEvent;

import com.google.inject.Inject;

public class HandleUserVarForceRemoveEvents implements EventHandler<ForceRemoveEntityEvent> {

	private final UserVariableService userVarService;
	private final ReportParameterService reportParameterService;

	@Inject
	public HandleUserVarForceRemoveEvents(UserVariableService userVarService, ReportParameterService reportParameterService) {
		this.userVarService = userVarService;
		this.reportParameterService = reportParameterService;
	}

	@Override
	public void handle(ForceRemoveEntityEvent event) {
		UserVariableDefinition userVar = (UserVariableDefinition) event.getObject();

		Collection<UserVariableInstance> instances = userVarService.getInstancesForDefinition(userVar);
		if(null != instances && instances.size() > 0)
			for(UserVariableInstance instance : instances)
				userVarService.remove(instance);
		
		Collection<UserVariableParameterDefinition> params = userVarService.getParametersFor(userVar);
		if(null != params && params.size() > 0){
			for(UserVariableParameterDefinition param : params){
				param.setUserVariableDefinition(null);
				reportParameterService.merge(param);
			}
		}
	}

}
