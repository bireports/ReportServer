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
 
 
package net.datenwerke.rs.dsbundle.client.dsbundle.hooker;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;
import com.google.inject.Provider;

import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.rs.core.client.datasourcemanager.config.DatasourceDefinitionConfigConfigurator;
import net.datenwerke.rs.core.client.datasourcemanager.dto.AbstractDatasourceManagerNodeDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionDto;
import net.datenwerke.rs.core.client.datasourcemanager.hooks.DatasourceDefinitionConfigProviderHook;
import net.datenwerke.rs.dsbundle.client.dsbundle.config.DatabaseBundleConfigConfigurator;
import net.datenwerke.rs.dsbundle.client.dsbundle.dto.DatabaseBundleDto;
import net.datenwerke.rs.dsbundle.client.dsbundle.locale.DatasourceBundleMessages;
import net.datenwerke.rs.dsbundle.client.dsbundle.ui.DatabaseBundleForm;
import net.datenwerke.rs.enterprise.client.EnterpriseUiService;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

public class DatabaseBundleConfigProviderHooker implements
		DatasourceDefinitionConfigProviderHook {

	private final Provider<DatabaseBundleConfigConfigurator> configurator;
	private final Provider<DatabaseBundleForm> formProvider;
	private final EnterpriseUiService enterpriseService;
	
	@Inject
	public DatabaseBundleConfigProviderHooker(
		Provider<DatabaseBundleConfigConfigurator> configurator,
		Provider<DatabaseBundleForm> formProvider,
		EnterpriseUiService enterpriseService
		){
		
		/* store objects */
		this.configurator = configurator;
		this.formProvider = formProvider;
		this.enterpriseService = enterpriseService;
	}
	
	@Override
	public Map<? extends Class<? extends DatasourceDefinitionDto>, ? extends Provider<? extends DatasourceDefinitionConfigConfigurator>> getConfigs() {
		Map<Class<? extends DatasourceDefinitionDto>, Provider<? extends DatasourceDefinitionConfigConfigurator>> map =
			new HashMap<Class<? extends DatasourceDefinitionDto>, Provider<? extends DatasourceDefinitionConfigConfigurator>>();
				
		map.put(DatabaseBundleDto.class, configurator);
		
		return map;
	}

	@Override
	public boolean consumes(DatasourceDefinitionDto dsd) {
		return dsd instanceof DatabaseBundleDto;
	}

	@Override
	public Collection<? extends MainPanelView> getAdminViews(
			DatasourceDefinitionDto dsd) {
		return Collections.singleton(formProvider.get());
	}

	@Override
	public Class<? extends AbstractDatasourceManagerNodeDto> getDatasourceClass() {
		return DatabaseBundleDto.class;
	}

	@Override
	public String getDatasourceName() {
		return DatasourceBundleMessages.INSTANCE.databaseBundleTypeName();
	}

	@Override
	public AbstractDatasourceManagerNodeDto instantiateDatasource() {
		return new DatabaseBundleDto();
	}

	@Override
	public ImageResource getDatasourceIcon() {
		return BaseIcon.RANDOM.toImageResource();
	}

	@Override
	public boolean isAvailable() {
		return enterpriseService.isEnterprise();
	}
}
