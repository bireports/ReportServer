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
 
 
package net.datenwerke.rs.dsbundle.client.dsbundle.config;

import net.datenwerke.gxtdto.client.utilityservices.toolbar.ToolbarService;
import net.datenwerke.rs.base.client.datasources.config.DatabaseDatasourceConfigConfigurator;
import net.datenwerke.rs.base.client.datasources.dto.DatabaseDatasourceConfigDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionConfigDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionDto;
import net.datenwerke.rs.dsbundle.client.dsbundle.dto.DatabaseBundleDto;

import com.google.inject.Inject;

public class DatabaseBundleConfigConfigurator extends
	DatabaseDatasourceConfigConfigurator {

	@Inject
	public DatabaseBundleConfigConfigurator(ToolbarService toolbarService) {
		super(toolbarService);
	}


	public DatasourceDefinitionConfigDto createConfigObject() {
		return new DatabaseDatasourceConfigDto();
	}
	
	@Override
	public boolean consumes(DatasourceDefinitionDto datasourceDefinitionDto, DatasourceDefinitionConfigDto datasourceConfig) {
		return null != datasourceConfig && datasourceConfig instanceof DatabaseDatasourceConfigDto && datasourceDefinitionDto instanceof DatabaseBundleDto;
	}
}
