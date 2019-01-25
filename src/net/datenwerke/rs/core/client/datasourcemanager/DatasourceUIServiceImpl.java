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
 
 
package net.datenwerke.rs.core.client.datasourcemanager;

import java.util.HashMap;
import java.util.Map;

import net.datenwerke.gf.client.treedb.UITree;
import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.client.datasourcemanager.config.DatasourceDefinitionConfigConfigurator;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionDto;
import net.datenwerke.rs.core.client.datasourcemanager.helper.forms.DatasourceSelectionField;
import net.datenwerke.rs.core.client.datasourcemanager.helper.forms.DatasourceSelectionFieldFactory;
import net.datenwerke.rs.core.client.datasourcemanager.hooks.DatasourceDefinitionConfigProviderHook;
import net.datenwerke.rs.core.client.datasourcemanager.provider.annotations.DatasourceTreeBasic;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.sencha.gxt.widget.core.client.container.Container;

/**
 * 
 *
 */
public class DatasourceUIServiceImpl implements DatasourceUIService {

	private final HookHandlerService hookHandler;
	private final Provider<UITree> datasourceTreeProvider;
	private final DatasourceDao datasourceDao;

	private Map<Class<? extends DatasourceDefinitionDto>, Provider<? extends DatasourceDefinitionConfigConfigurator>> configuratorLookup;
	private final DatasourceSelectionFieldFactory fieldFactory;

	@Inject
	public DatasourceUIServiceImpl(
		HookHandlerService hookHandler,
		@DatasourceTreeBasic Provider<UITree> datasourceTreeProvider,
		DatasourceDao generalPropertiesDao,
		DatasourceSelectionFieldFactory fieldFactory
		){
	
		/* store objects */
		this.hookHandler = hookHandler;
		this.datasourceTreeProvider = datasourceTreeProvider;
		this.datasourceDao = generalPropertiesDao;
		this.fieldFactory = fieldFactory;
	}
	
	public DatasourceDefinitionConfigConfigurator getConfigurator(
			Class<? extends DatasourceDefinitionDto> configClazz) {
		if(null== configuratorLookup)
			initConfigurator();
		
		Provider<? extends DatasourceDefinitionConfigConfigurator> provider = configuratorLookup.get(configClazz);
		if(null == provider)
			throw new IllegalStateException("I should probably know the provider for " + configClazz.getName()); //$NON-NLS-1$
		return provider.get();
	}
	
	protected void initConfigurator(){
		configuratorLookup =
			new HashMap<Class<? extends DatasourceDefinitionDto>, Provider<? extends DatasourceDefinitionConfigConfigurator>>();
		
		for(DatasourceDefinitionConfigProviderHook configProvider : hookHandler.getHookers(DatasourceDefinitionConfigProviderHook.class))
			configuratorLookup.putAll(configProvider.getConfigs());
	}

	public DatasourceSelectionField getSelectionField(Container container, boolean displayConfigFields, Class<? extends DatasourceDefinitionDto>... types){
		return fieldFactory.create(displayConfigFields, container, datasourceTreeProvider.get(), datasourceDao, types);
	}

	public DatasourceSelectionField getSelectioField(Container container){
		return fieldFactory.create(true, container, datasourceTreeProvider.get(), datasourceDao);
	}

}
