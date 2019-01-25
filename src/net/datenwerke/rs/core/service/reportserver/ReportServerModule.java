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
 
 
package net.datenwerke.rs.core.service.reportserver;

import net.datenwerke.rs.core.service.guice.AbstractReportServerModule;
import net.datenwerke.rs.utils.properties.ApplicationPropertiesService;
import net.datenwerke.rs.utils.properties.ApplicationPropertiesServiceImpl;

import com.google.inject.Singleton;

public class ReportServerModule extends AbstractReportServerModule {

	public final static String USER_PROPERTY_ACCOUNT_INHIBITED = "accountInhibited";
	public final static String USER_PROPERTY_ACCOUNT_EXPIRATION_DATE = "accountExpirationDate";
	
	@Override
	protected void configure() {
		bind(ReportServerService.class).to(ReportServerServiceImpl.class).in(Singleton.class);
		bind(ApplicationPropertiesService.class).to(ApplicationPropertiesServiceImpl.class).in(Singleton.class);

		bind(ReportServerServiceStartup.class).asEagerSingleton();
	}

}
