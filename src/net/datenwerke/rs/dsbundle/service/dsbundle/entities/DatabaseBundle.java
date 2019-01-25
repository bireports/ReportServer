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
 
 
package net.datenwerke.rs.dsbundle.service.dsbundle.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.base.service.datasources.definitions.DatabaseDatasource;
import net.datenwerke.rs.dsbundle.service.dsbundle.locale.DatasourceBundleMessages;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;
import net.datenwerke.rs.utils.instancedescription.annotations.InstanceDescription;

import org.hibernate.envers.Audited;
import net.datenwerke.gf.base.service.annotations.Indexed;

@Entity
@Table(name="DB_BUNDLE_DATASOURCE")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.dsbundle.client.dsbundle.dto"
)
@InstanceDescription(
	msgLocation = DatasourceBundleMessages.class,
	objNameKey = "databaseBundleTypeName",
	icon = "database"
)
@Indexed
public class DatabaseBundle extends DatabaseDatasource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5705061680418219522L;

	@ExposeToClient
	@OneToMany(cascade=CascadeType.ALL)
	@EnclosedEntity
	@JoinTable(name="DB_BUNDLE_2_ENTRY")
	private Set<DatabaseBundleEntry> bundleEntries = new HashSet<DatabaseBundleEntry>();
	
	@ExposeToClient
	private String keySource;
	
	@ExposeToClient
	private String keySourceParamName;
	
	@ExposeToClient
	private String mappingSource;

	public Set<DatabaseBundleEntry> getBundleEntries() {
		return bundleEntries;
	}

	public void setBundleEntries(Set<DatabaseBundleEntry> bundleEntries) {
		this.bundleEntries = bundleEntries;
	}

	public String getKeySource() {
		return keySource;
	}

	public void setKeySource(String keySource) {
		this.keySource = keySource;
	}

	public String getMappingSource() {
		return mappingSource;
	}

	public void setMappingSource(String mappingSource) {
		this.mappingSource = mappingSource;
	}

	public String getKeySourceParamName() {
		return keySourceParamName;
	}

	public void setKeySourceParamName(String keySourceParamName) {
		this.keySourceParamName = keySourceParamName;
	}
	
}