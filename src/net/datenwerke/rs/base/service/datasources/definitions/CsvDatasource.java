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
 
 
package net.datenwerke.rs.base.service.datasources.definitions;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.io.IOUtils;
import org.hibernate.envers.Audited;

import net.datenwerke.dtoservices.dtogenerator.annotations.ExposeToClient;
import net.datenwerke.dtoservices.dtogenerator.annotations.GenerateDto;
import net.datenwerke.gf.base.service.annotations.Indexed;
import net.datenwerke.rs.core.service.datasourcemanager.entities.CacheableDatasource;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceDefinitionConfig;

@Entity
@Table(name="CSV_DATASOURCE")
@Audited
@GenerateDto(
	dtoPackage="net.datenwerke.rs.base.client.datasources.dto"
)
@Indexed
public class CsvDatasource extends FormatBasedDatasourceDefinition implements CacheableDatasource {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3362289890273571147L;
	
	@ExposeToClient
	private String separator = ";";
	
	@ExposeToClient
	private String quote = "\"";
	
	@ExposeToClient
	private int databaseCache = -1;
	
	@Override @Transient
	public DatasourceDefinitionConfig createConfigObject() {
		return new DatasourceDefinitionConfig();
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}

	public String getQuote() {
		return null == quote || "".equals(quote.trim()) ? "\"" : quote.trim();
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getSeparator() {
		return null == separator || "".equals(separator.trim()) ? ";" : separator.trim();
	}

	public String getDataAsString(FormatBasedDatasourceConfig dsConfig) throws IOException {
		return IOUtils.toString(getDataStream(dsConfig));
	}

	public int getDatabaseCache() {
		return databaseCache;
	}

	public void setDatabaseCache(int databaseCache) {
		this.databaseCache = databaseCache;
	}
	

}
