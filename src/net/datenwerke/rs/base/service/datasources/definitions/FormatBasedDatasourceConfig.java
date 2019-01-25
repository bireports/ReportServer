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
 
 
package net.datenwerke.rs.base.service.datasources.definitions;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.service.datasources.connectors.DatasourceConnector;
import net.datenwerke.rs.base.service.datasources.connectors.DatasourceConnectorConfig;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinitionConfig;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;

@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.datasources.dto",
	abstractDto=true
)
@MappedSuperclass
public abstract class FormatBasedDatasourceConfig extends DatasourceDefinitionConfig {

	private static final long serialVersionUID = 1699176959776661620L;
	
	@EnclosedEntity
	@OneToMany(cascade=CascadeType.ALL)
	@ExposeToClient
	@JoinTable(name="DATASOURCE_FBCFG_2_DSCC")
	private List<DatasourceConnectorConfig> connectorConfig;

	public List<DatasourceConnectorConfig> getConnectorConfig() {
		return connectorConfig;
	}

	public void setConnectorConfig(List<DatasourceConnectorConfig> connectorConfig) {
		this.connectorConfig = connectorConfig;
	}
	
	@Override
	public boolean contentEquals(DatasourceDefinition definition, DatasourceDefinitionConfig config) {
		if(! (config instanceof FormatBasedDatasourceConfig))
			return false;
		if(! (definition instanceof FormatBasedDatasourceDefinition))
			return false;
		
		FormatBasedDatasourceDefinition def = (FormatBasedDatasourceDefinition) definition;
		DatasourceConnector connector = def.getConnector();
		if(null == connector)
			return true;
		
		FormatBasedDatasourceConfig otherConf = (FormatBasedDatasourceConfig) config;
		
		if(null == getConnectorConfig())
			return null == otherConf.getConnectorConfig() || otherConf.getConnectorConfig().isEmpty();
		
		DatasourceConnectorConfig connectorConfig = connector.getConnectorConfigFor(this);
		DatasourceConnectorConfig otherConnectorConfig = connector.getConnectorConfigFor(otherConf);

		return null == connectorConfig ? null == otherConnectorConfig : connectorConfig.contentEquals(otherConnectorConfig);
	}
}
