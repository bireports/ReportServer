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
 
 
package net.datenwerke.rs.core.service.datasourcemanager.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.utils.entitycloner.annotation.EnclosedEntity;

import org.hibernate.envers.Audited;

@Entity
@Table(name="DATASOURCE_CONTAINER")
@Audited
@Inheritance(strategy=InheritanceType.JOINED)
@GenerateDto(
	dtoPackage="net.datenwerke.rs.core.client.datasourcemanager.dto"
)
public class DatasourceContainer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6605057669794888889L;

	@ExposeToClient
	@EnclosedEntity
    @ManyToOne(cascade=CascadeType.ALL)
    private DatasourceDefinitionConfig datasourceConfig;
    
	@ExposeToClient
    @ManyToOne
    private DatasourceDefinition datasource;
	
	@Version
	private Long version;
	
	@ExposeToClient(id=true)
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	public DatasourceContainer() {
	}

	public DatasourceContainer(DatasourceDefinition definition) {
		this.datasource = definition;
	}

	public DatasourceContainer(DatasourceDefinition definition, DatasourceDefinitionConfig config) {
		this.datasource = definition;
		this.datasourceConfig = config;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
    public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public DatasourceDefinitionConfig getDatasourceConfig() {
		return datasourceConfig;
	}

	public void setDatasourceConfig(DatasourceDefinitionConfig datasourceConfig) {
		this.datasourceConfig = datasourceConfig;
	}

	public DatasourceDefinition getDatasource() {
		return datasource;
	}

	public void setDatasource(DatasourceDefinition datasource) {
		this.datasource = datasource;
	}
	
	public DatasourceDefinitionConfig createDatasourceConfig(){
		if(null == datasource)
			return null;
		this.datasourceConfig = datasource.createConfigObject();
		return datasourceConfig;
	}
		
}
