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
 
 
package net.datenwerke.rs.core.client.parameters;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Inject;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.parameters.config.ParameterConfigurator;
import net.datenwerke.rs.core.client.parameters.hooks.ParameterProviderHook;
import net.datenwerke.rs.core.client.parameters.propertywidgets.ParameterView;

/**
 * 
 *
 */
public class ParameterUIModule extends AbstractGinModule {


	@Override
	protected void configure() {
		/* bind service */
		bind(ParameterUIService.class).to(ParameterUIServiceImpl.class).in(Singleton.class);
		
		/* bind startup */
		bind(ParameterUIStartup.class).asEagerSingleton();
		
		/* request static injection */
		requestStaticInjection(ParameterView.class);
	}
	
	@SuppressWarnings("unchecked")
	@Inject
	@Provides
	public List<ParameterConfigurator> provideParameterConfigurators(
		HookHandlerService hookHandler
		){
		/* instantiate list */
		List<ParameterConfigurator> configurator = new ArrayList<ParameterConfigurator>();
		
		List<ParameterProviderHook> providers = hookHandler.getHookers(ParameterProviderHook.class);
		
		/* add parameters to list */
		for(ParameterProviderHook provider : providers)
			configurator.addAll(provider.parameterProviderHook_getConfigurators());
		
		/* return list */
		return configurator;
	}

}
