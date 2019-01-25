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
 
 
package net.datenwerke.rs.uservariables.client.uservariables;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import net.datenwerke.gxtdto.client.stores.LoadableListStore;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.uservariables.client.uservariables.dto.UserVariableDefinitionDto;
import net.datenwerke.rs.uservariables.client.uservariables.dto.UserVariableInstanceDto;
import net.datenwerke.rs.uservariables.client.uservariables.dto.pa.UserVariableDefinitionDtoPA;
import net.datenwerke.rs.uservariables.client.uservariables.hooks.UserVariableProviderHook;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.ListLoadConfig;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.ListLoader;

/**
 * 
 *
 */
public class UserVariablesUIServiceImpl implements UserVariablesUIService {

	private static UserVariableDefinitionDtoPA uvDefPa = GWT.create(UserVariableDefinitionDtoPA.class);
	
	final private HookHandlerService hookHandler;
	final private UserVariableDao userVaroaneDao;
	
	@Inject
	public UserVariablesUIServiceImpl(
		HookHandlerService hookHandler,
		UserVariableDao rpcService
		){
		
		this.hookHandler = hookHandler;
		this.userVaroaneDao = rpcService;
	}
	
	public Collection<UserVariableConfigurator> getAllVariableConfigurators(){
		Set<UserVariableConfigurator> configurators = new HashSet<UserVariableConfigurator>();
		
		Collection<UserVariableProviderHook> hookers = hookHandler.getHookers(UserVariableProviderHook.class);
		for(UserVariableProviderHook hooker : hookers)
			configurators.addAll(hooker.userVariableProviderHook_getConfigurators());
		
		return configurators;
	}
	
	
	public UserVariableConfigurator getConfigurator(UserVariableDefinitionDto uvd) {
		for(UserVariableConfigurator configurator : getAllVariableConfigurators())
			if(configurator.createDTOInstance().getClass().equals(uvd.getClass()))
				return configurator;

		return null;
	}

	public UserVariableConfigurator getConfigurator(UserVariableInstanceDto instance) {
		return getConfigurator(instance.getDefinition());
	}
	
	public ListLoader<ListLoadConfig, ListLoadResult<UserVariableDefinitionDto>> getDefinedVariableDefinitionsLoader(){
		/* create store */
		RpcProxy<ListLoadConfig, ListLoadResult<UserVariableDefinitionDto>> proxy = new RpcProxy<ListLoadConfig, ListLoadResult<UserVariableDefinitionDto>>() {

			@Override
			public void load(
					ListLoadConfig loadConfig,
					AsyncCallback<ListLoadResult<UserVariableDefinitionDto>> callback) {
				userVaroaneDao.getDefinedUserVariableDefinitions(callback);
			}
		};
	
		return new ListLoader<ListLoadConfig, ListLoadResult<UserVariableDefinitionDto>>(proxy);
	}

	@Override
	public LoadableListStore<ListLoadConfig, UserVariableDefinitionDto, ListLoadResult<UserVariableDefinitionDto>> getDefinedVariableDefinitionsStore() {
		return new LoadableListStore<ListLoadConfig, UserVariableDefinitionDto, ListLoadResult<UserVariableDefinitionDto>>(uvDefPa.dtoId(), getDefinedVariableDefinitionsLoader());
	}
}
