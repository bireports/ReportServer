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
 
 
package net.datenwerke.rs.base.service.datasources.transformers.database;

import java.sql.Connection;

import com.google.inject.Inject;

import net.datenwerke.dbpool.DbPoolService;
import net.datenwerke.dbpool.config.predefined.ReadOnlyConnectionConfig;
import net.datenwerke.rs.base.service.datasources.definitions.DatabaseDatasource;
import net.datenwerke.rs.base.service.datasources.transformers.DataSourceDefinitionTransformer;
import net.datenwerke.rs.base.service.reportengines.jasper.JasperDBDataSource;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainer;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainerProvider;
import net.datenwerke.rs.core.service.reportmanager.exceptions.DatabaseConnectionException;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * Takes a {@link #DataSourceDefinition} and transforms it into the corresponding JRDataSource.
 * 
 *
 * @see DatasourceDefinition2
 */
public class Database2JasperTransformer implements DataSourceDefinitionTransformer<JRDataSource>  {

	private final DbPoolService<?> dbPoolService; 
	
	@Inject
	public Database2JasperTransformer(
		DbPoolService dbPoolService
		) {
		super();
		
		/* store objects */
		this.dbPoolService = dbPoolService;
	}

	@Override
	public boolean consumes(DatasourceContainerProvider containerProvider, Class<?> dst) {
		DatasourceContainer container = containerProvider.getDatasourceContainer();
		return (null != container && container.getDatasource() instanceof DatabaseDatasource && dst.isAssignableFrom(JasperDBDataSource.class));
	}
	
	@Override
	public JRDataSource transform(DatasourceContainerProvider containerProvider, Class<?> dst, ParameterSet parameters) {
		DatasourceContainer container = containerProvider.getDatasourceContainer();
		DatabaseDatasource ds = (DatabaseDatasource) container.getDatasource();
		
		/* open connection */
		try {
			Connection conn = dbPoolService.getConnection(ds.getConnectionConfig(), new ReadOnlyConnectionConfig()).get();
			return new JasperDBDataSource(conn);
		} catch (Exception e) {
			DatabaseConnectionException dce = new DatabaseConnectionException(ds.getUrl(), ds.getUsername(),e);
			throw dce;
		}
	}

}
