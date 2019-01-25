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
 
 
package net.datenwerke.rs.base.service.datasources.connectors;

import java.io.IOException;
import java.io.InputStream;

import javax.persistence.Entity;

import org.apache.commons.io.IOUtils;
import org.hibernate.envers.Audited;

import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.service.datasources.definitions.FormatBasedDatasourceConfig;

@GenerateDto(
		dtoPackage="net.datenwerke.rs.base.client.datasources.dto",
		createDecorator = true
	)
@Entity
@Audited
public class ArgumentDatasourceConnector extends DatasourceConnector {
	
	private static final transient String CONNECTOR_CFG_KEY = "ARGUMENT_DATASRC_CNCTR_CFG";

	@Override
	public InputStream getDataStream(FormatBasedDatasourceConfig dsConfig) throws IOException {
		DatasourceConnectorConfig ccfg = getConnectorConfigFor(dsConfig);
		if(null != ccfg)
			return IOUtils.toInputStream(ccfg.getValue());
		return null;
	}
	
	@Override
	public DatasourceConnectorConfig getConnectorConfigFor(FormatBasedDatasourceConfig dsConfig){
		for(DatasourceConnectorConfig ccfg : dsConfig.getConnectorConfig()){
			if(CONNECTOR_CFG_KEY.equals(ccfg.getKey())){
				return ccfg;
			}
		}
		return null;
	}

}
