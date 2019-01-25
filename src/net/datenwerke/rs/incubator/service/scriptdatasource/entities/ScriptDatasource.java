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
 
 
package net.datenwerke.rs.incubator.service.scriptdatasource.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gf.base.service.annotations.Indexed;
import net.datenwerke.gxtdto.client.dtomanager.DtoView;
import net.datenwerke.rs.core.service.datasourcemanager.entities.CacheableDatasource;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinition;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinitionConfig;
import net.datenwerke.rs.fileserver.service.fileserver.entities.FileServerFile;

@Entity
@Table(name="SCRIPT_DATASOURCE")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.incubator.client.scriptdatasource.dto"
)
@Indexed
public class ScriptDatasource extends DatasourceDefinition implements CacheableDatasource{

	/**
	 * 
	 */
	private static final long serialVersionUID = -48477041777124011L;
	
	public static final String TABLE_NAME_PREFIX = "RS_SCR_TAB_";
	
	@ExposeToClient
	@ManyToOne
	private FileServerFile script;
	
	@ExposeToClient
	private int databaseCache = -1;
	
	@ExposeToClient(view=DtoView.MINIMAL)
	private boolean defineAtTarget;
	
	@Override
	public DatasourceDefinitionConfig createConfigObject() {
		return new ScriptDatasourceConfig();
	}

	public void setScript(FileServerFile script) {
		this.script = script;
	}

	public FileServerFile getScript() {
		return script;
	}

	public int getDatabaseCache() {
		return databaseCache;
	}

	public void setDatabaseCache(int databaseCache) {
		this.databaseCache = databaseCache;
	}
	
	public String generateTableName(){
		if(null == getId())
			throw new IllegalStateException("Expected id");
		return TABLE_NAME_PREFIX + getId();
	}
	
	public boolean isDefineAtTarget() {
		return defineAtTarget;
	}
	
	public void setDefineAtTarget(boolean defineAtTarget) {
		this.defineAtTarget = defineAtTarget;
	}

}
