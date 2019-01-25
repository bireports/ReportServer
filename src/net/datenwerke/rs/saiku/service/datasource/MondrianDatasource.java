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
import javax.persistence.Lob;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinitionConfig;
import net.datenwerke.rs.saiku.service.locale.SaikuMessages;
import net.datenwerke.rs.utils.instancedescription.annotations.InstanceDescription;
import net.datenwerke.security.service.crypto.pbe.PbeService;
import net.datenwerke.security.service.crypto.pbe.encrypt.EncryptionService;

import org.apache.commons.codec.binary.Hex;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import net.datenwerke.gf.base.service.annotations.Indexed;

import com.google.inject.Inject;
import com.google.inject.Provider;

@Entity
@Table(name="MONDRIAN_DATASOURCE")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.saiku.client.datasource.dto"
)
@InstanceDescription(
		msgLocation = SaikuMessages.class,
		objNameKey = "databaseDatasourceTypeName",
		icon = "cubes"
	)
@Indexed
public class MondrianDatasource extends DatasourceDefinition {

	private static final long serialVersionUID = -7162754197542626296L;
	
	
	@Inject
	protected static Provider<PbeService> pbeServiceProvider;
	
	@ExposeToClient
	private String url = "jdbc:mondrian:Jdbc=jdbc:mysql://localhost/foodmart";
    
	@ExposeToClient
	private String username;
    
	@ExposeToClient(
		exposeValueToClient=false,
		mergeDtoValueBack=true
	)
	private String password;

	@ExposeToClient(disableHtmlEncode = true)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	private String properties = "type=OLAP\n" + 
							"name=\n" +
							"driver=mondrian.olap4j.MondrianOlap4jDriver\n" + 
							"jdbcDrivers=com.mysql.jdbc.Driver";
	
	@ExposeToClient(allowArbitraryLobSize=true, disableHtmlEncode = true)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	private String mondrianSchema;
	
	
	@Override
	public DatasourceDefinitionConfig createConfigObject() {
		return new MondrianDatasourceConfig();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		if(null == password)
			return null;
		if("".equals(password))
			return "";
		
		EncryptionService encryptionService = pbeServiceProvider.get().getEncryptionService();
		String decrypted = new String(encryptionService.decryptFromHex(password));
		return decrypted;
	}

	public void setPassword(String password) {
		if(null == password)
			password = "";
		
		EncryptionService encryptionService = pbeServiceProvider.get().getEncryptionService();
		byte[] encrypted = encryptionService.encrypt(password);

		this.password = new String(Hex.encodeHex(encrypted));
	}

	public String getProperties() {
		return properties;
	}


	public void setProperties(String properties) {
		this.properties = properties;
	}


	public String getMondrianSchema() {
		return mondrianSchema;
	}


	public void setMondrianSchema(String mondrianSchema) {
		this.mondrianSchema = mondrianSchema;
	}

}