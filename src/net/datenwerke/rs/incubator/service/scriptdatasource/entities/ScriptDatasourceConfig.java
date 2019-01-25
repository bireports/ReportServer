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
 
 
package net.datenwerke.rs.incubator.service.scriptdatasource.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinitionConfig;

@Entity
@Table(name="SCRIPT_DATASOURCE_CONFIG")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.incubator.client.scriptdatasource.dto"
)
public class ScriptDatasourceConfig extends DatasourceDefinitionConfig {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4068895957698607312L;

	@ExposeToClient
	private String arguments = "";
	
	@ExposeToClient(allowArbitraryLobSize=true,disableHtmlEncode=true)
	@Lob
	@Type(type = "net.datenwerke.rs.utils.hibernate.RsClobType")
	private String script = "";
	
	@ExposeToClient(disableHtmlEncode=true)
	@Column(length = 4096)
	private String queryWrapper = "";

	public String getQueryWrapper() {
		return queryWrapper;
	}

	public void setQueryWrapper(String queryWrapper) {
		this.queryWrapper = queryWrapper;
	}

	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	public String getArguments() {
		return arguments;
	} 
	
	public String getScript() {
		return script;
	}
	
	public void setScript(String script) {
		this.script = script;
	}
	
	@Override
	public boolean contentEquals(DatasourceDefinition definition, DatasourceDefinitionConfig config) {
		if(! (config instanceof ScriptDatasourceConfig))
			return false;
		if(! (definition instanceof ScriptDatasource))
			return false;
		
		ScriptDatasourceConfig otherConfig = (ScriptDatasourceConfig) config;
		
		if(null == arguments && null != otherConfig.getArguments())
			return false;
		
		if(null != arguments && ! arguments.equals(otherConfig.getArguments()))
			return false;
		
		if(null == queryWrapper && null != otherConfig.getQueryWrapper())
			return false;
		
		if(null != queryWrapper && ! queryWrapper.equals(otherConfig.getQueryWrapper()))
			return false;
		
		/* script if allowed */
		if(((ScriptDatasource)definition).isDefineAtTarget()){
			if(null == script && null != otherConfig.getScript())
				return false;
			
			if(null != script && ! script.equals(otherConfig.getScript()))
				return false;
		}
		
		return true;
	}
}
