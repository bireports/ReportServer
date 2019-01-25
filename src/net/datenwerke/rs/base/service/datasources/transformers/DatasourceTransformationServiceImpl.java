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
 
 
package net.datenwerke.rs.base.service.datasources.transformers;

import javax.inject.Inject;

import net.datenwerke.hookhandler.shared.hookhandler.HookHandlerService;
import net.datenwerke.rs.core.service.datasourcemanager.entities.DatasourceContainerProvider;
import net.datenwerke.rs.core.service.reportmanager.parameters.ParameterSet;

public class DatasourceTransformationServiceImpl implements DatasourceTransformationService {

	private HookHandlerService hookHandler;

	@Inject
	public DatasourceTransformationServiceImpl(
			HookHandlerService hookHandler
			) {
		
		this.hookHandler = hookHandler;
	}

	@Override
	public <T> T transform(Class<T> resultType, DatasourceContainerProvider containerProvider, ParameterSet parameters) {
		/* return null if no database is defined */
		if(null == containerProvider || null == containerProvider.getDatasourceContainer() || null == containerProvider.getDatasourceContainer().getDatasource())
			return null;
		
		for(DataSourceDefinitionTransformer tfmr : hookHandler.getHookers(DataSourceDefinitionTransformer.class)){
			if(tfmr.consumes(containerProvider, resultType)){
				return (T) tfmr.transform(containerProvider, resultType, parameters);
			}
		}
		
		String from = "null";
		
		if(null != containerProvider 
				&& null != containerProvider.getDatasourceContainer() 
				&& null != containerProvider.getDatasourceContainer().getDatasource()){
			from = containerProvider.getDatasourceContainer().getDatasource().getClass().getName();
		}
		String to = resultType instanceof Class ? resultType.getName() : resultType.getClass().getName();
		throw new RuntimeException("No DataSourceDefinitionTransformer for " + from + " -> " + to);
		
	}

}
