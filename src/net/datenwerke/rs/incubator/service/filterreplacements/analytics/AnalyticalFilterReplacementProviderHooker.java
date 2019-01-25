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
 
 
package net.datenwerke.rs.incubator.service.filterreplacements.analytics;

import net.datenwerke.rs.base.service.dbhelper.hooks.FilterReplacementProviderHook;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.ManagedQuery;
import net.datenwerke.rs.base.service.dbhelper.querybuilder.QueryBuilder;
import net.datenwerke.rs.base.service.reportengines.table.entities.Column;
import net.datenwerke.rs.utils.juel.SimpleJuel;

import com.google.inject.Inject;

public class AnalyticalFilterReplacementProviderHooker implements FilterReplacementProviderHook{

	private static final String AGG = "analytical";
	private final AnalyticalFunctionWrapperFactory aggWrapperFactory;
	
	@Inject
	public AnalyticalFilterReplacementProviderHooker(
			AnalyticalFunctionWrapperFactory analyticalFunctionWrapperFactory) {
		this.aggWrapperFactory = analyticalFunctionWrapperFactory;
	}

	@Override
	public void enhance(SimpleJuel juel, Column column, QueryBuilder queryBuilder, ManagedQuery query) {
		if(null == query.getDatasource() || null == query.getDatasource().getDatasourceContainerProvider())
			return;
		
		juel.addReplacement(AGG, aggWrapperFactory.create(column, query.getDatasource().getParameters(), query.getDatasource().getDatasourceContainerProvider(), queryBuilder, query));
	}

}