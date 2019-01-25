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
 
 
package net.datenwerke.rs.base.service.parameters;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.base.service.parameters.eventhandlers.HandleDatasourceRemoveEventHandler;
import net.datenwerke.rs.base.service.parameters.hookers.BaseParameterProviderHooker;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.reportmanager.hooks.ParameterProviderHook;
import net.datenwerke.rs.utils.eventbus.EventBus;
import net.datenwerke.security.service.eventlogger.jpa.RemoveEntityEvent;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class BaseParametersStartup {

	@Inject
	public BaseParametersStartup(
		HookHandlerService hookHandler,
		EventBus eventBus,
		
		Provider<BaseParameterProviderHooker> parameterProvider,
		
		HandleDatasourceRemoveEventHandler handleDatasourceRemoveEventHandler
		
		){
		
		hookHandler.attachHooker(ParameterProviderHook.class, parameterProvider);
		
		eventBus.attachObjectEventHandler(RemoveEntityEvent.class, DatasourceDefinition.class, handleDatasourceRemoveEventHandler);
		
	}
}
