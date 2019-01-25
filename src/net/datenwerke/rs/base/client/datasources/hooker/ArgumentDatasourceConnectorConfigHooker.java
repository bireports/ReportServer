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
 
 
package net.datenwerke.rs.base.client.datasources.hooker;

import net.datenwerke.rs.base.client.datasources.dto.ArgumentDatasourceConnectorDto;
import net.datenwerke.rs.base.client.datasources.dto.DatasourceConnectorDto;
import net.datenwerke.rs.base.client.datasources.dto.FormatBasedDatasourceDefinitionDto;
import net.datenwerke.rs.base.client.datasources.dto.decorator.ArgumentDatasourceConnectorDtoDec;
import net.datenwerke.rs.base.client.datasources.hooks.DatasourceConnectorConfiguratorHook;
import net.datenwerke.rs.base.client.datasources.locale.BaseDatasourceMessages;

import com.google.gwt.user.client.ui.Widget;

public class ArgumentDatasourceConnectorConfigHooker implements
		DatasourceConnectorConfiguratorHook {

	@Override
	public Widget configureForm(FormatBasedDatasourceDefinitionDto datasource) {
		return null;
	}

	@Override
	public DatasourceConnectorDto instantiateConnector() {
		return new ArgumentDatasourceConnectorDtoDec();
	}

	@Override
	public String getConnectorName() {
		return BaseDatasourceMessages.INSTANCE.argumentConnector();
	}

	@Override
	public boolean consumes(DatasourceConnectorDto connector) {
		return connector instanceof ArgumentDatasourceConnectorDto;
	}

}
