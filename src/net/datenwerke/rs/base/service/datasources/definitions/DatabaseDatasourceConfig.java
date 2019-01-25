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

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinitionConfig;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

/**
 * 
 *
 */
@Entity
@Table(name="DATABASE_DATASOURCE_CONF")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.datasources.dto"
)
public class DatabaseDatasourceConfig extends DatasourceDefinitionConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2133375471305510767L;
	
	@ExposeToClient(allowArbitraryLobSize=true,disableHtmlEncode=true)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	protected String query;
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	@Override
	public boolean contentEquals(DatasourceDefinition definition, DatasourceDefinitionConfig config) {
		if(! (config instanceof DatabaseDatasourceConfig))
			return false;
		return null == query ? null == ((DatabaseDatasourceConfig)config).getQuery() : query.equals(((DatabaseDatasourceConfig)config).getQuery());
	}
	
}
