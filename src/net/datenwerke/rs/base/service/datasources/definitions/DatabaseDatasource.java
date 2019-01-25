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
 
 
package net.datenwerke.rs.base.service.datasources.definitions;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.datenwerke.dbpool.config.ConnectionPoolConfig;
import net.datenwerke.dbpool.config.ConnectionPoolConfigFactory;
import net.datenwerke.dbpool.config.ConnectionPoolConfigImpl;
import net.datenwerke.dtoservices.dtogenerator.annotations.AdditionalField;
import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gf.base.service.annotations.Field;
import net.datenwerke.gf.base.service.annotations.Indexed;
import net.datenwerke.rs.base.service.datasources.definitions.dtogen.DatabaseDatasource2DtoPostProcessor;
import net.datenwerke.rs.base.service.datasources.locale.DatasourcesMessages;
import net.datenwerke.rs.base.service.dbhelper.DBHelperService;
import net.datenwerke.rs.base.service.dbhelper.DatabaseHelper;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinitionConfig;
import net.datenwerke.rs.core.service.datasourcemanager.interfaces.ParameterAwareDatasource;
import net.datenwerke.rs.utils.instancedescription.annotations.InstanceDescription;
import net.datenwerke.security.service.crypto.pbe.PbeService;
import net.datenwerke.security.service.crypto.pbe.encrypt.EncryptionService;

import org.apache.commons.codec.binary.Hex;
import org.hibernate.envers.Audited;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

/**
 * Used to define data sources that can be used in ReportServer.
 * 
 * <p>
 * 
 * </p> 
 *  
 *
 */
@Entity
@Table(name="DATABASE_DATASOURCE")
@Audited
@GenerateDto(
		dtoPackage="net.datenwerke.rs.base.client.datasources.dto",
		poso2DtoPostProcessors=DatabaseDatasource2DtoPostProcessor.class,
		additionalFields = {
			@AdditionalField(name="hasPassword", type=Boolean.class),
		},
		icon="database"
		)
@InstanceDescription(
		msgLocation = DatasourcesMessages.class,
		objNameKey = "databaseDatasourceTypeName",

		icon = "database"
		)
@Indexed
public class DatabaseDatasource extends DatasourceDefinition implements ParameterAwareDatasource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5532424176260294397L;


	@Inject
	protected static Provider<PbeService> pbeServiceProvider;

	@Inject
	protected static Provider<ConnectionPoolConfigFactory> connectionConfigFactoryProvider;

	@Inject
	protected static Provider<DBHelperService> dbHelperServiceProvider;

	@ExposeToClient
	@Field
	private String url;

	@ExposeToClient
	@Field
	private String username;

	@ExposeToClient(
			exposeValueToClient=false,
			mergeDtoValueBack=true
			)
	private String password;

	@ExposeToClient
	private String databaseDescriptor;

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

	public void setDatabaseDescriptor(String databaseDescriptor) {
		this.databaseDescriptor = databaseDescriptor;
	}

	public String getDatabaseDescriptor() {
		return databaseDescriptor;
	}

	@Override @Transient
	public String escapeString(Injector injector, String string){
		if(null == getDatabaseDescriptor())
			return string;

		DBHelperService dbHelperService = injector.getInstance(DBHelperService.class);
		DatabaseHelper dbHelper = dbHelperService.getDatabaseHelper(getDatabaseDescriptor());

		return dbHelper.escapeString(string);
	}

	@Override @Transient
	public DatasourceDefinitionConfig createConfigObject() {
		return new DatabaseDatasourceConfig();
	}

	public ConnectionPoolConfig getConnectionConfig() {
		ConnectionPoolConfigImpl config = connectionConfigFactoryProvider.get().create(getId());

		config.setUsername(getUsername());
		config.setPassword(getPassword());
		config.setJdbcUrl(getUrl());
		config.setDriver(dbHelperServiceProvider.get().getDatabaseHelper(getDatabaseDescriptor()).getDriver());
		config.setLastUpdated(getLastUpdated());

		return config;
	}

	@Override
	public boolean usesParameter(DatasourceDefinitionConfig config, String key) {
		if(null == config)
			return false;

		String query = ((DatabaseDatasourceConfig)config).getQuery();
		if(null == query)
			return false;

		return query.contains(key);
	}

}
