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
 
 
package net.datenwerke.rs.saiku.server.rest;

import net.datenwerke.rs.saiku.server.rest.resources.BasicRepositoryResource;
import net.datenwerke.rs.saiku.server.rest.resources.BasicRepositoryResource2;
import net.datenwerke.rs.saiku.server.rest.resources.BasicTagRepositoryResource;
import net.datenwerke.rs.saiku.server.rest.resources.DataSourceResource;
import net.datenwerke.rs.saiku.server.rest.resources.ExporterResource;
import net.datenwerke.rs.saiku.server.rest.resources.OlapDiscoverResource;
import net.datenwerke.rs.saiku.server.rest.resources.QueryResource;
import net.datenwerke.rs.saiku.server.rest.resources.SaikuI18nResource;
import net.datenwerke.rs.saiku.server.rest.resources.SessionResource;
import net.datenwerke.rs.saiku.server.rest.resources.StatisticsResource;

import com.google.inject.AbstractModule;
import com.sun.jersey.api.core.ClassNamesResourceConfig;

public class SaikuRestModule extends AbstractModule {

	@Override
	protected void configure() {
		/* register rest resources */
//		ResourceConfig rc = new PackagesResourceConfig(this.getClass().getPackage().getName());
		
		ClassNamesResourceConfig cnrc = new ClassNamesResourceConfig(
				BasicRepositoryResource.class,
				BasicRepositoryResource2.class,
				BasicTagRepositoryResource.class,
				DataSourceResource.class,
				ExporterResource.class, 
				OlapDiscoverResource.class,
				QueryResource.class,
				SessionResource.class, 
				StatisticsResource.class, 
				SaikuI18nResource.class);
		
		for ( Class<?> resource : cnrc.getClasses() ) {
			bind( resource );
		}
	}

}
