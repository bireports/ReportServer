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
 
 
package net.datenwerke.rs.saiku.service.datasource;

import javax.persistence.Entity;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinitionConfig;

import org.hibernate.envers.Audited;

@Entity
@Table(name="MONDRIAN_DATASOURCE_CFG")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.saiku.client.datasource.dto"
)
public class MondrianDatasourceConfig extends DatasourceDefinitionConfig {
	
	private static final long serialVersionUID = 2267825708465524128L;
	
	@ExposeToClient
	private String cubeName;

	
	public MondrianDatasourceConfig() {

	}

	
	
	public String getCubeName() {
		return cubeName;
	}

	public void setCubeName(String cube) {
		this.cubeName = cube;
	}

	
	
}
