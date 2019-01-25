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
 
 
package net.datenwerke.rs.utils.simplequery;

import net.datenwerke.rs.utils.simplequery.annotations.QueryByAttribute;
import net.datenwerke.rs.utils.simplequery.annotations.QueryById;
import net.datenwerke.rs.utils.simplequery.annotations.SimpleQuery;
import net.datenwerke.rs.utils.simplequery.byatt.QueryByAttInterceptor;
import net.datenwerke.rs.utils.simplequery.byid.QueryByIdInterceptor;
import net.datenwerke.rs.utils.simplequery.simple.SimpleQueryInterceptor;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

public class SimpleQueryModule extends AbstractModule {

	@Override
	protected void configure() {
		/* simple */
		SimpleQueryInterceptor interceptor = new SimpleQueryInterceptor();
		requestInjection(interceptor);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(SimpleQuery.class), interceptor);
		
		QueryByIdInterceptor idQInterceptor = new QueryByIdInterceptor();
		requestInjection(idQInterceptor);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(QueryById.class), idQInterceptor);
		
		QueryByAttInterceptor attQInterceptor = new QueryByAttInterceptor();
		requestInjection(attQInterceptor);
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(QueryByAttribute.class), attQInterceptor);

	}

}
