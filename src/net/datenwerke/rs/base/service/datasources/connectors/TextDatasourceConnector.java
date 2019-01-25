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

import java.io.InputStream;

import javax.persistence.Entity;
import javax.persistence.Lob;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.rs.base.service.datasources.definitions.FormatBasedDatasourceConfig;

import org.apache.commons.io.IOUtils;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

@GenerateDto(
		dtoPackage="net.datenwerke.rs.base.client.datasources.dto"
	)
@Entity
@Audited
public class TextDatasourceConnector extends DatasourceConnector {

	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	@ExposeToClient(view=DtoView.ALL)
	private String data;

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	@Override
	public InputStream getDataStream(FormatBasedDatasourceConfig dsConfig) {
		return IOUtils.toInputStream(data);
	}
	
	@Override
	public DatasourceConnectorConfig getConnectorConfigFor(FormatBasedDatasourceConfig dsConfig) {
		return null;
	}
}
