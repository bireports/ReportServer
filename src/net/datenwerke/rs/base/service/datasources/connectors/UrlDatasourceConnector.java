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
 
 
package net.datenwerke.rs.base.service.datasources.connectors;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.persistence.Entity;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.envers.Audited;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.rs.base.service.datasources.definitions.FormatBasedDatasourceConfig;

@GenerateDto(
		dtoPackage="net.datenwerke.rs.base.client.datasources.dto"
	)
@Entity
@Audited
public class UrlDatasourceConnector extends DatasourceConnector {

	@ExposeToClient(view=DtoView.ALL)
	private String url;

	public void setUrl(String url) {
		this.url = url;
	}


	public String getUrl() {
		return url;
	}
	
	@Override
	public InputStream getDataStream(FormatBasedDatasourceConfig dsConfig) throws IOException {
		URL u = new URL(url);
		URLConnection urlConnection = u.openConnection();
		
		if (u.getUserInfo() != null) {
		    String basicAuth = "Basic " + new String(new Base64().encode(u.getUserInfo().getBytes()));
		    urlConnection.setRequestProperty("Authorization", basicAuth);
		}
		
		return urlConnection.getInputStream();
	}
	
	@Override
	public DatasourceConnectorConfig getConnectorConfigFor(FormatBasedDatasourceConfig dsConfig) {
		return null;
	}
}
