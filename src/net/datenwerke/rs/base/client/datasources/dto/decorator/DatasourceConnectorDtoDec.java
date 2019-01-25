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
 
 
package net.datenwerke.rs.base.client.datasources.dto.decorator;

import java.util.List;

import net.datenwerke.gxtdto.client.dtomanager.IdedDto;
import net.datenwerke.rs.base.client.datasources.config.CsvDatasourceConfigConfigurator;
import net.datenwerke.rs.base.client.datasources.dto.DatasourceConnectorConfigDto;
import net.datenwerke.rs.base.client.datasources.dto.DatasourceConnectorDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionConfigDto;
import net.datenwerke.rs.core.client.datasourcemanager.dto.DatasourceDefinitionDto;
import net.datenwerke.rs.core.client.datasourcemanager.helper.forms.DatasourceSelectionField;

import com.google.gwt.user.client.ui.Widget;

/**
 * Dto Decorator for {@link DatasourceConnectorDto}
 *
 */
public class DatasourceConnectorDtoDec extends DatasourceConnectorDto implements IdedDto {


	private static final long serialVersionUID = 1L;

	public DatasourceConnectorDtoDec() {
		super();
	}

	public DatasourceConnectorConfigDto createConfigObject(){
		return null;
	}
	
	public void addConnectorSpecificFormFields(List<Widget> widgets, DatasourceDefinitionConfigDto config, DatasourceDefinitionDto datasourceDefinitionDto, DatasourceSelectionField datasourceSelectionField, CsvDatasourceConfigConfigurator configConfigurator){
		
	}

	public void inheritConnectorSpecificChanges(
			DatasourceDefinitionConfigDto config,
			DatasourceDefinitionDto datasourceDefinitionDto,
			CsvDatasourceConfigConfigurator csvDatasourceConfigConfigurator) {
		// TODO Auto-generated method stub
		
	}

}
