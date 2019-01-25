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
 
 
package net.datenwerke.rs.saiku.server.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.sun.jersey.api.core.ClassNamesResourceConfig;

import net.datenwerke.rs.saiku.server.rest.resources.AdminResource;
import net.datenwerke.rs.saiku.server.rest.resources.BasicRepositoryResource2;
import net.datenwerke.rs.saiku.server.rest.resources.DataSourceResource;
import net.datenwerke.rs.saiku.server.rest.resources.ExporterResource;
import net.datenwerke.rs.saiku.server.rest.resources.FilterRepositoryResource;
import net.datenwerke.rs.saiku.server.rest.resources.InfoResource;
import net.datenwerke.rs.saiku.server.rest.resources.License;
import net.datenwerke.rs.saiku.server.rest.resources.OlapDiscoverResource;
import net.datenwerke.rs.saiku.server.rest.resources.Query2Resource;
import net.datenwerke.rs.saiku.server.rest.resources.SaikuI18nResource;
import net.datenwerke.rs.saiku.server.rest.resources.SessionResource;
import net.datenwerke.rs.saiku.server.rest.resources.StatisticsResource;
import net.datenwerke.rs.saiku.service.saiku.OlapUtilServiceImpl;

public class SaikuRestModule extends AbstractModule {

	private static final Logger log = LoggerFactory.getLogger(SaikuRestModule.class);

	
	@Override
	protected void configure() {
		/* register rest resources */
//		ResourceConfig rc = new PackagesResourceConfig(this.getClass().getPackage().getName());
		
		ClassNamesResourceConfig cnrc = new ClassNamesResourceConfig(
				AdminResource.class,
				BasicRepositoryResource2.class,
				DataSourceResource.class,
				ExporterResource.class,
				FilterRepositoryResource.class,
				InfoResource.class,
				License.class,
				OlapDiscoverResource.class,
				Query2Resource.class,
				SessionResource.class, 
				SaikuI18nResource.class,
				StatisticsResource.class 
				);
		
		for ( Class<?> resource : cnrc.getClasses() ) {
			log.info("try to bind: " + resource.getName());
			bind( resource );
			log.info("done binding: " + resource.getName());
		}
	}

}
