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
 
 
package net.datenwerke.rs.saiku.client.datasource;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import net.datenwerke.gf.client.managerhelper.mainpanel.MainPanelView;
import net.datenwerke.gxtdto.client.resources.BaseResources;
import net.datenwerke.rs.core.client.datasourcemanager.config.DatasourceDefinitionConfigConfigurator;
import net.datenwerke.rs.core.client.datasourcemanager.dto.AbstractDatasourceManagerNodeDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionDto;
import net.datenwerke.rs.core.client.datasourcemanager.hooks.DatasourceDefinitionConfigProviderHook;
import net.datenwerke.rs.saiku.client.datasource.dto.MondrianDatasourceDto;
import net.datenwerke.rs.saiku.client.saiku.locale.SaikuMessages;
import net.datenwerke.rs.theme.client.icon.BaseIcon;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Provider;

public class MondrianDatasourceConfigProviderHooker implements DatasourceDefinitionConfigProviderHook {
	
	private final Provider<MondrianDatasourceConfigConfigurator> configurator;
	private final Provider<MondrianDatasourceForm> formProvider;

	
	@Inject
	public MondrianDatasourceConfigProviderHooker(
			Provider<MondrianDatasourceConfigConfigurator> configurator,
			Provider<MondrianDatasourceForm> formProvider) {

		this.configurator = configurator;
		this.formProvider = formProvider;
	}

	@Override
	public boolean consumes(DatasourceDefinitionDto dsd) {
		return dsd instanceof MondrianDatasourceDto;
	}

	@Override
	public Collection<? extends MainPanelView> getAdminViews(DatasourceDefinitionDto dsd) {
		return Collections.singleton(formProvider.get());
	}

	@Override
	public Map<? extends Class<? extends DatasourceDefinitionDto>, ? extends Provider<? extends DatasourceDefinitionConfigConfigurator>> getConfigs() {
		Map<Class<? extends DatasourceDefinitionDto>, Provider<? extends DatasourceDefinitionConfigConfigurator>> map =
				new HashMap<Class<? extends DatasourceDefinitionDto>, Provider<? extends DatasourceDefinitionConfigConfigurator>>();
					
			map.put(MondrianDatasourceDto.class, configurator);
			
			return map;
	}

	@Override
	public Class<? extends AbstractDatasourceManagerNodeDto> getDatasourceClass() {
		return MondrianDatasourceDto.class;
	}

	@Override
	public String getDatasourceName() {
		return SaikuMessages.INSTANCE.databaseDatasourceTypeName();
	}

	@Override
	public AbstractDatasourceManagerNodeDto instantiateDatasource() {
		return new MondrianDatasourceDto();
	}

	@Override
	public ImageResource getDatasourceIcon() {
		return BaseIcon.CUBES.toImageResource();
	}

	@Override
	public boolean isAvailable() {
		return true;
	}
}
